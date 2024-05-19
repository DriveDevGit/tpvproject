package Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Producto {
		
		@Id
		private int id;
		
		private String nombre;
		private String descripcion;
		private String categoria;
		private double precio;
		
		
		public Producto() {
			
		}
	
		public Producto(String nombre, String descripcion, String categoria, double precio) {
			super();
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.categoria = categoria;
			this.precio = precio;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getCategoria() {
			return categoria;
		}

		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}

		public double getPrecio() {
			return precio;
		}

		public void setPrecio(double precio) {
			this.precio = precio;
		}

		public String getAll() {
			return id+";"+nombre+";"+descripcion+";"+categoria+";"+precio;
		}
}
