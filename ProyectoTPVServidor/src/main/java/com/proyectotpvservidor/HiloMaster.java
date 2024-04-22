package com.proyectotpvservidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import DAO.PerfilDao;

public class HiloMaster extends Thread {
	private Socket clientSocket;
	private PerfilDao perfilDao = new PerfilDao();
	public String estado = "INICIO";

	public HiloMaster(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		System.out.println("Cliente conectado!");

		try {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String mensaje = in.readLine();
			String modulo = mensaje.split(":")[1];
			
			switch (estado) {
			case "INICIO":
				switch (modulo) {
				case "LOGIN":
					String parámetros = mensaje.split(":")[2];
					login(parámetros.split(";")[0], parámetros.split(";")[1]); //Usuario y contraseña
					in.close();
					break;
				}
				break;
			case "CORRIENDO":
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void login(String usuario, String password) {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			String respuesta = perfilDao.UsuarioExiste(usuario, password);
			out.println("SERVIDOR:"+respuesta); // Servidor envía mensaje a cliente
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
