package model;

import javafx.event.ActionEvent;
import services.AuthService;

import java.beans.EventHandler;

public class AutModel implements AuthService {
    @Override
    public boolean login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()){
            return false;
        }
        return "admin".equals(username) && "1234".equals(password);
    }

    @Override
    public boolean register(String name, String username, String email, String password) {
        if (!username.isEmpty() && email.contains("@") && password.length() > 4){
            return true;
        }
        return false;
    }
}
