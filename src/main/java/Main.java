import config.Settings;
import dao.impl.ClientDaoImpl;
import dao.impl.UsbDaoImpl;
import dto.UsbClientDto;
import entities.PDF;
import model.AuthModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        try {
//            boolean done=AuthModel.clientRegister("87654321","hola123","William","Macalupu","will.maca@gmail.com");
//            System.out.println(done);
//        } catch (Exception e){
//            System.out.println("Ocurrio un error");
//        }

//        PDF pdf = new PDF(Settings.PDF_DIRECTORY,
//                Settings.PROCESSED_PDF_DIRECTORY,
//                Settings.TXT_DIRECTORY);
//        try{
//            pdf.trackFiles(1);
//        } catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Ocurrio un error a");
//        }

//        try {
//            System.out.println(PDF.fromTxtToPdf("b4b2f5d6-f889-49","e15aa534-ff6e-44"));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        UsbDaoImpl usbDaoImpl = new UsbDaoImpl();
        List<UsbClientDto> rs = usbDaoImpl.findByIdClient(1);
        rs.forEach(System.out::println);

//        try {
//            PDF.renderProject(1,"D:/Will/Emprendimiento/Proyectos/results/secret_proyect/carpetPrueba");
//        } catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Ocurrio un error");
//        }


    }
}
