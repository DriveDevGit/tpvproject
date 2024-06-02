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
import ViewModel.Mesa;
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

public class CrearMesaController {
	
	private SocketManager socketManager=TPV.getSocketManager();;
	
	@FXML
	private TableView<Mesa> tabMesas;
	
	@FXML
	private TableColumn<Mesa, String> tabTipo;
	 
	@FXML
	private TableColumn<Mesa, String> tabNumero;
	
	@FXML
	private TableColumn<Mesa, String> tabOcupado;

	@FXML
	private TextField inputNumero;
	
	@FXML
	private Text textWrong;
	
	@FXML
	private ChoiceBox choiceTipo;
	
	List<Mesa> mesas = new ArrayList<>();
	
	private static String tipoInicializar;
	private static int numeroMesaInicializar;
	
    @FXML
    public void initialize(){
    	
    	List<String> tipos = new ArrayList<>();
        tipos.add("Mesa");
        tipos.add("Barra");
        choiceTipo.getItems().addAll(tipos);
        choiceTipo.setValue("Elige tipo"); // Valor por defecto
    	try {
            
            
            MostrarTabla();
            
            //Por cada click en celda, rellena los inputs
            tabMesas.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue != null) {
                    Mesa selectedMesa = newValue;
                   
                    inputNumero.setText(String.valueOf(selectedMesa.getNumero()));
                    choiceTipo.setValue(selectedMesa.getNombre());
                }
            });
            
            tabMesas.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Mesa selectedMesa = tabMesas.getSelectionModel().getSelectedItem();
                    if (selectedMesa != null) {
                        String tipo = selectedMesa.getNombre();
                        int numero = selectedMesa.getNumero();

                        System.out.println("Tipo: " + tipo + ", Número: " + numero);
                        
                        try {
                        	tipoInicializar = tipo;
                        	numeroMesaInicializar = numero;
							TPV.setRoot("secondary", 840, 600);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static String getTipoInicializar() {
		return tipoInicializar;
	}



	public static int getNumeroMesaInicializar() {
		return numeroMesaInicializar;
	}



	public void MostrarTabla() throws IOException {
    	tabMesas.setVisible(true);
    	
    	//Limpiar tabla si hay celdas llenas
    	if(tabMesas.getColumns().size()>0) {
    		mesas.clear();
    		tabMesas.getItems().clear();
    	}
    	
    	socketManager.enviar("CLIENTE:MESA"); // Envía un mensaje al servidor

        String respuesta = socketManager.recibir(); // Recibe el mensaje del servidor
        System.out.println(respuesta);

        String[] partes = respuesta.split(":");
        int contador = Integer.parseInt(partes[1]);

        for (int i = 0; i < contador; i++) {
            respuesta = socketManager.recibir(); // Recibe cada perfil
            partes = respuesta.split(":");
            Mesa mesa = new Mesa();
            String[] datosMesa = partes[1].split(";");

            mesa.setNombre(datosMesa[1]);
            mesa.setNumero(Integer.parseInt(datosMesa[2]));
            mesa.setOcupado(Integer.parseInt(datosMesa[3]));

            mesas.add(mesa);

            System.out.println(mesa.getNombre() + ";" + mesa.getNumero());
        }
        
        //Rellenar tabla
    	tabTipo.setCellValueFactory(new PropertyValueFactory<Mesa, String>("Nombre"));
        tabNumero.setCellValueFactory(new PropertyValueFactory<Mesa, String>("Numero"));
        tabOcupado.setCellValueFactory(new PropertyValueFactory<Mesa, String>("Ocupado"));
        tabMesas.getItems().addAll(mesas);
    }
    
    
   
    @FXML
    public void CambiarAAdministracion() throws IOException{
    	tabMesas.getItems().clear();
    	TPV.setRoot("administracion", 600, 350);
    }
    
    @FXML
    public void CrearMesa() throws IOException {
    	if(choiceTipo.getValue().toString().equals("Elige tipo") || 
    			inputNumero.getText().equals("")) {
    		textWrong.setVisible(true);
    	}
    	else {
    		
   			Mesa nuevoProducto = new Mesa(choiceTipo.getValue().toString(), 
   					Integer.parseInt(inputNumero.getText()));
   			System.out.println(nuevoProducto.getAll());
    			
    		socketManager.enviar("CLIENTE:CREAR MESA:"+nuevoProducto.getAll());
    		mesas.clear();
    		tabMesas.getItems().clear();
    		MostrarTabla();
    	}

    }
}