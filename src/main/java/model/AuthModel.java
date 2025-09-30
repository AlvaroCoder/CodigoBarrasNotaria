package model;

import dao.ClientDao;
import dao.impl.AdminDaoImpl;
import dao.impl.ClientDaoImpl;
import entities.Admin;
import entities.Client;
import org.mindrot.jbcrypt.BCrypt;
import services.AuthService;

import java.util.HashMap;

public class AuthModel {

    public static boolean clientLogin(String dni, String password) throws Exception {
        if (dni.isEmpty() || password.isEmpty()){
            throw new Exception("Falta rellenar un campo");
        }

        if (dni.length() != 8){
            throw new Exception("El dni debe tener 8 dígitos");
        }

        if (password.length()<6){
            throw new Exception("La contraseña debe tener como mínimo 6 caracteres");
        }


        ClientDaoImpl clientDaoImpl = new ClientDaoImpl();
        Client client =clientDaoImpl.findOneDb(dni);

        if (client == null){
            throw new Exception("El dni no está registrado");
        }

        return BCrypt.checkpw(password,client.getPassword());

    }

    public static boolean adminLogin(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()){
            throw new Exception("Falta rellenar un campo");
        }
        AdminDaoImpl adminDaoImpl = new AdminDaoImpl();
        Admin admin =adminDaoImpl.findOneDb(username);

        if (admin == null){
            throw new Exception("El nombre de usuario no está registrado");
        }

        return BCrypt.checkpw(password,admin.getPassword());
    }

    public static boolean clientRegister(String dni, String password, String firstName, String lastName, String email) throws Exception {

        if (dni.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
            throw new Exception("Falta rellenar un campo");
        }

        if (password.length()<6){
            throw new Exception("La contraseña debe tener mínimo 6 caracteres");
        }

        ClientDaoImpl clientDaoImpl = new ClientDaoImpl();

        if(clientDaoImpl.findOne(dni) != null){
            throw new Exception("El dni ya se está utilizando.");
        }

        password = BCrypt.hashpw(password,BCrypt.gensalt());

        Integer id=clientDaoImpl.insertOne(new Client(dni,password,firstName,lastName,email));

        return id != null;

    }

    public static boolean adminRegister(String username, String password,String email) throws Exception{
        if (username.isEmpty() || password.isEmpty()){
            throw new Exception("Falta completar un campo");
        }

        if (password.length()<6){
            throw new Exception("La contraseña debe tener mínimo 6 caracteres");
        }

        AdminDaoImpl adminDaoImpl = new AdminDaoImpl();

        if (adminDaoImpl.findOne(username) != null){
            throw new Exception("El nombre de usuario ya se está utilizando");
        }

        password = BCrypt.hashpw(password,BCrypt.gensalt());

        adminDaoImpl.insertOne(new Admin(username,password,email));

        return true;
    }

}
