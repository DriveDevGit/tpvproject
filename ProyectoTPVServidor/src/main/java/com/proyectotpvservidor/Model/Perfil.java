package com.proyectotpvservidor.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Perfil {
		
		@Id
		private int id;
		
		private String nombre;
		private String username;
		private String password;
		private int rol;
		
		public Perfil() {
			
		}
		
		public int isRol() {
			return rol;
		}

		public void setRol(int rol) {
			this.rol = rol;
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
	

}
