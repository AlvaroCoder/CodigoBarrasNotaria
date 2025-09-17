package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private VBox container;

    @FXML
    private TopbarController topbarController;

    @FXML
    private void initialize(){
        loadTopBar();
        loadView("/fxml/dashboard/homeView.fxml");
    }

    private void loadTopBar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/topBar.fxml"));
            Node top = loader.load();
            topbarController = loader.getController();
            topbarController.setDashboardController(this);
            mainLayout.setTop(top);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar topBar.fxml");
        }
    }

    public void loadView(String path){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Node clienteView = loader.load();
            container.getChildren().clear();
            container.getChildren().add(clienteView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
