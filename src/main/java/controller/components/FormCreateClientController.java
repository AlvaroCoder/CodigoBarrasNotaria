package controller.components;

import config.ToastAlerts;
import controller.ControllerParent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.AuthModel;

public class FormCreateClientController {

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnCancelar;

    private ControllerParent controllerParent;

    @FXML
    public void initialize(){
        btnCancelar.setOnAction(event -> cancelarRegistro());
        btnRegistrar.setOnAction(event -> registrarCliente());
    }

    public void setContainerController(ControllerParent controller){
        this.controllerParent = controller;
    }

    public void registrarCliente(){
        String dni = txtDni.getText().strip();
        String firstName = txtFirstName.getText().strip();
        String lastName = txtLastName.getText().strip();
        String email = txtEmail.getText().strip();
        String password = txtPassword.getText().strip();

        if (dni.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            ToastAlerts.warning("Error", "Completar los campos en blanco");
            return;
        }

        if (dni.length() != 8){
            ToastAlerts.error("Error","El DNI debe ser de 8 dígitos");
            return;
        }

        if (password.length() < 5){
            ToastAlerts.warning("Error", "La contraseña debe ser de al menos 6 digitos");
            return;
        }
        try {
            boolean clientRegister = AuthModel.clientRegister(dni, password, firstName, lastName, email);
            if (clientRegister){
                ToastAlerts.success("Exito", "Se guardo con exito el cliente");
                controllerParent.loadViewHome();
            } else  {
                ToastAlerts.error("Error", "No se pudo registrar al cliente");
            }
        } catch (Exception e){
            System.out.println("e = " + e);
            ToastAlerts.error("Error", "Surgio un error en el registro");
        }
    }

    public void cancelarRegistro(){
        controllerParent.loadViewHome();
    }

}
