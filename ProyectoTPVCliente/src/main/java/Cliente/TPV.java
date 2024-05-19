package Cliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Cliente.SocketManager;

import java.io.IOException;



/**
 * JavaFX App
 */
public class TPV extends Application {

    private static Scene scene;
    private static SocketManager socketManager;
    private static String rolManager;

    @Override
    public void start(Stage stage) throws IOException {
    	socketManager = new SocketManager();
    	rolManager = "";
    	//Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
        scene.getStylesheets().add(getClass().getResource("/com/Cliente/dash.css").toExternalForm());
    }
    
    public static SocketManager getSocketManager() {
        return socketManager;
    }
    
    public static String getRolManager() {
    	return rolManager;
    }
    
    public static void setRolManager(String rol) {
    	rolManager = rol;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(TPV.class.getResource("/com/Cliente/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    static void setRoot(String fxml, double width, double height) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
    }

    public static void main(String[] args) {
        launch();
    }

}