package view.dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Home extends Application {
    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboardContainer.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Dashboard General");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error al cargar el formulario fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
