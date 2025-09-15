package view.auth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Login extends Application   {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Inicio de Sesion del Usuario");
        // Componentes en la parte derecha
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        BorderPane root = loader.load();

        // Crear la escena
        Scene scene = new Scene(root, 800, 500);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
