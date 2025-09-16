package controller;

import dao.impl.UsbDaoImpl;
import entities.Pdf;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private void initialize(){
        btnProcess.setOnAction(e->processDocument());
    }

    private void processDocument(){
        String inputDir = txtInputDir.getText();
        String outputDirPdf = txtOutputDirPdf.getText();
        String outputDirTxt = txtOutputDirTxt.getText();
        if (inputDir.isEmpty() || outputDirPdf.isEmpty() || outputDirTxt.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos vacios", "Ingresa todas las rutas");
            return;
        }
        try {
            Pdf pdf = new Pdf(inputDir, outputDirPdf, outputDirTxt);

            String usbId = pdf.saveInfoUsb(1);
            String recordId = pdf.saveRecord("Registro 1", "Información de un registro", 1, usbId);
            UsbDaoImpl usbDaoImpl = new UsbDaoImpl();
            String pdfPassword = usbDaoImpl.findOne(usbId).getPdfPassword();

            pdf.processPdfs(recordId, pdfPassword);

            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Se procesó la información correctamente.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error al procesar", "Ocurrió un error: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
