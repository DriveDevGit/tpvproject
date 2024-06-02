package ViewModel;

public class Pedido {
		
		private int id;
		
		private String apertura;
		private String cierre;
		private double total;
		private int id_mesa;
		private String usuario;
		
		public Pedido() {
			
		}
		
		

		public Pedido(String apertura, String cierre, double total, int id_mesa, String usuario) {
			super();
			this.apertura = apertura;
			this.cierre = cierre;
			this.total = total;
			this.id_mesa = id_mesa;
			this.usuario = usuario;
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
			return id_mesa;
		}



		public void setId_mesa(int id_mesa) {
			this.id_mesa = id_mesa;
		}
	


		public String getUsuario() {
			return usuario;
		}



		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}



		public String getAll() {
			return id+";"+apertura+";"+cierre+";"+total+";"+id_mesa+";"+usuario;
		}
}
