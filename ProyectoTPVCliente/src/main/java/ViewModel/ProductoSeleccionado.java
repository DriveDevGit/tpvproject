package ViewModel;

public class ProductoSeleccionado {
	
	private int id;
	
	private int id_producto;
	private int id_pedido;
	private int unidades;
	private String nombre;
	private double precioTotal;
	private int listoCocina;
	private int listoEntrega;
	
	public ProductoSeleccionado() {
		
	}
	
	public ProductoSeleccionado(int unidades, String nombre, double precioTotal) {
		super();
		this.unidades = unidades;
		this.nombre = nombre;
		this.precioTotal = precioTotal;
	}
	
		
	public ProductoSeleccionado(int id_producto, int id_pedido, int unidades, String nombre, double precioTotal) {
		super();
		this.id_producto = id_producto;
		this.id_pedido = id_pedido;
		this.unidades = unidades;
		this.nombre = nombre;
		this.precioTotal = precioTotal;
	}

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public int getUnidades() {
		return unidades;
	}
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
		
	public int getListoCocina() {
		return listoCocina;
	}

	public void setListoCocina(int listoCocina) {
		this.listoCocina = listoCocina;
	}

	public int getListoEntrega() {
		return listoEntrega;
	}

	public void setListoEntrega(int listoEntrega) {
		this.listoEntrega = listoEntrega;
	}

	public String getAll() {
		return unidades+";"+nombre+";"+precioTotal+";"+listoCocina+";"+listoEntrega;
	}

}
