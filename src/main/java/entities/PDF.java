package entities;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import config.Settings;
import dao.impl.RecordDaoImpl;
import dao.impl.PageDaoImpl;
import dao.impl.SectionDaoImpl;
import dao.impl.UsbDaoImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

/*
* A3 == 2-A4
* A2 == 4-A4
* A1 == 8-A4
* A0 == 16-A4
* */

public class PDF {
    private final String pdfDirectory;
    private final String processedPdfDirectory;
    private final String txtDirectory;

    public PDF(String pdfDirectory,String processedPdfDirectory, String txtDirectory){
        this.pdfDirectory=pdfDirectory;
        this.processedPdfDirectory=processedPdfDirectory;
        this.txtDirectory=txtDirectory;
    }
    //para mover el proyeto al directorio del usb
    public static void renderProject(int usbId,String destinyPath) throws Exception{
        UsbDaoImpl usbDaoImpl = new UsbDaoImpl();
        Usb project=usbDaoImpl.findOne(usbId);
        File projectPath = new File(Settings.MAIN_DIRECTORY,project.getPath());
        File destinyProjectPath = new File(destinyPath,project.getPath());
        if (!destinyProjectPath.mkdir()){
            throw new Exception("Ocurrio un error al crear la carpeta del proyeto");
        }
        for (String record : projectPath.list()){
            File recordPath = new File(projectPath,record);
            File destinyRecordPath = new File(destinyProjectPath,record);
            if (!destinyRecordPath.mkdir()){
                throw new Exception("Ocurrio un error al crear la carpeta de un expediente");
            }

            for (String section: recordPath.list()){
                File sectionPath = new File(recordPath,section);
                File destinySectionPath = new File(destinyRecordPath,section);
                if (!destinySectionPath.mkdir()){
                    throw new Exception("Ocurrio un error al crear la carpeta de una seccion");
                }
                for (String page: sectionPath.list()){
                    File pagePath = new File(sectionPath,page);
                    File destinyPagePath = new File(destinySectionPath,page.split("\\.")[0]+".pdf");
                    try{
                        String txtBase64 = new String(Files.readAllBytes(Paths.get(pagePath.getAbsolutePath())));
                        byte[] pdfBytes = Base64.getDecoder().decode(txtBase64);
                        Files.write(Paths.get(destinyPagePath.getAbsolutePath()),pdfBytes);
                    } catch (Exception e){
                        e.printStackTrace();
                        System.out.println("Ocurrio un error al convertir el documento txt a pdf");
                    }

                }
            }
        }

    }

