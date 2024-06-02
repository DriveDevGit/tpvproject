package Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ViewModel.Mesa;
import ViewModel.Pedido;
import ViewModel.Perfil;
import ViewModel.ProductoSeleccionado;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CocinaController {
	
	private SocketManager socketManager = TPV.getSocketManager();
	
	private Perfil perfilActual = TPV.getPerfilActual();
	
    @FXML
    private Button exitButton;
    
    @FXML
    private TableView<Pedido> tabPedidos;
    
    List<Pedido> pedidos = new ArrayList<>();
    
    @FXML
	private TableColumn<Pedido, Integer> tabPedido;
    
    @FXML
	private TableColumn<Pedido, Integer> tabMesa;
    
    @FXML
    private TableView<ProductoSeleccionado> tabProductos;
    
    ObservableList<ProductoSeleccionado> productosSeleccionados = FXCollections.observableArrayList();
    
    @FXML
	private TableColumn<ProductoSeleccionado, Integer> udsTabla;
    @FXML
	private TableColumn<ProductoSeleccionado, String> nombreTabla;
    @FXML
	private TableColumn<ProductoSeleccionado, Double> precioTabla;
    
    private int idPedidoSelected;
    
    private Thread hilo;
    
    @FXML
    public void initialize() throws IOException {       
    	//Inicializar la tabla
    	udsTabla = new TableColumn<>("Unidades");
        udsTabla.setCellValueFactory(new PropertyValueFactory<>("unidades"));

        nombreTabla = new TableColumn<>("Nombre");
        nombreTabla.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        precioTabla = new TableColumn<>("Precio Total");
        precioTabla.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));

        tabProductos.getColumns().addAll(udsTabla, nombreTabla, precioTabla);
        
        //Crear una clase personalizada de TableRow
        tabProductos.setRowFactory(tv -> {
            TableRow<ProductoSeleccionado> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ProductoSeleccionado rowData = row.getItem();
                    // Acciones al hacer doble clic en una fila
                }
            });
            row.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    row.setStyle("");
                } else if (newValue.getListoEntrega() == 1) {
                    row.setStyle("-fx-background-color: lightgreen;");
                } else if (newValue.getListoCocina() == 1) {
                    row.setStyle("-fx-background-color: red;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });
    	
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
            	while(true) {
	            	try {
	            		LimpiarTabla();
		                socketManager.enviar("CLIENTE:COCINA");
		                
		                String respuesta = socketManager.recibir(); // Recibe el mensaje del servidor
		                System.out.println(respuesta);
		
		                String[] partes = respuesta.split(":");
		                int contador = Integer.parseInt(partes[1]);
		
		                for (int i = 0; i < contador; i++) {
		                    respuesta = socketManager.recibir(); // Recibe cada perfil
		                    partes = respuesta.split(":");
		                    Pedido pedido = new Pedido();
		                    String[] datosPedido = partes[1].split(";");
		
		                    pedido.setId(Integer.parseInt(datosPedido[0]));
		                    pedido.setId_mesa(Integer.parseInt(datosPedido[1]));
		
		                    pedidos.add(pedido);
		
		                    System.out.println(pedido.getId() + ";" + pedido.getId_mesa());
		                }
	            	
	                
	                //Rellenar tabla
	                Platform.runLater(new Runnable() {
	                    @Override
	                    public void run() {
	                        tabPedido.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("Id"));
	                        tabMesa.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("Id_mesa"));
	                        tabPedidos.getItems().addAll(pedidos);
	                    }
	                });
	                
	                	Thread.sleep(7000);
	                	
	            	}
	            	catch(IOException ioe) {
	            		ioe.printStackTrace();
	            	} 
	            	catch (InterruptedException e) {
	
						e.printStackTrace();
					}
            	}
            }
        });
        		
        hilo.start();
        
        tabPedidos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
            	LimpiarTablaProductos();
                Pedido selectedPedido = newValue;
                idPedidoSelected = selectedPedido.getId();
               
                try {
                socketManager.enviar("CLIENTE:TRAER PEDIDO COCINA:"+selectedPedido.getId());
                
                String respuesta2 = socketManager.recibir(); // Recibe el mensaje del servidor
                System.out.println(respuesta2);

                String[] partes2 = respuesta2.split(":");
                int longitudPedido = Integer.parseInt(partes2[1]);
                
                for(int i = 0; i<longitudPedido; i++) {
                    respuesta2 = socketManager.recibir(); // Recibe cada Producto
                    partes2 = respuesta2.split(":");
                    ProductoSeleccionado productoSeleccionado = new ProductoSeleccionado();
                    String[] datosProductos = partes2[1].split(";");

                    productoSeleccionado.setUnidades(Integer.parseInt(datosProductos[1]));
                    productoSeleccionado.setNombre(datosProductos[3]);
                    productoSeleccionado.setPrecioTotal(Double.parseDouble(datosProductos[4]));
                    productoSeleccionado.setListoCocina(Integer.parseInt(datosProductos[5]));
                    productoSeleccionado.setListoEntrega(Integer.parseInt(datosProductos[6]));

                    productosSeleccionados.add(productoSeleccionado);
                }

                    tabProductos.getItems().clear();
                    tabProductos.getItems().addAll(productosSeleccionados);
                    System.out.println(tabProductos.getItems().size());
                    tabProductos.refresh();
                }
                catch(IOException ioe) {
                	ioe.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    private void CambiarListoCocina() {
    	 // Obtener la fila seleccionada
        TablePosition<ProductoSeleccionado, ?> pos = tabProductos.getSelectionModel().getSelectedCells().get(0);
        int rowIndex = pos.getRow();

        // Obtener el objeto ProductoSeleccionado correspondiente a la fila seleccionada
        ProductoSeleccionado productoSeleccionado = tabProductos.getItems().get(rowIndex);

        // Cambiar el valor del atributo ListoEntrega a 1
        productoSeleccionado.setListoCocina(1);
        tabProductos.refresh();
        
        socketManager.enviar("CLIENTE:LISTO COCINA:"+idPedidoSelected+";"+productosSeleccionados.size());
		for (ProductoSeleccionado producto : productosSeleccionados) {
			socketManager.enviar("CLIENTE:"+producto.getAll());
		}
    }
    
    @FXML
    private void LimpiarTabla() {
    	pedidos.clear();
    	tabPedidos.getItems().clear();
    }
    
    @FXML
    private void LimpiarTablaProductos() {
    	productosSeleccionados.clear();
    	tabProductos.getItems().clear();
    }
    
    @FXML
    private void Atrás() throws IOException {
    	socketManager.enviar("CLIENTE:SALIR");
		TPV.setRoot("primary", 600, 350);
		if (hilo!= null && hilo.isAlive()) {
	        hilo.interrupt();
	    }
    }    
    
}
