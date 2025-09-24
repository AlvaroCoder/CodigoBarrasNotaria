package view.auth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Register extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Registro de Usuario");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/authentication/signup.fxml"));
        BorderPane root = loader.load();

        Scene scene = new Scene(root, 800, 500);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
