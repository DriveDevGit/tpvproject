package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import DAO.CategoriaDao;
import DAO.MesaDao;
import DAO.PedidoDao;
import DAO.PerfilDao;
import DAO.ProductoDao;
import Model.Categoria;
import Model.Mesa;
import Model.Pedido;
import Model.Perfil;
import Model.Producto;
import Model.ProductoSeleccionado;

public class HiloMaster extends Thread {
	private Socket clientSocket;
	private PerfilDao perfilDao = new PerfilDao();
	private ProductoDao productoDao = new ProductoDao();
	private CategoriaDao categoriaDao = new CategoriaDao();
	private MesaDao mesaDao = new MesaDao();
	private PedidoDao pedidoDao = new PedidoDao();
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
	                            ProductoInicio();
	                            break;
	                        case "CREAR PRODUCTO":
	                        	CrearProducto(mensaje.split(":")[2]);
	                        	break;
	                        case "MESA":
	                        	MesaInicio();
	                        	break;
	                        case "CATEGORIA":
	                        	CategoriaInicio();
	                        	break;
	                        case "CREAR MESA":
	                        	CrearMesa(mensaje.split(":")[2]);
	                        	break;
	                        case "TPV CATEGORIA":
	                        	RealizarCategoria(mensaje.split(":")[2]);
	                        	break;
	                        case "TPV MESA":
	                        	DisponibilidadMesa(mensaje.split(":")[2]); //Saber si existe la mesa
	                        	break;
	                        case "CAMBIAR UNIDAD":
	                        	CambiarUnidad(mensaje.split(":")[2]);
	                        	break;
	                        case "COMPROBAR MESA":
	                        	String mesa = mensaje.split(":")[2];
	                        	String tipoMesa = mesa.split(" ")[0];
	                        	int numero = Integer.parseInt(mesa.split(" ")[1]);
	                        	out.println("SERVIDOR:"+mesaDao.MesaOcupada(tipoMesa, numero)); //Saber si la mesa está ocupada si existe
	                        	break;
	                        case "CREAR PEDIDO":
	                        	CrearPedido();
	                        	break;
	                        case "ACTUALIZAR MESA":
	                        	String respuesta = mensaje.split(":")[2];
	                        	mesa = respuesta.split(";")[0];
	                        	String longitud = respuesta.split(";")[1];
	                        	
	                        	ActualizarMesa(mesa, longitud);
	                        	break;
	                        case "TRAER PEDIDO":
	                        	mesa = mensaje.split(":")[2];
	                        	tipoMesa = mesa.split(" ")[0];
	                        	numero = Integer.parseInt(mesa.split(" ")[1]);
	                        	
	                        	TraerPedido(tipoMesa, numero);
	                        	break;
	                        case "CAMBIAR MESA":
	                        	String mesas = mensaje.split(":")[2];
	                        	String mesaABuscar = mesas.split(";")[0];
	                        	String mesaCambio = mesas.split(";")[1];
	                        	
	                        	CambiarMesa(mesaABuscar, mesaCambio);
	                        	break;
	                        case "TOTAL PEDIDO":
	                        	mesa = mensaje.split(":")[2];
	                        	tipoMesa = mesa.split(" ")[0];
	                        	numero = Integer.parseInt(mesa.split(" ")[1]);
	                        	
	                        	TotalPedido(tipoMesa, numero);
	                        	break;
	                        case "PAGAR PEDIDO":
	                        	mesa = mensaje.split(":")[2];
	                        	tipoMesa = mesa.split(" ")[0];
	                        	numero = Integer.parseInt(mesa.split(" ")[1]);
	                        	
	                        	CierrePedido(tipoMesa, numero);
	                        	break;
	                        case "COCINA":
	                        	TraerPedidosCocina();
	                        	break;
	                        case "TRAER PEDIDO COCINA":
	                        	int idPedido = Integer.parseInt(mensaje.split(":")[2]);
	                        	
