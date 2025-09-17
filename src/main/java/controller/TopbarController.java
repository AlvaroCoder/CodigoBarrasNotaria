package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TopbarController {
    private DashboardController dashboardController;

    @FXML
    private HBox topBar;

    @FXML
    private Label txtDashboard;

    @FXML
    private void  initialize(){
        topBar.getStylesheets().add(getClass().getResource("/css/topbar.css").toExternalForm());
        txtDashboard.setText("Usuario1");
    }

    public void setDashboardController(DashboardController dashboardController){
        this.dashboardController = dashboardController;
    }

    @FXML
    private void handleClienteClick() {
        dashboardController.loadView("/fxml/dashboard/clientView.fxml");
    }

    @FXML
    private void handleDocumentClick(){
        dashboardController.loadView("/fxml/dashboard/documentView.fxml");
    }

    @FXML
    private void handleUsbClick(){
        dashboardController.loadView("/fxml/dashboard/usbView.fxml");
    }

    @FXML
    private void handleClickHome(){
        dashboardController.loadView("/fxml/dashboard/homeView.fxml");
    }

    @FXML
    private void handleClickGenerarPdf(){
        dashboardController.loadView("/fxml/dashboard/pdfView.fxml");
    }
}
