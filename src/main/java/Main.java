import config.Settings;
import dao.impl.UsbDaoImpl;
import entities.Pdf;

public class Main {
    public static void main(String[] args) {

        Pdf pdf = new Pdf(Settings.PDF_DIRECTORY,
                Settings.PROCESSED_PDF_DIRECTORY,
                Settings.TXT_DIRECTORY);

        String usbId=pdf.saveInfoUsb(1);

        String recordId=pdf.saveRecord("Registro 1","Informacion de un registro",1,usbId);

        UsbDaoImpl usbDaoImpl = new UsbDaoImpl();
        String pdfPassword=usbDaoImpl.findOne(usbId).getPdfPassword();

        pdf.processPdfs(recordId,pdfPassword);
    }
}
