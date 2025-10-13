package controller;

import config.ToastAlerts;
import entities.PDF;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class DashboardControllerCopyDocuments {

    @FXML
    private TextField txtInputIdUsb;

    @FXML
    private TextField outputDirectoryUsb;

    @FXML
    private Button btnBuscarRuta;

    @FXML
    private Button btnCopiarDocs;

    @FXML
    private void initialize(){
       btnBuscarRuta.setOnAction(e->seleccionarCarpeta());
       btnCopiarDocs.setOnAction(e->copiarDocumentos());
    }

    private void seleccionarCarpeta(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta de destino");

        Stage stage = (Stage) btnBuscarRuta.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            outputDirectoryUsb.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void copiarDocumentos(){
        String idUsb = txtInputIdUsb.getText();
        if (idUsb.isEmpty()){
            ToastAlerts.warning("Incompleto", "Complete los campos vacíos");
            return;
        }
        try {
            PDF.renderProject(Integer.parseInt(idUsb), outputDirectoryUsb.getText());
            ToastAlerts.success("Exito", "Se copiaron con exitos los documentos");
        } catch (Exception e){
            System.out.println("e = " + e);
            ToastAlerts.error("Error", "Ocurrio un error al copiar los documentos");
        }
    }
}
