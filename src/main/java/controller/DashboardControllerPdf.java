package controller;

import config.ToastAlerts;
import entities.PDF;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DashboardControllerPdf {

    @FXML
    private ImageView pdfPreviewImage;

    @FXML
    private TextField txtClaveId;

    @FXML
    private TextField txtContrasennadPdf;

    @FXML
    private Button btnGenerarPdf;

    @FXML
    private Label lblPreviewPlaceholder;

    @FXML
    private Button btnZoomOut;

    @FXML
    private Button btnZoomIn;
    // =========================
    // ZOOM STATE
    // =========================
    private double zoomFactor = 1.0;
    private static final double ZOOM_STEP = 0.15;
    private static final double MIN_ZOOM = 0.5;
    private static final double MAX_ZOOM = 3.0;

    @FXML
    private void initialize() {
        resetZoom();
    }

    @FXML
    private void handleClickGeneratePdf() {
        try {
            String id = txtClaveId.getText();
            String passwordPdf = txtContrasennadPdf.getText();

            byte[] pdfByte = PDF.fromTxtToPdf(id, passwordPdf);

            Path tempFile = Files.createTempFile("pdfPreview_", ".pdf");
            Files.write(tempFile, pdfByte);

            renderPreviewPdf(tempFile);
            resetZoom();

            lblPreviewPlaceholder.setVisible(false);

            ToastAlerts.success("Éxito", "Se generó el documento PDF");
            System.out.println("PDF generado en: " + tempFile.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            ToastAlerts.error("Error", e.getMessage());
        }
    }

    private void renderPreviewPdf(Path pathPdf) {
        try (PDDocument document = PDDocument.load(pathPdf.toFile())) {

            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage bim = renderer.renderImageWithDPI(0, 150);

            Image fxImage = SwingFXUtils.toFXImage(bim, null);
            pdfPreviewImage.setImage(fxImage);

        } catch (IOException e) {
            System.out.println("Error renderizando PDF: " + e.getMessage());
        }
    }

    // =========================
    // ZOOM CONTROLS
    // =========================

    @FXML
    private void handleZoomIn() {
        if (zoomFactor < MAX_ZOOM) {
            zoomFactor += ZOOM_STEP;
            applyZoom();
        }
    }

    @FXML
    private void handleZoomOut() {
        if (zoomFactor > MIN_ZOOM) {
            zoomFactor -= ZOOM_STEP;
            applyZoom();
        }
    }

    private void applyZoom() {
        pdfPreviewImage.setScaleX(zoomFactor);
        pdfPreviewImage.setScaleY(zoomFactor);
    }

    private void resetZoom() {
        zoomFactor = 1.0;
        applyZoom();
    }
}