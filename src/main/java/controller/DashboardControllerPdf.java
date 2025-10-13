package controller;

import config.ToastAlerts;
import entities.PDF;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private void initialize(){

    }

    @FXML
    private void handleClickGeneratePdf(){
       try{
            String id = txtClaveId.getText();
            String passwordPdf = txtContrasennadPdf.getText();

             byte[] pdfByte =PDF.fromTxtToPdf(id,passwordPdf);

             Path tempFile = Files.createTempFile("pdfPreview_",".pdf");
             Files.write(tempFile, pdfByte);
             renderPreviewPdf(tempFile);

             ToastAlerts.success("Exito", "Se genero el documento PDF");
             System.out.println("PDF generado en : "+tempFile.toAbsolutePath());
       } catch (Exception e){
           System.out.println("Error = " + e);
           ToastAlerts.error("Error",e.getMessage());
       }
    }


    private void renderPreviewPdf(Path pathPdf){
        try (PDDocument document = PDDocument.load(pathPdf.toFile())){
            PDFRenderer renderer = new PDFRenderer(document);

            BufferedImage bim = renderer.renderImageWithDPI(0, 150);

            Image fxImage = SwingFXUtils.toFXImage(bim, null);
            pdfPreviewImage.setImage(fxImage);
        } catch (IOException e){
            System.out.println("e = " + e);
        }
    }

}
