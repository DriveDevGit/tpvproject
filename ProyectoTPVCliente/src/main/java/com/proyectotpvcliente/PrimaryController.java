package com.proyectotpvcliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PrimaryController {
	
	@FXML
    private TextField usuarioId;
	
	@FXML
    private PasswordField passwordId;
	
	@FXML
	private Text mensajeError;
    
    @FXML
    private void comprobarContrasena() {
    	try {
    		Socket socket = new Socket("localhost", 9090);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String texto = usuarioId.getText();
            String contraseña = passwordId.getText();
            
            out.println("Comunicando con el servidor"); // Envía mensaje al servidor 1#
            out.println(usuarioId.getText()); // Envía el usuario al servidor 2#
            out.println(passwordId.getText()); // Envía el contraseña al servidor 3#
            
            boolean response = Boolean.parseBoolean(in.readLine()); // 1# Recibe el booleano del servidor

            in.close();
            out.close();
            
            if(response==true) {
            	App.setRoot("secondary");
            }
            else {
            	mensajeError.setVisible(true);
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
}
