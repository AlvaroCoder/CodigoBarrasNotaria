package controller;

import config.ToastAlerts;
import dao.impl.UsbDaoImpl;
import entities.PDF;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class DashboardControllerHome {
    @FXML
    private TextField txtInputDir;

    @FXML
    private TextField txtOutputDirPdf;

    @FXML
    private TextField txtOutputDirTxt;

    @FXML
    private Button btnProcess;

    @FXML
    private Button btnBuscarRutaInput;

    @FXML
    private Button btnBuscarRutaOutputPdf;

    @FXML
    private Button btnBuscarRutaOutputTxt;

    @FXML
    private void initialize(){
        btnProcess.setOnAction(e->processDocument());
        btnBuscarRutaInput.setOnAction(e->seleccionarCarpeta(btnBuscarRutaInput, txtInputDir));
        btnBuscarRutaOutputPdf.setOnAction(e->seleccionarCarpeta(btnBuscarRutaOutputPdf, txtOutputDirPdf));
        btnBuscarRutaOutputTxt.setOnAction(e->seleccionarCarpeta(btnBuscarRutaOutputTxt, txtOutputDirTxt));
    }

    private void seleccionarCarpeta(Button btnBuscarRuta, TextField outputDirectoryUsb){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta de destino");

        Stage stage = (Stage) btnBuscarRuta.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            outputDirectoryUsb.setText(selectedDirectory.getAbsolutePath());
        }
    }


    private void processDocument(){
        String inputDir = txtInputDir.getText();
        String outputDirPdf = txtOutputDirPdf.getText();
        String outputDirTxt = txtOutputDirTxt.getText();
        if (inputDir.isEmpty() || outputDirPdf.isEmpty() || outputDirTxt.isEmpty()) {
            ToastAlerts.warning("Campos Vacios", "Ingrese todas las rutas");
            return;
        }

        int idCliente =1;
        PDF pdf = new PDF(
                inputDir,
                outputDirPdf,
                outputDirTxt);

        try{
            pdf.trackFiles(idCliente);
        } catch (Exception e){
            ToastAlerts.error("Error", "Ocurrio un error al procesar los PDF");
        }

    }

}
