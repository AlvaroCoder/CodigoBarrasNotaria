package controller;

import dao.impl.UsbDaoImpl;
import entities.Usb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class DashboardControllerUsb {

    @FXML
    private TableView<Usb> tableUsb;

    @FXML
    private TableColumn<Usb, String> colId;

    @FXML
    private TableColumn<Usb, String> colCliente;

    @FXML
    private TableColumn<Usb, LocalDateTime> colFechaCreacion;

    @FXML
    private TableColumn<Usb, LocalDateTime> colFechaModificacion;

    @FXML
    private void initialize(){
        UsbDaoImpl usbDao = new UsbDaoImpl();
        List<Usb> usbs = usbDao.findByIdClient(1);

        // 🔑 Asociar las columnas con las propiedades del modelo
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clientUsername"));
        colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        colFechaModificacion.setCellValueFactory(new PropertyValueFactory<>("lastModifiedDate"));

        // Convertimos lista normal en observable
        ObservableList<Usb> observableUsbList = FXCollections.observableArrayList(usbs);

        tableUsb.setItems(observableUsbList);

        // Debug
        for (Usb usb : usbs) {
            System.out.println("USB ID: " + usb.getId() +
                    ", Cliente: " + usb.getClientUsername() +
                    ", Creación: " + usb.getCreationDate() +
                    ", Modificación: " + usb.getLastModifiedDate());
        }
    }
}
