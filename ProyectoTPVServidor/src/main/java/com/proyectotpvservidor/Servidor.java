package com.proyectotpvservidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import DAO.PerfilDao;


public class Servidor 
{
	public static boolean encendido = true;
	
	
    public static void main( String[] args )
    {
    	
    	try {
    		ServerSocket serverSocket = new ServerSocket(9090);
    		Socket clientSocket = null;
    		
            
            while(encendido) {
            	System.out.println("Esperando al cliente...");
            	clientSocket = serverSocket.accept();
        		HiloMaster hilo = new HiloMaster(clientSocket);
        		hilo.start();
            }
            
            clientSocket.close();
            serverSocket.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    
}
