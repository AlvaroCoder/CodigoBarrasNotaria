import config.Settings;
import dao.impl.UsbDaoImpl;
import entities.Pdf;

public class Main {
    public static void main(String[] args) {
        Pdf pdf = new Pdf();
        try {
            byte[] pdfByte = pdf.fromTxtToPdf("321bc531-3a4f-4b","ead38daf-e131-44");
            System.out.println(pdfByte);
        } catch (Exception e){
            System.out.println("Ocurrio un error");
        }
    }
}
