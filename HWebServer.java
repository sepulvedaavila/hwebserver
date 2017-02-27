import java.net.*;
import java.io.*;
import java.util.*;

public class HWebServer implements Runnable{
	
	private Socket socket;

	public HWebServer(Socket socket){
		this.socket = socket;
	}

	public void run(){
		try{
			// Code from prev class
			BufferedReader serverIn;
			serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream salidaCliente = new DataOutputStream(socket.getOutputStream());
			String cadena;
			cadena = serverIn.readLine();
			System.out.println(cadena);

			if(cadena != null){
				StringTokenizer st = new StringTokenizer(cadena);
				if(st.countTokens() == 3){

					String comando = st.nextToken();
					String ruta = st.nextToken();
					ruta = ruta.substring(1);
					System.out.println(ruta);
					String protocolo = st.nextToken();
					if(comando.toUpperCase().equals("GET")){
						File file = new File(ruta);

						if(file.exists()){
							System.out.println("Encontro el archivo");
							int longitud = (int)file.length();
							//Arreglo de bytes donde se pondra el archivo
							byte [] buffer = new byte[longitud];
							//Abrir archivo para lectura
							FileInputStream archivo = new FileInputStream(ruta);
							//Lee el contenido de archivos y los pone en 
							//la variable de tipo byte[] llamada buffer
							archivo.read(buffer,0,longitud);
							//Creamos un flujo de salida
							salidaCliente.writeBytes("HTTP/1.0 200 ok\n");
							salidaCliente.writeBytes("Server: MiApache Server/1.0\n");
							salidaCliente.writeBytes("Date: "+ new Date());
							if(ruta.endsWith(".jpg")){
								salidaCliente.writeBytes("Content-Type: image/jpeg\r\n");
							}else{
								if(ruta.endsWith(".png")){
									salidaCliente.writeBytes("Content-Type: image/png\r\n");
								}else{
									if(ruta.endsWith(".gif")){
										salidaCliente.writeBytes("Content-Type: image/gif\r\n");	
									}else{
										salidaCliente.writeBytes("Content-Type: text/html\r\n");	
									}
								}
							}
							salidaCliente.writeBytes("Content-Length: "+longitud+"\r\n\r\n");
							salidaCliente.write(buffer, 0, longitud);
						}else{
							salidaCliente.writeBytes("HTTP/1.0 404 Archivo no encontrado\n");
							salidaCliente.writeBytes("Server: MiApache Server/1.0\n");
							salidaCliente.writeBytes("Date: "+new Date());
						}
					}else{
						salidaCliente.writeBytes("HTTP/1.0 300 Solicitud erronea\n");
						salidaCliente.writeBytes("Server: SDChuy Server/1.0\n");
						salidaCliente.writeBytes("Date: "+new Date());
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}