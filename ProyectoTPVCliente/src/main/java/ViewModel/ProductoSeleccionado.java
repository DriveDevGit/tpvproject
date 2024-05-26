package ViewModel;

public class ProductoSeleccionado {
	
	private int unidades;
	private String nombre;
	private double precioTotal;
	
	
	
	
	public ProductoSeleccionado(int unidades, String nombre, double precioTotal) {
		super();
		this.unidades = unidades;
		this.nombre = nombre;
		this.precioTotal = precioTotal;
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
	
	public String getAll() {
		return unidades+";"+nombre+";"+precioTotal;
	}

}