	                        	TraerProducto(idPedido);
	                        	break;
	                        case "LISTO COCINA":
	                        	respuesta = mensaje.split(":")[2];
	                        	idPedido = Integer.parseInt(respuesta.split(";")[0]);
	                        	longitud = respuesta.split(";")[1];
	                        	
	                        	ListoCocina(idPedido, longitud);
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
	    } catch (Exception e) {
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
	
	//==============Perfil=================
	//=====================================
	
	public void PerfilInicio() {
		try {
	        List<Perfil> perfiles = perfilDao.ObtenerTodosLosPerfiles();
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
	
	//==============Producto=================
	//=======================================
	
	public void ProductoInicio() {
		try {
	        List<Producto> productos = productoDao.ObtenerTodosLosProductos();
	        out.println("SERVIDOR:" + productos.size());
	        for (Producto producto : productos) {
	            out.println("SERVIDOR:" + producto.getAll());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void CrearProducto(String productoMensaje) {
		System.out.println(productoMensaje);
		Producto nuevoProducto = new Producto(productoMensaje.split(";")[0], productoMensaje.split(";")[1], 
				productoMensaje.split(";")[2], Double.parseDouble(productoMensaje.split(";")[3]));
		
		productoDao.InsertarProducto(nuevoProducto);
	}
	
	public void CambiarUnidad(String nombre) {
		out.println("SERVIDOR:"+productoDao.ObtenerPrecioProductoPorNombre(nombre));
	}
	
	//==============Mesa=====================
	//=======================================
	
	public void MesaInicio() {
		try {
	        List<Mesa> mesas = mesaDao.ObtenerTodosLasMesas();
	        out.println("SERVIDOR:" + mesas.size());
	        for (Mesa mesa : mesas) {
	            out.println("SERVIDOR:" + mesa.getAll());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void CrearMesa(String mesaMensaje) {
		System.out.println(mesaMensaje);
		Mesa nuevaMesa = new Mesa(mesaMensaje.split(";")[1], Integer.parseInt(mesaMensaje.split(";")[2]));
		
		mesaDao.InsertarMesa(nuevaMesa);
	}
	
	//==============TPV=================
	//==================================
	
	public void CategoriaInicio() {
		try {
	        List<Categoria> categorias = categoriaDao.ObtenerTodosLasCategorias();
	        out.println("SERVIDOR:" + categorias.size());
	        for (Categoria categoria : categorias) {
	            out.println("SERVIDOR:" + categoria.getAll());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void RealizarCategoria(String categoria) {
		try {
	        List<Producto> productos = productoDao.ObtenerProductosPorCategoria(categoria);
	        out.println("SERVIDOR:" + productos.size());
	        for (Producto producto : productos) {
	            out.println("SERVIDOR:" + producto.getAll());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void DisponibilidadMesa(String mesa) {
		MesaDao mesaDao = new MesaDao();
		String tipo = mesa.split(";")[0];
		int numero = Integer.parseInt(mesa.split(";")[1]);
		
		if(mesaDao.MesaExiste(tipo, numero)) {
			out.println("SERVIDOR:EXISTE");
		}
		else {
			out.println("SERVIDOR:NO EXISTE");
		}
		
	}
	
	public void CrearPedido() throws IOException {
		String mensaje = in.readLine().split(":")[1];
		String mesa = mensaje.split(";")[0];
		String tipoMesa = mesa.split(" ")[0];
		String numMesa = mesa.split(" ")[1];
		int longitudPedido = Integer.parseInt(mensaje.split(";")[1]);
		List<ProductoSeleccionado> productos = new ArrayList<>();
		LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String apertura = fechaActual.format(formato);
        System.out.println(apertura);
		
		System.out.println(tipoMesa+" "+numMesa);
		int idMesa = mesaDao.ObtenerIdMesaPorNombreYNumero(tipoMesa, Integer.parseInt(numMesa));
		System.out.println("ID Mesa: "+idMesa);
		
		String usuario = in.readLine().split(":")[1];
		System.out.println(usuario);	
        
        Pedido pedido = new Pedido(apertura, idMesa, usuario);
		int idPedido = pedidoDao.InsertarPedido(pedido);
		
		System.out.println("ID Pedido: "+idPedido);
		
		for(int i = 0; i<longitudPedido; i++) {
			String respuesta = in.readLine(); // Recibe cada Producto
            String[] partes = respuesta.split(":");
            ProductoSeleccionado productoSeleccionado = new ProductoSeleccionado();
            String[] datosProductos = partes[1].split(";");

            productoSeleccionado.setUnidades(Integer.parseInt(datosProductos[0]));
            productoSeleccionado.setNombre(datosProductos[1]);
            productoSeleccionado.setPrecioTotal(Double.parseDouble(datosProductos[2]));
            productoSeleccionado.setListoCocina(Integer.parseInt(datosProductos[3]));
            productoSeleccionado.setListoEntrega(Integer.parseInt(datosProductos[4]));

            productos.add(productoSeleccionado);
            System.out.println("ID: "+productoDao.ObtenerIdProductoPorNombre(productoSeleccionado.getNombre()));
            
            productoSeleccionado.setId_producto(productoDao.ObtenerIdProductoPorNombre(productoSeleccionado.getNombre()));
            productoSeleccionado.setId_pedido(idPedido);
            
            System.out.println(productoSeleccionado.getAll());
            productoDao.InsertarProductoSeleccionado(productoSeleccionado);
		}
		
		mesaDao.ActualizarMesaOcupada(idMesa);
		
	}
	
	public void TraerPedido(String tipo, int numero) throws Exception{
		List<ProductoSeleccionado> productosSeleccionados = pedidoDao.getProductosSeleccionadosByMesa(tipo, numero);
		out.println("SERVIDOR:" + productosSeleccionados.size());
		System.out.println(tipo+" "+numero);
        for (ProductoSeleccionado producto : productosSeleccionados) {
            out.println("SERVIDOR:" + producto.getAll());
        }
	}
	
	public void ActualizarMesa(String mesa, String numLongitud) throws IOException {
		String tipoMesa = mesa.split(" ")[0];
		int numeroMesa = Integer.parseInt(mesa.split(" ")[1]);
		int longitudPedido = Integer.parseInt(numLongitud);
		
		List<ProductoSeleccionado> productos = new ArrayList<>();
		
		int idMesa = mesaDao.ObtenerIdMesaPorNombreYNumero(tipoMesa, numeroMesa);
		int idPedido = pedidoDao.ObtenerIdPedidoPorIdMesa(idMesa);
		productoDao.EliminarProductoSeleccionadosPorPedidoId(idPedido);
		
		for(int i = 0; i<longitudPedido; i++) {
			String respuesta = in.readLine(); // Recibe cada Producto
            String[] partes = respuesta.split(":");
            ProductoSeleccionado productoSeleccionado = new ProductoSeleccionado();
            String[] datosProductos = partes[1].split(";");

            productoSeleccionado.setUnidades(Integer.parseInt(datosProductos[0]));
            productoSeleccionado.setNombre(datosProductos[1]);
            productoSeleccionado.setPrecioTotal(Double.parseDouble(datosProductos[2]));
            productoSeleccionado.setListoCocina(Integer.parseInt(datosProductos[3]));
            productoSeleccionado.setListoEntrega(Integer.parseInt(datosProductos[4]));

            productos.add(productoSeleccionado);
            System.out.println("ID: "+productoDao.ObtenerIdProductoPorNombre(productoSeleccionado.getNombre()));
            
            productoSeleccionado.setId_producto(productoDao.ObtenerIdProductoPorNombre(productoSeleccionado.getNombre()));
            productoSeleccionado.setId_pedido(idPedido);
            
            System.out.println(productoSeleccionado.getAll());
            productoDao.InsertarProductoSeleccionado(productoSeleccionado);
		}
	}
	
	public void CambiarMesa(String mesaBuscada, String mesaCambio){
		String tipoMesaBuscada = mesaBuscada.split(" ")[0];
		int numeroMesaBuscada = Integer.parseInt(mesaBuscada.split(" ")[1]);
		
		String tipoMesaCambio = mesaCambio.split(" ")[0];
		int numeroMesaCambio = Integer.parseInt(mesaCambio.split(" ")[1]);
		
		int idMesaBuscada = mesaDao.ObtenerIdMesaPorNombreYNumero(tipoMesaBuscada, numeroMesaBuscada);
		int idPedido = pedidoDao.ObtenerIdPedidoPorIdMesa(idMesaBuscada);
		int idMesaCambio = mesaDao.ObtenerIdMesaPorNombreYNumero(tipoMesaCambio, numeroMesaCambio);
		
		pedidoDao.ActualizarPedido(idMesaBuscada, idMesaCambio);
		mesaDao.ActualizarOcupado(idMesaBuscada, 0);
		mesaDao.ActualizarOcupado(idMesaCambio, 1);
		
	}
	
	public void TotalPedido(String tipo, int numeroMesa) {
		int mesaId = mesaDao.ObtenerIdMesaPorNombreYNumero(tipo, numeroMesa);
		double total = pedidoDao.ObtenerTotalPedido(mesaId);
		
		out.println("SERVIDOR:"+total);
	}
	
	public void CierrePedido(String tipo, int numeroMesa) {	
		int mesaId = mesaDao.ObtenerIdMesaPorNombreYNumero(tipo, numeroMesa);
		mesaDao.ActualizarMesaLibre(mesaId);
		pedidoDao.CerrarPedido(mesaId);		
	}
	
	//==============Cocina==============
	//==================================
	
	public void TraerPedidosCocina() {
		List<Pedido> pedidos = pedidoDao.getAllPedidos();
		out.println("SERVIDOR:" + pedidos.size());
		for (Pedido pedido : pedidos) {
	        // Realiza alguna operación en cada pedido
	        int pedidoId = pedido.getId();
	        int mesaId = pedido.getId_mesa();
	        
	        out.println("SERVIDOR:"+pedidoId+";"+mesaId);
	    }
	}
	
	public void TraerProducto(int idPedido) {
		List<ProductoSeleccionado> productosSeleccionados = pedidoDao.getProductosSeleccionados(idPedido);
		out.println("SERVIDOR:" + productosSeleccionados.size());
        for (ProductoSeleccionado producto : productosSeleccionados) {
            out.println("SERVIDOR:" + producto.getAll());
        }
	}
	
	public void ListoCocina(int idPedido, String numLongitud) throws IOException {
		int longitudPedido = Integer.parseInt(numLongitud);
		
		List<ProductoSeleccionado> productos = new ArrayList<>();
		productoDao.EliminarProductoSeleccionadosPorPedidoId(idPedido);
		
		for(int i = 0; i<longitudPedido; i++) {
			String respuesta = in.readLine(); // Recibe cada Producto
            String[] partes = respuesta.split(":");
            ProductoSeleccionado productoSeleccionado = new ProductoSeleccionado();
            String[] datosProductos = partes[1].split(";");

            productoSeleccionado.setUnidades(Integer.parseInt(datosProductos[0]));
            productoSeleccionado.setNombre(datosProductos[1]);
            productoSeleccionado.setPrecioTotal(Double.parseDouble(datosProductos[2]));
            productoSeleccionado.setListoCocina(Integer.parseInt(datosProductos[3]));
            productoSeleccionado.setListoEntrega(Integer.parseInt(datosProductos[4]));

            productos.add(productoSeleccionado);
            System.out.println("ID: "+productoDao.ObtenerIdProductoPorNombre(productoSeleccionado.getNombre()));
            
            productoSeleccionado.setId_producto(productoDao.ObtenerIdProductoPorNombre(productoSeleccionado.getNombre()));
            productoSeleccionado.setId_pedido(idPedido);
            
            System.out.println(productoSeleccionado.getAll());
            productoDao.InsertarProductoSeleccionado(productoSeleccionado);
		}
	}
}
