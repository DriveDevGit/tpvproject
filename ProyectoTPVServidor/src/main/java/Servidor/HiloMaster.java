package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import DAO.PerfilDao;
import Model.Perfil;

public class HiloMaster extends Thread {
	private Socket clientSocket;
	private PerfilDao perfilDao = new PerfilDao();
	public static boolean encendido = true;
	public String estado = "INICIO";

	public HiloMaster(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
	        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

	        while (true) {
	            String mensaje = in.readLine();
	            if (mensaje == null) {
	                break; // Si el cliente cierra la conexión, salir del bucle
	            }

	            String modulo = mensaje.split(":")[1];

	            switch (estado) {
	                case "INICIO":
	                    switch (modulo) {
	                        case "LOGIN":
	                            String parametros = mensaje.split(":")[2];
	                            login(parametros.split(";")[0], parametros.split(";")[1]); //Usuario y contraseña
	                            break;
	                        case "PERFIL":
	                        	avisoIniciando(clientSocket);
	                            break;
	                    }
	                    break;
	                case "CORRIENDO":
	                    switch (modulo) {
	                        case "LOGIN":
	                        	avisoCorriendo(clientSocket);
	                            break;
	                        case "PERFIL":
	                            out.println("SERVIDOR: Hola"); // Servidor envía mensaje a cliente
	                            break;
	                    }
	                    break;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            clientSocket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	}

	public void login(String usuario, String password) {
		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			String respuesta = perfilDao.UsuarioExiste(usuario, password);
			out.println("SERVIDOR:"+respuesta); // Servidor envía mensaje a cliente
			

			if(respuesta.equals("LOGIN CORRECTO")) {
				estado = "CORRIENDO";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void avisoCorriendo(Socket clientSocket) throws IOException {
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		String respuesta = "No se puede acceder a este módulo si aún no has logueado";
		out.println("SERVIDOR:"+respuesta); // Servidor envía mensaje a cliente
		
	}
	
	public void avisoIniciando(Socket clientSocket) throws IOException {
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		String respuesta = "No se puede acceder a este módulo si ya estás logueado";
		out.println("SERVIDOR:"+respuesta); // Servidor envía mensaje a cliente
		
	}
}
