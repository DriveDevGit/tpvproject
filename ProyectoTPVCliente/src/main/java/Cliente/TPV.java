package Cliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerLight;

/**
 * JavaFX App
 */
public class TPV extends Application {

    private static Scene scene;
    private static SocketManager socketManager;

    @Override
    public void start(Stage stage) throws IOException {
    	socketManager = new SocketManager();
    	Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }
    
    public static SocketManager getSocketManager() {
        return socketManager;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(TPV.class.getResource("/com/Cliente/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    static void setRoot(String fxml, double width, double height, SocketManager socketManager) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
    }

    public static void main(String[] args) {
        launch();
    }

}