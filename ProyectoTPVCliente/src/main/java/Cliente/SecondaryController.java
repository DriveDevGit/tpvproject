package Cliente;

import java.io.IOException;

import Cliente.TPV;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import atlantafx.base.theme.Styles;

public class SecondaryController {

    @FXML
    private Button exitButton;
    
    @FXML
    private TextArea panelOperaciones;
    
    @FXML
    private AnchorPane panePrincipal;
    
    private String paneOperacionesText;
    private String operador = "";
    private Double numero1 = null;
        
    @FXML
    public void initialize() {
        //Inicialice aquí cualquier objeto que sea necesario
    	panelOperaciones.textProperty().addListener((observable, oldValue, newValue) -> {
            paneOperacionesText = newValue;
            exitButton.getStyleClass().add(Styles.DANGER);
            exitButton.setContentDisplay(ContentDisplay.RIGHT);
            exitButton.setMnemonicParsing(true);
        });
    }
    
    @FXML
    private void Atrás() throws IOException {
    	TPV.setRoot("primary");
    }
    
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
    		TPV.setRoot("primary");
    		break;
    	}
        
    }
}