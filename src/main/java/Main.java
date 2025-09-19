import config.Settings;
import dao.impl.UsbDaoImpl;
import entities.Pdf;
import model.AuthModel;

public class Main {
    public static void main(String[] args) {
        try{
            //AuthModel.adminRegister("will2","hola123");
            //boolean start=AuthModel.adminLogin("will2","");
            //System.out.println(start);
//            AuthModel.clientRegister("87654321","hola123",
//                    "William","Macalupu");
            System.out.println(AuthModel.clientLogin("87654321","hola123"));
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
