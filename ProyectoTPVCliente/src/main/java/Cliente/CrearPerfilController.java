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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class CrearPerfilController {
	
	private SocketManager socketManager=TPV.getSocketManager();;
	
	@FXML
	private TableView<Perfil> tabPerfiles;
	 
	@FXML
	private TableColumn<Perfil, String> tabDNI;
	
	@FXML
	private TableColumn<Perfil, String> tabUsuario;
	
	@FXML
	private TableColumn<Perfil, String> tabDireccion;

	@FXML
	private TextField inputNombre;
	
	@FXML
	private TextField inputDni;
	
	@FXML
	private TextField inputDir;
	
	@FXML
	private TextField inputTlf;
	
	@FXML
	private TextField inputUsuario;
	
	@FXML
	private TextField inputRol;
	
	@FXML
	private TextField inputPwd;
	
	@FXML
	private TextField inputRepetir;
	
	@FXML
	private Text labelContrasena;
	
	@FXML
	private Text labelRepetir;
	
	@FXML
	private Text textWrong;
	
	@FXML
	private Text textWrongPwd;
	
	@FXML
	private Button crearBoton;
	
	List<Perfil> perfiles = new ArrayList<>();
	
    @FXML
    public void initialize() {

    	try {
            
            MostrarTabla();
            tabPerfiles.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue != null) {
                    Perfil selectedPerfil = newValue;
                   
                    inputNombre.setText(selectedPerfil.getNombre());
                    inputUsuario.setText(selectedPerfil.getUsername()); 
                    inputRol.setText(selectedPerfil.getRol());
                    inputTlf.setText(selectedPerfil.getTelefono());
                    inputDir.setText(selectedPerfil.getDireccion());
                    inputDni.setText(selectedPerfil.getDni());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void MostrarTabla() throws IOException {
    	tabPerfiles.setVisible(true);
    	labelContrasena.setVisible(false);
    	labelRepetir.setVisible(false);
    	inputPwd.setVisible(false);
    	inputRepetir.setVisible(false);
    	crearBoton.setVisible(false);
    	
    	if(tabPerfiles.getColumns().size()>0) {
    		perfiles.clear();
    		tabPerfiles.getItems().clear();
    	}
    	
    	socketManager.enviar("CLIENTE:PERFIL"); // Envía un mensaje al servidor

        String respuesta = socketManager.recibir(); // Recibe el mensaje del servidor
        System.out.println(respuesta);

        String[] partes = respuesta.split(":");
        int contador = Integer.parseInt(partes[1]);

        for (int i = 0; i < contador; i++) {
            respuesta = socketManager.recibir(); // Recibe cada perfil
            partes = respuesta.split(":");
            Perfil perfil = new Perfil();
            String[] datosPerfiles = partes[1].split(";");

            perfil.setNombre(datosPerfiles[1]);
            perfil.setUsername(datosPerfiles[2]);
            perfil.setPassword(datosPerfiles[3]);
            perfil.setRol(datosPerfiles[4]);
            perfil.setTelefono(datosPerfiles[5]);
            perfil.setDireccion(datosPerfiles[6]);
            perfil.setDni(datosPerfiles[7]);

            perfiles.add(perfil);

            System.out.println(perfil.getNombre() + ";" + perfil.getUsername() + ";" + perfil.getPassword() + ";" + perfil.getRol() + ";" + perfil.getTelefono() + ";" + perfil.getDireccion() + ";" + perfil.getDni());
        }
    	tabDNI.setCellValueFactory(new PropertyValueFactory<Perfil, String>("Dni"));
        tabUsuario.setCellValueFactory(new PropertyValueFactory<Perfil, String>("Username"));
        tabDireccion.setCellValueFactory(new PropertyValueFactory<Perfil, String>("Direccion"));
        tabPerfiles.getItems().addAll(perfiles);
    }
    
    
   
    @FXML
    public void CambiarAAdministracion() throws IOException{
    	tabPerfiles.getItems().clear();
    	TPV.setRoot("administracion", 600, 350);
    }
    
    @FXML
    public void CrearNuevoPerfil() {
    	tabPerfiles.setVisible(false);
    	labelContrasena.setVisible(true);
    	labelRepetir.setVisible(true);
    	inputPwd.setVisible(true);
    	inputRepetir.setVisible(true);
    	crearBoton.setVisible(true);
    }
    
    @FXML
    public void CrearPerfil() {
    	if(inputNombre.getText().equals("") || inputDni.getText().equals("") || inputDir.getText().equals("") || inputUsuario.getText().equals("") || inputPwd.getText().equals("")
    			|| inputRepetir.getText().equals("") || inputRol.getText().equals("") || inputTlf.getText().equals("")) {
    		textWrong.setVisible(true);
    		textWrongPwd.setVisible(false);
    	}
    	else {
    		if(!inputPwd.getText().equals(inputRepetir.getText())) {
    			textWrong.setVisible(false);
        		textWrongPwd.setVisible(true);
    		}
    		else {
    			Perfil nuevoPerfil = new Perfil(inputNombre.getText(), inputUsuario.getText(), inputPwd.getText(), 
    					inputRol.getText(), inputTlf.getText(), inputDir.getText(), inputDni.getText());
    			
    			socketManager.enviar("CLIENTE:CREAR PERFIL:"+nuevoPerfil.getAll());
    		}
    	}

    }
}