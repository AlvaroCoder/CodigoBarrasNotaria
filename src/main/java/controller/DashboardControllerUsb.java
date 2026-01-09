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
    private TableColumn<Usb, String> colPassword;

    private UsbDaoImpl usbDao;

    public DashboardControllerUsb(){
        this.usbDao = new UsbDaoImpl();
    }

    @FXML
    private void initialize(){
        loadUsbs();
    }

    public void loadUsbs(){
        try {
            List<Usb> usbs = usbDao.findMany();

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colCliente.setCellValueFactory(new PropertyValueFactory<>("clientId"));
            colPassword.setCellValueFactory(new PropertyValueFactory<>("pdfPassword"));
            colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
            colFechaModificacion.setCellValueFactory(new PropertyValueFactory<>("lastModifiedDate"));

            ObservableList<Usb> observableList = FXCollections.observableArrayList(usbs);
            tableUsb.setItems(observableList);

        } catch (Exception e){
            System.out.println("e = " + e);
        }
    }
}
