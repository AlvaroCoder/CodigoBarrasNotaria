package config;

import javafx.scene.control.Alert;

public class ToastAlerts {
    private static void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void success(String title, String message){
        showAlert(Alert.AlertType.CONFIRMATION, title, message);
    }

    public static void warning(String title, String message){
        showAlert(Alert.AlertType.WARNING, title, message);
    }

    public static void error(String title, String message){
        showAlert(Alert.AlertType.ERROR, title, message);
    }
}
