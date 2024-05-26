package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Cliente.TPV;
import ViewModel.Perfil;
import ViewModel.Producto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class CrearProductoController {
	
	private SocketManager socketManager=TPV.getSocketManager();;
	
	@FXML
	private TableView<Producto> tabProductos;
	
	@FXML
	private TableColumn<Producto, String> tabNombre;
	 
	@FXML
	private TableColumn<Producto, String> tabCategoria;
	
	@FXML
	private TableColumn<Producto, String> tabDescripcion;
	
	@FXML
	private TableColumn<Producto, Double> tabPrecio;

	@FXML
	private TextField inputNombre;
	
	@FXML
	private TextField inputPrecio;
	
	@FXML
	private ChoiceBox choiceCategoria;
	
	@FXML
	private TextArea areaDescr;
	
	@FXML
	private Text textWrong;
	
	@FXML
	private Button crearBoton;
	
	List<Producto> productos = new ArrayList<>();
	
    @FXML
    public void initialize() {
    	
    	List<String> categorias = new ArrayList<>();
        categorias.add("Desayunos");
        categorias.add("Bebidas");
        categorias.add("Licores");
        choiceCategoria.getItems().addAll(categorias);
        choiceCategoria.setValue("Elige categoría"); // Valor por defecto
    	try {
            
            
            MostrarTabla();
            
            //Por cada click en celda, rellena los inputs
            tabProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue != null) {
                    Producto selectedProducto = newValue;
                   
                    inputNombre.setText(selectedProducto.getNombre());
                    choiceCategoria.setValue(selectedProducto.getCategoria());
                    inputPrecio.setText(String.valueOf(selectedProducto.getPrecio())); 
                    areaDescr.setText(selectedProducto.getDescripcion());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void MostrarTabla() throws IOException {
    	tabProductos.setVisible(true);
    	crearBoton.setVisible(false);
    	areaDescr.setDisable(true);
    	areaDescr.setEditable(false);
    	
    	//Limpiar tabla si hay celdas llenas
    	if(tabProductos.getColumns().size()>0) {
    		productos.clear();
    		tabProductos.getItems().clear();
    	}
    	
    	socketManager.enviar("CLIENTE:PRODUCTO"); // Envía un mensaje al servidor

        String respuesta = socketManager.recibir(); // Recibe el mensaje del servidor
        System.out.println(respuesta);

        String[] partes = respuesta.split(":");
        int contador = Integer.parseInt(partes[1]);

        for (int i = 0; i < contador; i++) {
            respuesta = socketManager.recibir(); // Recibe cada perfil
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
        
        //Rellenar tabla
    	tabNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("Nombre"));
        tabCategoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("Categoria"));
        tabDescripcion.setCellValueFactory(new PropertyValueFactory<Producto, String>("Descripcion"));
        tabPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("Precio"));
        tabProductos.getItems().addAll(productos);
    }
    
    
   
    @FXML
    public void CambiarAAdministracion() throws IOException{
    	tabProductos.getItems().clear();
    	TPV.setRoot("administracion", 600, 350);
    }
    
    @FXML
    public void CrearNuevoProducto() {
    	tabProductos.setVisible(false);
    	areaDescr.setDisable(false);
    	areaDescr.setEditable(true);
    	crearBoton.setVisible(true);
    }
    
    @FXML
    public void CrearProducto() {
    	if(inputNombre.getText().equals("") || choiceCategoria.getValue().toString().equals("Elige categoría") || 
    			inputPrecio.getText().equals("") || areaDescr.getText().equals("")) {
    		textWrong.setVisible(true);
    	}
    	else {
    		
   			Producto nuevoProducto = new Producto(inputNombre.getText(), areaDescr.getText(), choiceCategoria.getValue().toString(), 
   					Double.parseDouble(inputPrecio.getText()));
   			System.out.println(nuevoProducto.getAll());
    			
    		socketManager.enviar("CLIENTE:CREAR PRODUCTO:"+nuevoProducto.getAll());

    	}

    }
}