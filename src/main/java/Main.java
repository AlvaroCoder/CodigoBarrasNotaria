import config.Settings;
import entities.PDF;
import model.AuthModel;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        try {
//            boolean done=AuthModel.clientRegister("87654321","hola123","William","Macalupu","will.maca@gmail.com");
//            System.out.println(done);
//        } catch (Exception e){
//            System.out.println("Ocurrio un error");
//        }

        PDF pdf = new PDF(Settings.PDF_DIRECTORY,
                Settings.PROCESSED_PDF_DIRECTORY,
                Settings.TXT_DIRECTORY);
        try{
            pdf.trackFiles(1);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Ocurrio un error a");
        }


    }
}
