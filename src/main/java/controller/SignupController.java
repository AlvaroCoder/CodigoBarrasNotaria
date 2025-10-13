package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.AuthModel;
import model.SessionModel;

public class SignupController {
    @FXML
    private ImageView imageLogo;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnLogin;


    @FXML
    private void initialize(){

        imageLogo.setImage(new Image(getClass().getResourceAsStream("/assets/LogoCosai.png")));
        btnLogin.setOnAction((event -> {
            try{
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                String email=txtEmail.getText();

                boolean adminRegister =  AuthModel.adminRegister(username, password,email);
                System.out.println("adminRegister = " + adminRegister);
                if (adminRegister){
                    SessionModel.setUser(username, true);

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/fxml/dashboardContainer.fxml")
                    );
                    Parent parent = loader.load();

                    Stage stage = (javafx.stage.Stage) btnLogin.getScene().getWindow();
                    stage.setScene(new javafx.scene.Scene(parent));
                    stage.show();
                }

            }catch (Exception e){
                System.out.println("e = " + e);
            }
        }));
    }


}
