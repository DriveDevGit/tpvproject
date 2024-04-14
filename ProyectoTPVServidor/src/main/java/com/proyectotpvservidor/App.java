package com.proyectotpvservidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import com.proyectotpvservidor.Model.PerfilDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Hello world!
 *
 */
public class App 
{
	static PerfilDao perfilDao = new PerfilDao();
	
    public static void main( String[] args )
    {
    	
    	try {
    		ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("Esperando al cliente...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado!");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            String message = in.readLine(); // 1# Recibe mensaje del cliente
            String usuario = in.readLine(); // 2# Recibe el usuario del cliente
            String password = in.readLine(); // 3# Recibe la contraseña del cliente
            
            boolean respuesta = perfilDao.UsuarioExiste(usuario, password);
            out.println(String.valueOf(respuesta)); // Envía un booleano al cliente 1#

            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    
}
