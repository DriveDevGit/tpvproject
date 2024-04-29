package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Cliente.SocketManager;

public class PrimaryController{
	
	@FXML
	private Text wrongLoginText;
	
	@FXML
	private TextField usuarioTextfield;
	
	@FXML
	private PasswordField passwordTextfield;
	
	@FXML
	private Pane paneLogin;
	
	@FXML
	private	Pane panelAdmin;
	
	private SocketManager socketManager;
	
    @FXML
    private void login(){
    	String usuario = usuarioTextfield.getText();
    	String contraseña = passwordTextfield.getText();
    	if(usuario.equals("")) usuario = " ";
    	if(contraseña.equals("")) contraseña = " ";
    	PrintWriter out = null;
    	BufferedReader in = null;	
    	
    	try {
    		socketManager = TPV.getSocketManager();
            socketManager.enviar("CLIENTE:LOGIN:"+usuario+";"+contraseña); // Envía un mensaje al servidor 1#

            // Recibir la respuesta del servidor
            String respuesta = socketManager.recibir();// 1# Recibe el mensaje del servidor
            String mensaje = respuesta.split(":")[1];
            
            if(mensaje.equals("LOGIN CORRECTO")) {
            	paneLogin.setVisible(true);
            }
            else {
            	wrongLoginText.setVisible(true);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void CambiarATpv(MouseEvent event) throws IOException {
    	TPV.setRoot("secondary", 840, 600, socketManager);
    }
    
    @FXML
    public void VisiblePanelAdmin() {
    	panelAdmin.setVisible(true);
    }
    
    public void CambiarACrearPerfil(MouseEvent event) throws IOException {
    	TPV.setRoot("crearperfil", 840, 600, socketManager);	
    }
 
}