    public static byte[] fromTxtToPdf(String serialNumber, String password) throws Exception {
        PageDaoImpl pageDaoImpl = new PageDaoImpl();
        Page page =pageDaoImpl.findOne(serialNumber);
        String txtPath= Settings.MAIN_DIRECTORY;
        try{
            txtPath=txtPath.concat("/"+page.getPath());
        } catch (Exception e){
            throw new Exception("Número de serie incorrecto.");
        }
        String txtBase64 = new String(Files.readAllBytes(Paths.get(txtPath)));
        byte[] pdfBytes = Base64.getDecoder().decode(txtBase64);
        ReaderProperties props = new ReaderProperties().setPassword(password==null?null:password.getBytes());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfReader reader = new PdfReader(new ByteArrayInputStream(pdfBytes),props);
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(reader,writer);
            ){
            int pages = pdfDoc.getNumberOfPages();
        } catch (Exception e){
            throw new Exception("Contraseña incorrecta.");
        }
        return baos.toByteArray();
    }

    public Integer saveUsbInfo(Integer clientId,String description,String path,String pdfPassword){
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = LocalDateTime.now();
        UsbDaoImpl usbDaoImpl = new UsbDaoImpl();
        return usbDaoImpl.insertOne(new Usb(description,clientId,creationDate,lastModifiedDate,pdfPassword,path));
    }

    public Integer saveRecordInfo(String name, String description, int usbId,String path){
        RecordDaoImpl recordDaoImpl = new RecordDaoImpl();
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = LocalDateTime.now();

        return recordDaoImpl.insertOne(new Record(name,description,usbId,creationDate,lastModifiedDate,path));
    }

    public Integer saveSectionInfo(String name, int recordId,String path){
        SectionDaoImpl sectionDaoImpl = new SectionDaoImpl();
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = LocalDateTime.now();

        return sectionDaoImpl.insertOne(new Section(name,recordId,creationDate,lastModifiedDate,path));
    }

    public void savePageInfo(int sectionId, String path){
        String serialNumber = UUID.randomUUID().toString().substring(0,16);
        PageDaoImpl pageDaoImpl = new PageDaoImpl();
        pageDaoImpl.insertOne(new Page(serialNumber,sectionId,path));
    }

    //el outputPdf y el outputTxt deben generarse dentro del metodo, no se deben pedir
    public void processPdf(File doc, File processedSectionPath, File txtSectionPath,
                           WriterProperties props,
                           File pathToDb, int sectionId) throws Exception{
        try(PdfReader reader = new PdfReader(doc);
            PdfDocument pdf = new PdfDocument(reader);
        ){
            int pageTotal = pdf.getNumberOfPages();
            for (int i = 1; i<=pageTotal;i++){

                File outputFile = new File(processedSectionPath,doc.getName().split("\\.")[0]+"_page_"+i+".pdf");
                File outputTxt = new File(txtSectionPath,doc.getName().split("\\.")[0]+"_page_"+i+".txt");
                try(PdfWriter writer = new PdfWriter(outputFile.getAbsolutePath(),props);
                    PdfDocument newPdf = new PdfDocument(writer);
                    Document document = new Document(newPdf);
                ){
                    pdf.copyPagesTo(i,i,newPdf);


                    Barcode128 barcode = new Barcode128(newPdf);

                    String serialNumber=UUID.randomUUID().toString().substring(0,16);
                    barcode.setCode(serialNumber);//id

                    barcode.setFont(null);
                    barcode.setBarHeight(20);
                    barcode.setX(0.5f);

                    Image barcodeImage = new Image(barcode.createFormXObject(ColorConstants.BLACK,ColorConstants.BLACK,newPdf));

                    Rectangle pageSize = newPdf.getPage(1).getPageSize();

                    float x = (pageSize.getWidth()/2) -(barcodeImage.getImageWidth()/2) ;
                    float y = 10;

                    barcodeImage.setFixedPosition(1,x,y);
                    document.add(barcodeImage);
                    document.close();

                    FileInputStream fis = new FileInputStream(outputFile);
                    byte[] pdfBytes = new byte[(int) outputFile.length()];
                    fis.read(pdfBytes);
                    fis.close();

                    String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
//                    String txtName=doc.getName().split("\\.")[0]+".txt";

                    String txtPathToDb = new File(pathToDb,outputTxt.getName()).getPath().replace("\\","/");
                    System.out.println(txtPathToDb);
                    try(FileWriter fw = new FileWriter(outputTxt)){
                        fw.write(pdfBase64);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    savePageInfo(sectionId,txtPathToDb);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Ocurrio un error procesando el pdf");
        }
    }


    public void trackFiles(int clientId) throws Exception {
        File principalPath = new File(getPdfDirectory());
        File processedPath = new File(getProcessedPdfDirectory());
        File txtPath = new File(getTxtDirectory());

        if (!principalPath.exists() || !principalPath.isDirectory()) {
            throw new Exception("La ruta principal no existe o no es un directorio: " + principalPath.getAbsolutePath());
        }
        if (!processedPath.exists() && !processedPath.mkdirs()) {
            throw new Exception("No se pudo crear la ruta de procesados: " + processedPath.getAbsolutePath());
        }
        if (!txtPath.exists() && !txtPath.mkdirs()) {
            throw new Exception("No se pudo crear la ruta de TXT: " + txtPath.getAbsolutePath());
        }

        String pdfPassword = UUID.randomUUID().toString().substring(0, 16);

        WriterProperties props = new WriterProperties().setStandardEncryption(
                pdfPassword.getBytes(StandardCharsets.UTF_8),
                pdfPassword.getBytes(StandardCharsets.UTF_8),
                EncryptionConstants.ALLOW_PRINTING,
                EncryptionConstants.ENCRYPTION_AES_128
        );

        String[] projectList = principalPath.list();
        if (projectList == null) {
            throw new Exception("No se pudo listar el contenido de la ruta principal: " + principalPath.getAbsolutePath());
        }

        for (String projectName : projectList) {
            File projectPath = new File(principalPath, projectName);
            if (!projectPath.isDirectory()) continue;

            File projectPathToDb = new File(projectName);
            File processedProjectPath = new File(processedPath, projectName);
            File txtProjectPath = new File(txtPath, projectName);

            System.out.println("Creando carpetas de proyecto: " + processedProjectPath + " | " + txtProjectPath);

            if (!processedProjectPath.exists() && !processedProjectPath.mkdirs()) {
                throw new Exception("Ocurrió un error al crear la carpeta del proyecto: " + processedProjectPath);
            }
            if (!txtProjectPath.exists() && !txtProjectPath.mkdirs()) {
                throw new Exception("Ocurrió un error al crear la carpeta TXT del proyecto: " + txtProjectPath);
            }

            Integer usbId = saveUsbInfo(clientId, null, projectPathToDb.getPath(), pdfPassword);

            String[] recordList = projectPath.list();
            if (recordList == null) continue;

            for (String recordName : recordList) {
                File recordPath = new File(projectPath, recordName);
                if (!recordPath.isDirectory()) continue;

                File recordPathToDb = new File(projectPathToDb, recordName);
                File processedRecordPath = new File(processedProjectPath, recordName);
                File txtRecordPath = new File(txtProjectPath, recordName);

                if (!processedRecordPath.exists() && !processedRecordPath.mkdirs()) {
                    throw new Exception("Ocurrió un error al crear la carpeta del expediente: " + processedRecordPath);
                }
                if (!txtRecordPath.exists() && !txtRecordPath.mkdirs()) {
                    throw new Exception("Ocurrió un error al crear la carpeta TXT del expediente: " + txtRecordPath);
                }

                Integer recordId = saveRecordInfo(recordName, null, usbId, recordPathToDb.getPath().replace("\\", "/"));

                String[] sectionFiles = recordPath.list();
                if (sectionFiles == null) continue;

                for (String sectionFile : sectionFiles) {
                    File sectionPath = new File(recordPath, sectionFile);
                    if (!sectionPath.isFile()) continue; // Ignorar carpetas dentro

                    String sectionName = sectionFile.split("\\.")[0];
                    File sectionPathToDb = new File(recordPathToDb, sectionName);
                    File processedSectionPath = new File(processedRecordPath, sectionName);
                    File txtSectionPath = new File(txtRecordPath, sectionName);

                    if (!processedSectionPath.exists() && !processedSectionPath.mkdirs()) {
                        throw new Exception("Ocurrió un error al crear la carpeta de la sección: " + processedSectionPath);
                    }
                    if (!txtSectionPath.exists() && !txtSectionPath.mkdirs()) {
                        throw new Exception("Ocurrió un error al crear la carpeta TXT de la sección: " + txtSectionPath);
                    }

                    Integer sectionId = saveSectionInfo(sectionName, recordId, sectionPathToDb.getPath().replace("\\", "/"));

                    processPdf(sectionPath, processedSectionPath, txtSectionPath, props, sectionPathToDb, sectionId);
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

}
