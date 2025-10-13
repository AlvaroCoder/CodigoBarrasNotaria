package controller;

import controller.components.FormCreateClientController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ControllerParent {

    private DashboardControllerHome dashboardControllerHome;
    private FormCreateClientController formClientController;

    @FXML
    private BorderPane containerHome;

    @FXML
    public void initialize(){
        loadViewHome();
    }


    public void loadViewHome(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard/homeView.fxml"));
            Node fxmlHome = loader.load();

            dashboardControllerHome = loader.getController();
            dashboardControllerHome.setContainerController(this);

            this.containerHome.setCenter(fxmlHome);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setCenter(Node node) {
        containerHome.setCenter(node);
    }

    public void loadFormCreateClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard/components/formCreateClient.fxml"));
            Node fxmlForm = loader.load();

            this.formClientController = loader.getController();
            this.formClientController.setContainerController(this);

            containerHome.setCenter(fxmlForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
