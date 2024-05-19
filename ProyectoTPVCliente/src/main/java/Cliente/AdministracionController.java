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

public class AdministracionController{
	
	    
    public void CambiarATpv(MouseEvent event) throws IOException {
    	TPV.setRoot("secondary", 840, 600);
    }
    
    public void CambiarACrearPerfil(MouseEvent event) throws IOException {
    	TPV.setRoot("crearperfil", 840, 600);	
    }
    
    public void CambiarACrearProducto(MouseEvent event) throws IOException {
    	TPV.setRoot("crearproducto", 840, 600);	
    }
 
}
