package controller;

import config.ToastAlerts;
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
    private Button btnRegister;

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
        btnRegister.setOnAction(event -> loadRegisterView());
        btnLogin.setOnAction((event -> {
            try{
                String username = txtUsername.getText();
                String password = txtPassword.getText();

                if (username.isEmpty() || password.isEmpty()){
                    ToastAlerts.warning("Error","Complete sus credenciales");
                    return;
                }
                boolean adminLogin = AuthModel.adminLogin(username, password);
                System.out.println("adminLogin = " + adminLogin);
                if (adminLogin){
                    SessionModel.setUser(username, true);
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/fxml/dashboardContainer.fxml")
                    );
                    Parent parent = loader.load();

                    ToastAlerts.success("Exito", "Inicio de sesion exitoso!");
                    Stage stage = (javafx.stage.Stage) btnLogin.getScene().getWindow();
                    stage.setScene(new Scene(parent));
                    stage.show();
                }
                else{
                    ToastAlerts.error("Error", "Credenciales incorrectas");
                    txtPassword.setText("");
                    txtUsername.setText("");
                }
            }catch (Exception e){
                System.out.println("e = " + e);
                ToastAlerts.error("Error de Inicio", "Ocurrio un error en el Inicio de Sesion");
            }
        }));
    }

    public void loadRegisterView(){


    }
}
