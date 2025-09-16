package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AuthController {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView logoNotaria;

    @FXML
    private void initialize(){
        logoNotaria.setImage(new Image(getClass().getResourceAsStream("/assets/logonotaria.png")));

        btnLogin.setOnAction(event -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            System.out.println("Usuario: " + username + " | Contraseña: " + password);
        });
    }
}
