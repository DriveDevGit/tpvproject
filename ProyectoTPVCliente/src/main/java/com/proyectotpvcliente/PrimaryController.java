package com.proyectotpvcliente;

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
    private void login() throws IOException {
    	String usuario = usuarioTextfield.getText();
    	String contraseña = passwordTextfield.getText();
    	if(usuario.equals("")) usuario = " ";
    	if(contraseña.equals("")) contraseña = " ";
    	try {
            Socket socket = new Socket("localhost", 9090);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println("CLIENTE:LOGIN:"+usuario+";"+contraseña); // Envía un mensaje al servidor 1#

            // Recibir la respuesta del servidor
            String respuesta = in.readLine(); // 1# Recibe el mensaje del servidor
            String mensaje = respuesta.split(":")[1];
            
            if(mensaje.equals("LOGIN CORRECTO")) {
            	paneLogin.setVisible(true);
            }
            else {
            	wrongLoginText.setVisible(true);
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void CambiarATpv(MouseEvent event) throws IOException {
    	TPV.setRoot("secondary");	
    }
 
}
