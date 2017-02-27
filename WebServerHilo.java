import java.net.*;
import java.io.*;
import java.util.*;

public class WebServerHilo{
	private static ServerSocket sSocket;

	public static void main(String[] args){
		try{
			sSocket = new ServerSocket(6789);
			System.out.println("Servidor Activo");
			while(true){
				Socket socket = sSocket.accept();
				System.out.println(socket);
				HWebServer hWebServer = new HWebServer(socket);
				Thread hilo = new Thread(hWebServer);
				hilo.start();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}