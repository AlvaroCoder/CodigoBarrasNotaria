package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.AuthModel;
import model.SessionModel;

public class LoginController {
    @FXML
    private ImageView imageLogo;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private void initialize(){
        imageLogo.setImage(new Image(getClass().getResourceAsStream("/assets/LogoCosai.png")));
        btnLogin.setOnAction((event -> {
            try{
                String username = txtUsername.getText();
                String password = txtPassword.getText();

                boolean adminLogin = AuthModel.adminLogin(username, password);
                System.out.println("adminLogin = " + adminLogin);
                if (adminLogin){
                    SessionModel.setUser(username, true);
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/fxml/dashboardContainer.fxml")
                    );
                    Parent parent = loader.load();

                    Stage stage = (javafx.stage.Stage) btnLogin.getScene().getWindow();
                    stage.setScene(new Scene(parent));
                    stage.show();
                }
            }catch (Exception e){
                System.out.println("e = " + e);
            }
        }));
    }
}
