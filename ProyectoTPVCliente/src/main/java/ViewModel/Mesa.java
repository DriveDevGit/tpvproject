package ViewModel;

public class Mesa {
		
		private int id;
		
		private String nombre;
		private int numero;
		private int ocupado;
		
		public Mesa() {
			
		}		
	
		public Mesa(String nombre, int numero) {
			super();
			this.nombre = nombre;
			this.numero = numero;
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

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public int getOcupado() {
			return ocupado;
		}

		public void setOcupado(int ocupado) {
			this.ocupado = ocupado;
		}

		public String getAll() {
			return id+";"+nombre+";"+numero+";"+ocupado;
		}
}
