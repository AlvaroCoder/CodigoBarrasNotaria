package services;

public interface AuthService {
    boolean login(String username, String password);
    boolean register(String name, String username, String email, String password);

}
