package entities;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import dao.impl.PageDaoImpl;
import dao.impl.RecordDaoImpl;
import dao.impl.UsbDaoImpl;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

public class Pdf {
    private final String pdfDirectory;
    private final String processedPdfDirectory;
    private final String txtDirectory;

    public Pdf(){
        this.pdfDirectory = "";
        this.processedPdfDirectory = "";
        this.txtDirectory = "";
    }

    public Pdf(String pdfDirectory,String processedPdfDirectory, String txtDirectory){
        this.pdfDirectory=pdfDirectory;
        this.processedPdfDirectory=processedPdfDirectory;
        this.txtDirectory=txtDirectory;
    }

    public byte[] fromTxtToPdf(String pageId) throws IOException {
        PageDaoImpl pageDaoImpl = new PageDaoImpl();
        Page page =pageDaoImpl.findOne(pageId);
        String txtPath = page.getPath();
        String txtBase64 = new String(Files.readAllBytes(Paths.get(txtPath)));
        return Base64.getDecoder().decode(txtBase64);
    }

    public String saveInfoUsb(Integer clientId){
        String usbId = UUID.randomUUID().toString();
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = LocalDateTime.now();
        String pdfPassword = UUID.randomUUID().toString().substring(0,16);
        UsbDaoImpl usbDaoImpl = new UsbDaoImpl();
        usbDaoImpl.insertOne(new Usb(usbId,clientId,creationDate,lastModifiedDate,pdfPassword));
        return usbId;
    }

    public String saveRecord(String name, String description, Integer clientId, String usbId){
        RecordDaoImpl recordDaoImpl = new RecordDaoImpl();
        String recordId = UUID.randomUUID().toString();
        recordDaoImpl.insertOne(new Record(recordId,name,description,clientId,usbId));
        return recordId;
    }

    public void processPdfs(String recordId, String pdfPassword){

        File docsDir= new File(getPdfDirectory());

        File[] docs = docsDir.listFiles((dir,name)->name.toLowerCase().endsWith(".pdf"));

//        String userPassword= UUID.randomUUID().toString();
//
//        String ownerPassword="prueba123";
        //System.out.println(pdfPassword);

        WriterProperties props = new WriterProperties().setStandardEncryption(
                pdfPassword.getBytes(StandardCharsets.UTF_8),
                pdfPassword.getBytes(StandardCharsets.UTF_8),
                EncryptionConstants.ALLOW_PRINTING,
                EncryptionConstants.ENCRYPTION_AES_128
        );

        if (docs != null){
            for (File doc: docs){
                String outputPdf=String.format("%s/%s",
                        getProcessedPdfDirectory(),
                        doc.getName()); //path

                try(PdfReader reader = new PdfReader(doc);
                    PdfWriter writer = new PdfWriter(outputPdf,props);
                    PdfDocument pdf = new PdfDocument(reader,writer);
                    Document document = new Document(pdf);
                ){
                    //AGREGAR EL CODIGO DE BARRAS
                    Barcode128 barcode = new Barcode128(pdf);

                    String pageId=UUID.randomUUID().toString().substring(0,16);
                    barcode.setCode(pageId);//id

                    barcode.setFont(null);
                    barcode.setBarHeight(20);
                    barcode.setX(0.5f);


                    Image barcodeImage = new Image(barcode.createFormXObject(pdf));

                    int page = 1;
                    float x = 175;
                    float y = 10;

                    PdfCanvas canvas = new PdfCanvas(pdf.getPage(page));
                    barcodeImage.setFixedPosition(page,x,y);
                    document.add(barcodeImage);

                    //CONVERTIR A BYTES EL CONTENIDO DEL DOCUMENTO
                    document.close();

                    File processedFile = new File(outputPdf);
                    FileInputStream fis = new FileInputStream(processedFile);
                    byte[] pdfBytes = new byte[(int) processedFile.length()];
                    fis.read(pdfBytes);
                    fis.close();

                    String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
                    String txtName=doc.getName().split("\\.")[0]+".txt";

                    String txtPath = String.format("%s/%s",
                            getTxtDirectory(),
                            txtName);

                    File txtFile = new File(txtPath);

                    try(FileWriter fw = new FileWriter(txtFile)){
                        fw.write(pdfBase64);
                    }

                    //SUBIR A LA BASE DE DATOS
                    PageDaoImpl pageDaoImpl = new PageDaoImpl();
                    pageDaoImpl.insertOne(new Page(pageId,null,recordId,txtPath));

                } catch (Exception e){

                    System.out.println("Ocurrio un error");
                }
            }

        }

    }

    public String getPdfDirectory() {
        return pdfDirectory;
    }

    public String getProcessedPdfDirectory() {
        return processedPdfDirectory;
    }

    public String getTxtDirectory() {
        return txtDirectory;
    }

//    public void setDirectory(String directory) {
//        this.directory = directory;
//    }
}
