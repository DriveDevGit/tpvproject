package Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Cliente.TPV;
import ViewModel.Perfil;
import ViewModel.Producto;
import ViewModel.ProductoSeleccionado;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SecondaryController {
	
	private String tipo;
	private int numero;
	
	private SocketManager socketManager = TPV.getSocketManager();
	
	private Perfil perfilActual = TPV.getPerfilActual();
	
    @FXML
    private Button exitButton;
    
    @FXML
    private Button cambiarUnidadBoton;
    
    @FXML
    private Button bebidaBoton;
    
    @FXML
    private Button desayunoBoton;
    
    @FXML
    private Button licorBoton;
    
    @FXML
    private TextArea panelOperaciones;
    
    @FXML
    private AnchorPane panePrincipal;
    
    private String paneOperacionesText;
    private String operador = "";
    private Double numero1 = null;
    
    @FXML
    private Pane modalMesas;
    
    @FXML
    private Pane modalMesas2;
    
    @FXML
    private Text mesaId;
    
    List<Producto> productos = new ArrayList<>();
    
    @FXML
    private TableView<ProductoSeleccionado> tabProductos;
    
    ObservableList<ProductoSeleccionado> productosSeleccionados = FXCollections.observableArrayList();
    
    @FXML
	private TableColumn<ProductoSeleccionado, Integer> udsTabla;
    @FXML
	private TableColumn<ProductoSeleccionado, String> nombreTabla;
    @FXML
	private TableColumn<ProductoSeleccionado, Double> precioTabla;
    
    @FXML
    private GridPane gridSeccion;       
    
    @FXML
    private Pane modalTotal;
    
    @FXML
    private TextField inputTotal, inputCambio, inputImporte;
    
    @FXML
    public void initialize() {       
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
    	
        //Inicializar el panel de operaciones
    	panelOperaciones.textProperty().addListener((observable, oldValue, newValue) -> {
            paneOperacionesText = newValue;
            exitButton.setContentDisplay(ContentDisplay.RIGHT);
            exitButton.setMnemonicParsing(true);
        });
    	
    	
    	System.out.println(CrearMesaController.getTipoInicializar()+" ; "+CrearMesaController.getNumeroMesaInicializar());
    	
    	if(CrearMesaController.getTipoInicializar()!=null && CrearMesaController.getNumeroMesaInicializar()!=0) {
    		try {
                LimpiarTabla();
                socketManager.enviar("CLIENTE:TPV MESA:"+CrearMesaController.getTipoInicializar()+";"+CrearMesaController.getNumeroMesaInicializar());
                
                String respuesta = socketManager.recibir();// 1# Recibe el mensaje del servidor
                String mensaje = respuesta.split(":")[1];
                socketManager.enviar("CLIENTE:TRAER PEDIDO:"+CrearMesaController.getTipoInicializar()+" "+CrearMesaController.getNumeroMesaInicializar());
                
                respuesta = socketManager.recibir(); // Recibe el mensaje del servidor
                System.out.println(respuesta);

                String[] partes = respuesta.split(":");
                int longitudPedido = Integer.parseInt(partes[1]);
                
                for(int i = 0; i<longitudPedido; i++) {
                    respuesta = socketManager.recibir(); // Recibe cada Producto
                    partes = respuesta.split(":");
                    ProductoSeleccionado productoSeleccionado = new ProductoSeleccionado();
                    String[] datosProductos = partes[1].split(";");

                    productoSeleccionado.setUnidades(Integer.parseInt(datosProductos[1]));
                    productoSeleccionado.setNombre(datosProductos[3]);
                    productoSeleccionado.setPrecioTotal(Double.parseDouble(datosProductos[4]));
                    productoSeleccionado.setListoCocina(Integer.parseInt(datosProductos[5]));
                    productoSeleccionado.setListoEntrega(Integer.parseInt(datosProductos[6]));

                    productosSeleccionados.add(productoSeleccionado);
                }

                    tabProductos.getItems().clear();
                    tabProductos.getItems().addAll(productosSeleccionados);
                    mesaId.setText(CrearMesaController.getTipoInicializar()+" "+CrearMesaController.getNumeroMesaInicializar());
                    System.out.println(tabProductos.getItems().size());
                    tabProductos.refresh();
                
            	}
            	catch(IOException ioe){
            		ioe.printStackTrace();
            	}
    	}
    }
    
    
    @FXML
    private void Atrás() throws IOException {
    	socketManager.enviar("CLIENTE:SALIR");
		TPV.setRoot("primary", 600, 350);
    }    
    
    //Funciones lógicas para la calculadora
    @FXML
    private void botónCalc(ActionEvent event) {
        Button botón = (Button) event.getSource();
        String texto = botón.getText();

        if (texto.matches("\\d+")) {
            if (panelOperaciones.equals("")) {
                panelOperaciones.setText(texto);
            } else {
                panelOperaciones.setText(panelOperaciones.getText() + texto);
            }
        } else if (texto.matches("[+\\-*/%]")) {
            operador = texto;
            numero1 = Double.parseDouble(panelOperaciones.getText());
            panelOperaciones.setText("");
        } else if (texto.equals("=")) {
            Double numero2 = Double.parseDouble(panelOperaciones.getText());
            Double resultado = operar(numero1, numero2, operador);
            panelOperaciones.setText(String.valueOf(resultado));
            operador = "";
            numero1 = null;
        }
    }

    private Double operar(Double numero1, Double numero2, String operador) {
        switch (operador) {
            case "+":
                return numero1 + numero2;
            case "-":
                return numero1 - numero2;
            case "*":
                return numero1 * numero2;
            case "/":
                if (numero2 == 0) {
                    throw new IllegalArgumentException("División por cero no permitida");
                }
                return numero1 / numero2;
            case "%":
                return numero1 % numero2;
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }
    
    @FXML
    private void otrosBotones(ActionEvent event) throws IOException {
    	Button botón = (Button) event.getSource();
    	String texto = botón.getText();
    	
    	switch(texto) { //Borrar un carácter
    	case "◀":
    		if (!paneOperacionesText.isEmpty()) {
                panelOperaciones.setText(paneOperacionesText.substring(0, paneOperacionesText.length() - 1));
            }
    		break;
    	case "C": //Borrar todo
    		panelOperaciones.clear();
    		break;
    	case "Salir:":
    		socketManager.enviar("CLIENTE:SALIR");
    		TPV.setRoot("primary", 600, 350);
    		break;
    	}
        
    }
    
    
    //Categorías de los productos
    @FXML
    private void Seccion(ActionEvent event) throws IOException {
    	switch(((Button) event.getSource()).getText()) {
    	case "Bebidas":
    		socketManager.enviar("CLIENTE:TPV CATEGORIA:Bebidas");
    		RecibirProductos();
    		break;
    	case "Desayunos":
    		socketManager.enviar("CLIENTE:TPV CATEGORIA:Desayunos");
    		RecibirProductos();
    		break;
    	case "Licores":
    		socketManager.enviar("CLIENTE:TPV CATEGORIA:Licores");
    		RecibirProductos();
    		break;
    	}
    }
    
    
    
    private void RecibirProductos() throws IOException {
    	 gridSeccion.getChildren().clear();
    	 productos.clear();
    	 String respuesta = socketManager.recibir(); // Recibe el mensaje del servidor
         System.out.println(respuesta);

         String[] partes = respuesta.split(":");
         int contador = Integer.parseInt(partes[1]);
         for (int i = 0; i < contador; i++) {
             respuesta = socketManager.recibir(); // Recibe cada producto
             partes = respuesta.split(":");
             Producto producto = new Producto();
             String[] datosProducto = partes[1].split(";");

             producto.setNombre(datosProducto[1]);
             producto.setDescripcion(datosProducto[2]);
             producto.setCategoria(datosProducto[3]);
             producto.setPrecio(Double.parseDouble(datosProducto[4]));

             productos.add(producto);

             System.out.println(producto.getNombre() + ";" + producto.getDescripcion() + ";" + producto.getCategoria() + ";" + producto.getPrecio());
         }
         
	     int row = 0;
	     for (Producto producto : productos) {
	         //Crea un componente por cada registro
	         Label nombre = new Label(producto.getNombre());
	         gridSeccion.add(nombre, 0, row); //Añade una columna a la fila

	         //Crea un botón por registro
	         Button boton = new Button("Añadir");
	         gridSeccion.add(boton, 1, row); //Añade una nueva columna a la fila
	         
	        //Agrega un evento de clic al botón
	         boton.setOnAction(new EventHandler<ActionEvent>() {
	             @Override
	             public void handle(ActionEvent event) {
                 
	                 // Actualiza la tabla	                 
	                 int unidadesTemp = 1;
	                 if (!panelOperaciones.getText().isEmpty()) {
	                     unidadesTemp = Integer.parseInt(panelOperaciones.getText());
	                 }
	                 
	                 final int unidades = unidadesTemp;
	                 double precioTotal = producto.getPrecio() * unidades;

	                 ProductoSeleccionado productoSeleccionado = new ProductoSeleccionado(unidadesTemp, producto.getNombre(), precioTotal);
	                 productosSeleccionados.add(productoSeleccionado);

	                 System.out.println(productoSeleccionado.getAll());

	                 tabProductos.getItems().clear();
	                 tabProductos.getItems().addAll(productosSeleccionados);
	                 tabProductos.refresh();
	                 panelOperaciones.clear();
	             }
	         });

	         row++; //Añade una nueva fila para el siguiente registro
	     }
    }
    
    @FXML
    private void CambiarListoEntrega() {
    	 // Obtener la fila seleccionada
        TablePosition<ProductoSeleccionado, ?> pos = tabProductos.getSelectionModel().getSelectedCells().get(0);
        int rowIndex = pos.getRow();

        // Obtener el objeto ProductoSeleccionado correspondiente a la fila seleccionada
        ProductoSeleccionado productoSeleccionado = tabProductos.getItems().get(rowIndex);

        // Cambiar el valor del atributo ListoEntrega a 1
        productoSeleccionado.setListoEntrega(1);
        tabProductos.refresh();
    }
    
    @FXML
    private void CambiarUnidad() throws IOException {
    	ProductoSeleccionado productoSeleccionado = tabProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
        	socketManager.enviar("CLIENTE:CAMBIAR UNIDAD:"+productoSeleccionado.getNombre());
        	Double precio = Double.parseDouble(socketManager.recibir().split(":")[1]);
        	
        	productoSeleccionado.setPrecioTotal(Integer.parseInt(panelOperaciones.getText())*precio);
            productoSeleccionado.setUnidades(Integer.parseInt(panelOperaciones.getText()));
            tabProductos.refresh();
        }
    }
    
    @FXML
    private void LimpiarTabla() {
    	productosSeleccionados.clear();
    	tabProductos.getItems().clear();
    }
    
    @FXML
    private void EliminarProductoSeleccionado(ActionEvent event) {
        ProductoSeleccionado productoSeleccionado = tabProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            productosSeleccionados.remove(productoSeleccionado);
            tabProductos.getItems().remove(productoSeleccionado);
        }
    }
    
    @FXML
    private void ModalMesa(ActionEvent event) throws IOException {
    	
    	switch(((Button) event.getSource()).getText()) {
    	case "Mesa":
    		if(panelOperaciones.getText().isEmpty()) {
    			TPV.setRoot("crearmesa", 840, 600);		
    		}
    		else {
    			modalMesas.setVisible(true);
    		}
    		break;
    	case "Trasp. Mesa":
    		modalMesas2.setVisible(true);
    		break;
    	}
    	
    }
    
    @FXML
    private void SeleccionarMesa(ActionEvent event) throws IOException {
    	if (panelOperaciones.getText().isEmpty()) {
            panelOperaciones.setText("CAMPO VACÍO");
            modalMesas.setVisible(false);
        }
    	else {
    		int numeroMesa = Integer.parseInt(panelOperaciones.getText());
    		Button botón = (Button) event.getSource();
            String tipo = botón.getText();
    		socketManager.enviar("CLIENTE:TPV MESA:"+tipo+";"+numeroMesa);
    		
    		String respuesta = socketManager.recibir();// 1# Recibe el mensaje del servidor
            String mensaje = respuesta.split(":")[1];
            
            if(mensaje.equals("EXISTE")) {
            	mesaId.setText(tipo+" "+numeroMesa);
            	panelOperaciones.clear();
            	modalMesas.setVisible(false);
            	
            	//Traer el pedido de la mesa ocupada o no
            	socketManager.enviar("CLIENTE:COMPROBAR MESA:"+mesaId.getText());
        		String ocupado = socketManager.recibir().split(":")[1];
        		
        		if(!ocupado.equals("false")) { //Si está ocupada
        			LimpiarTabla();
        			socketManager.enviar("CLIENTE:TRAER PEDIDO:"+mesaId.getText());
        			
        			respuesta = socketManager.recibir(); // Recibe el mensaje del servidor
        	        System.out.println(respuesta);

        	        String[] partes = respuesta.split(":");
        	        int longitudPedido = Integer.parseInt(partes[1]);
        	        
        	        for(int i = 0; i<longitudPedido; i++) {
        				respuesta = socketManager.recibir(); // Recibe cada Producto
        	            partes = respuesta.split(":");
        	            ProductoSeleccionado productoSeleccionado = new ProductoSeleccionado();
        	            String[] datosProductos = partes[1].split(";");

        	            productoSeleccionado.setUnidades(Integer.parseInt(datosProductos[1]));
        	            productoSeleccionado.setNombre(datosProductos[3]);
        	            productoSeleccionado.setPrecioTotal(Double.parseDouble(datosProductos[4]));
        	            productoSeleccionado.setListoCocina(Integer.parseInt(datosProductos[5]));
        	            productoSeleccionado.setListoEntrega(Integer.parseInt(datosProductos[6]));

        	            productosSeleccionados.add(productoSeleccionado);
        			}
        	        
        	         tabProductos.getItems().clear();
	                 tabProductos.getItems().addAll(productosSeleccionados);
	                 tabProductos.refresh();
        		}
        		else {
        			LimpiarTabla();
        		}
            }
            else if(mensaje.equals("NO EXISTE")){
            	panelOperaciones.setText("MESA NO EXISTE");
                modalMesas.setVisible(false);
            }
    	}
    }
    
    @FXML
    private void GuardarMesa() throws NumberFormatException, IOException {
    	modalMesas.setVisible(false);
    	if(mesaId.getText().equals("BARRA")) {
    		System.out.println("No has seleccionado mesa");
    		
    	}
    	else if(tabProductos.getItems().isEmpty()) {
    		mesaId.setText("BARRA");

    	}
    	else {
    		socketManager.enviar("CLIENTE:COMPROBAR MESA:"+mesaId.getText());
    		String ocupado = socketManager.recibir().split(":")[1];
    		
    		if(ocupado.equals("false")) {
    			socketManager.enviar("CLIENTE:CREAR PEDIDO");
        		System.out.println(mesaId.getText());
        		socketManager.enviar("CLIENTE:"+mesaId.getText()+";"+ productosSeleccionados.size());
        		
        		socketManager.enviar("CLIENTE:"+perfilActual.getUsername());
        		
        		for (ProductoSeleccionado producto : productosSeleccionados) {
        			socketManager.enviar("CLIENTE:"+producto.getAll());
        		}
        		LimpiarTabla();
        		mesaId.setText("BARRA");
    		}
    		else {
    			socketManager.enviar("CLIENTE:ACTUALIZAR MESA:"+mesaId.getText()+";"+productosSeleccionados.size());
    			for (ProductoSeleccionado producto : productosSeleccionados) {
        			socketManager.enviar("CLIENTE:"+producto.getAll());
        		}    			
    			LimpiarTabla();
    			mesaId.setText("BARRA");
    			panelOperaciones.clear();
    		}
    		
    	}
    }
    
    @FXML
    private void CambiarMesa(ActionEvent event) throws IOException {
    	if(panelOperaciones.getText().isEmpty()) { //No ha seleccionado mesa
    		System.out.println("No has seleccionado mesa");
    		modalMesas2.setVisible(false);
    		
    	}
    	else if(tabProductos.getItems().isEmpty()) { //La tabla está vacía
    		mesaId.setText("BARRA");
    		modalMesas2.setVisible(false);

    	}
    	else {
    		String mesa = ((Button) event.getSource()).getText()+" "+panelOperaciones.getText();
    		socketManager.enviar("CLIENTE:COMPROBAR MESA:"+mesa);
    		String ocupado = socketManager.recibir().split(":")[1];
    		
    		if(ocupado.equals("true")) {
    			panelOperaciones.setText("MESA OCUPADA");
    			modalMesas2.setVisible(false);
    		}
    		else {
    			socketManager.enviar("CLIENTE:CAMBIAR MESA:"+mesaId.getText()+";"+mesa);
    			modalMesas2.setVisible(false);
    			mesaId.setText("Barra");
    			LimpiarTabla();
    		}
    	}
    }
    
    @FXML
    private void ModalTotal(ActionEvent event) throws IOException {
    	switch(((Button) event.getSource()).getText()) {
    	case "Total":
    		modalTotal.setVisible(true);
    		if(!mesaId.getText().equals("BARRA")) {
    			
    			socketManager.enviar("CLIENTE:COMPROBAR MESA:"+mesaId.getText());
        		String ocupado = socketManager.recibir().split(":")[1];
        		
        		if(!ocupado.equals("false")) { //Si está ocupada
        			socketManager.enviar("CLIENTE:TOTAL PEDIDO:"+mesaId.getText());
        			
        			String respuesta = socketManager.recibir(); // Recibe el mensaje del servidor
        	        double total = Double.parseDouble(respuesta.split(":")[1]);
        	        
        	        inputTotal.setText(String.valueOf(total));

        		}	
        	}
    		
    		break;
    	case "Cancelar":
    		modalTotal.setVisible(false);
    		break;
    	case "Con Tarjeta":
    		modalTotal.setVisible(false);
    		socketManager.enviar("CLIENTE:PAGAR PEDIDO:"+mesaId.getText());
    		LimpiarTabla();
    		mesaId.setText("BARRA");
    		break;
    	}
    }
    
    @FXML
    private void PagarEfectivo() {
    	double importe = Double.parseDouble(inputImporte.getText());
    	double total = Double.parseDouble(inputTotal.getText());
    	double cambio = importe - total;
    	
    	inputCambio.setText(String.valueOf(cambio));
    	
    	
    }
    
    
}