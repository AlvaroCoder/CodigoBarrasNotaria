import config.Settings;
import dao.impl.UsbDaoImpl;
import entities.Pdf;

public class Main {
    public static void main(String[] args) {

        Pdf pdf = new Pdf(Settings.PDF_DIRECTORY,
                Settings.PROCESSED_PDF_DIRECTORY,
                Settings.TXT_DIRECTORY);

        try {
            byte[] pdfByte = pdf.fromTxtToPdf("321bc531-3a4f-4b");
            System.out.println(pdfByte);
        } catch (Exception e){
            System.out.println("Ocurrio un error");
        }
    }
}
