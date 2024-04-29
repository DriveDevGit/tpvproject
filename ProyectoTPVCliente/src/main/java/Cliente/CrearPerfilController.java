package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import Cliente.TPV;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class CrearPerfilController {
	
	private SocketManager socketManager;
	
    @FXML
    public void initialize() {

		try {
			socketManager = TPV.getSocketManager();
            socketManager.enviar("CLIENTE:PERFIL:Hola"); // Envía un mensaje al servidor 1#

            // Recibir la respuesta del servidor
            String respuesta = socketManager.recibir();// 1# Recibe el mensaje del servidor
            String mensaje = respuesta.split(":")[1];
            System.out.println(mensaje);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

    }
   
}