package ViewModel;


public class Perfil {
		
		private int id;
		
		private String nombre;
		private String username;
		private String password;
		private String rol;
		private String telefono;
		private String direccion;
		private String dni;
		
		public Perfil() {
			
		}
		
		

		public Perfil(String nombre, String username, String password, String rol, String telefono, String direccion,
				String dni) {
			super();
			this.nombre = nombre;
			this.username = username;
			this.password = password;
			this.rol = rol;
			this.telefono = telefono;
			this.direccion = direccion;
			this.dni = dni;
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

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}

		public String getDireccion() {
			return direccion;
		}

		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}

		public String getDni() {
			return dni;
		}

		public void setDni(String dni) {
			this.dni = dni;
		}
		
		public void setRol(String rol) {
			this.rol = rol;
		}
		
		public String getRol() {
			return rol;
		}
		
		public String getAll() {
			return nombre+";"+username+";"+password+";"+rol+";"+telefono+";"+direccion+";"+dni;
		}
	
		
}
