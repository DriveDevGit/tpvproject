package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.TreeMap;

import DAO.PerfilDao;
import DAO.ProductoDao;
import Model.Perfil;
import Model.Producto;

public class HiloMaster extends Thread {
	private Socket clientSocket;
	private PerfilDao perfilDao = new PerfilDao();
	private ProductoDao productoDao = new ProductoDao();
	public static boolean encendido = true;
	public String estado = "INICIO";
	
	public BufferedReader in;
	public PrintWriter out;

	public HiloMaster(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		this.out = new PrintWriter(clientSocket.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {

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
	                            Login(parametros.split(";")[0], parametros.split(";")[1]); //Usuario y contraseña
	                            break;
	                    }
	                    break;
	                case "CORRIENDO":
	                    switch (modulo) {
	                        case "PERFIL":
	                            PerfilInicio();
	                            break;
	                        case "CREAR PERFIL":
	                        	CrearPerfil(mensaje.split(":")[2]);
	                        	break;
	                        case "PRODUCTO":
	                            PerfilInicio();
	                            break;
	                        case "CREAR PRODUCTO":
	                        	CrearPerfil(mensaje.split(":")[2]);
	                        	break;	
	                        case "SALIR":
	                        	estado = "INICIO";
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

	public void Login(String usuario, String password) {
		try {
			String respuesta = perfilDao.UsuarioExiste(usuario, password);
			out.println("SERVIDOR:"+respuesta); // Servidor envía mensaje a cliente
			

			if(respuesta.split(":")[0].equals("LOGIN CORRECTO")) {
				estado = "CORRIENDO";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void PerfilInicio() {
		try {
	        List<Perfil> perfiles = perfilDao.obtenerTodosLosPerfiles();
	        out.println("SERVIDOR:" + perfiles.size());
	        for (Perfil perfil : perfiles) {
	            out.println("SERVIDOR:" + perfil.getAll());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void CrearPerfil(String perfilMensaje) {
		Perfil nuevoPerfil = new Perfil(perfilMensaje.split(";")[0], perfilMensaje.split(";")[1], perfilMensaje.split(";")[2],
				perfilMensaje.split(";")[3], perfilMensaje.split(";")[4], perfilMensaje.split(";")[5], perfilMensaje.split(";")[6]);
		
		perfilDao.InsertarPerfil(nuevoPerfil);
	}
	
	public void PerfilProducto() {
		try {
	        List<Producto> productos = productoDao.obtenerTodosLosProductos();
	        out.println("SERVIDOR:" + productos.size());
	        for (Producto producto : productos) {
	            out.println("SERVIDOR:" + producto.getAll());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void CrearProducto(String productoMensaje) {
		Producto nuevoProducto = new Producto(productoMensaje.split(";")[0], productoMensaje.split(";")[1], 
				productoMensaje.split(";")[2], Double.parseDouble(productoMensaje.split(";")[3]));
		
		productoDao.InsertarProducto(nuevoProducto);
	}
	
}
