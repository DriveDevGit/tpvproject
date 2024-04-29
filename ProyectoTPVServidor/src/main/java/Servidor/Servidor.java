package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import DAO.PerfilDao;


public class Servidor 
{
	public static boolean encendido = true;


	public static void main( String[] args ) throws IOException
	{
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		
		try {
			serverSocket = new ServerSocket(9090);
			clientSocket = null;

			while(encendido) {

				System.out.println("Esperando al cliente...");
				clientSocket = serverSocket.accept();
				HiloMaster hilo = new HiloMaster(clientSocket);
				hilo.start();		
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			clientSocket.close();
			serverSocket.close();
		}

	}

}
