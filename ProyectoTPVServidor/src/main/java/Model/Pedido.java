package Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pedido {
		
		@Id
		private int id;
		
		private String apertura;
		private String cierre;
		private double total;
		private int mesa_id;
		private String username;
		
		public Pedido() {
			
		}
		
		

		public Pedido(String apertura, int id_mesa, String usuario) {
			super();
			this.apertura = apertura;
			this.cierre = cierre;
			this.total = total;
			this.mesa_id = id_mesa;
			this.username = usuario;
		}



		public int getId() {
			return id;
		}



		public void setId(int id) {
			this.id = id;
		}



		public String getApertura() {
			return apertura;
		}



		public void setApertura(String apertura) {
			this.apertura = apertura;
		}



		public String getCierre() {
			return cierre;
		}



		public void setCierre(String cierre) {
			this.cierre = cierre;
		}



		public double getTotal() {
			return total;
		}



		public void setTotal(double total) {
			this.total = total;
		}



		public int getId_mesa() {
			return mesa_id;
		}



		public void setId_mesa(int id_mesa) {
			this.mesa_id = id_mesa;
		}
		
		


		public String getUsuario() {
			return username;
		}



		public void setUsuario(String usuario) {
			this.username = usuario;
		}



		public String getAll() {
			return id+";"+apertura+";"+cierre+";"+total+";"+mesa_id+";"+username;
		}
}
