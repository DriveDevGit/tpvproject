package Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Cliente.TPV;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SecondaryController {
	
	private SocketManager socketManager = TPV.getSocketManager();
	
    @FXML
    private Button exitButton;
    
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
    public void initialize() {
        
    	//Inicializar la tabla
    	udsTabla = new TableColumn<>("Unidades");
        udsTabla.setCellValueFactory(new PropertyValueFactory<>("unidades"));

        nombreTabla = new TableColumn<>("Nombre");
        nombreTabla.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        precioTabla = new TableColumn<>("Precio Total");
        precioTabla.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));

        tabProductos.getColumns().addAll(udsTabla, nombreTabla, precioTabla);
    	
        //Inicializar el panel de operaciones
    	panelOperaciones.textProperty().addListener((observable, oldValue, newValue) -> {
            paneOperacionesText = newValue;
            exitButton.setContentDisplay(ContentDisplay.RIGHT);
            exitButton.setMnemonicParsing(true);
        });
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
    		socketManager.enviar("CLIENTE:TPV Categoria:Bebidas");
    		RecibirProductos();
    		break;
    	case "Desayunos":
    		socketManager.enviar("CLIENTE:TPV Categoria:Desayunos");
    		RecibirProductos();
    		break;
    	case "Licores":
    		socketManager.enviar("CLIENTE:TPV Categoria:Licores");
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
	             }
	         });

	         row++; //Añade una nueva fila para el siguiente registro
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
    
}