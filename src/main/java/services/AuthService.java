package services;

public interface AuthService {
    boolean clientLogin(String dni, String password) throws Exception;
    boolean adminLogin(String username, String password) throws Exception;
    boolean clientRegister(String dni, String password, String firstName, String lastName) throws Exception;
    boolean adminRegister(String username, String password) throws Exception;
}
