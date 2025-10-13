package controller;

import config.ToastAlerts;
import dao.impl.ClientDaoImpl;
import entities.Client;
import entities.PDF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class DashboardControllerHome {

    private int idCliente;

    @FXML
    private TextField txtInputDir;

    @FXML
    private TextField txtOutputDirPdf;

    @FXML
    private TextField txtOutputDirTxt;

    @FXML
    private Button btnProcess;

    @FXML
    private Button btnBuscarRutaInput;

    @FXML
    private Button btnBuscarRutaOutputPdf;

    @FXML
    private Button btnBuscarRutaOutputTxt;

    @FXML
    private Button btnRegistrarCliente;

    @FXML
    private TableView<Client> tableClientes;

    @FXML
    private TableColumn<Client, Integer> colId;

    @FXML
    private TableColumn<Client, String> colNombre;

    @FXML
    private TableColumn<Client, String> colApellido;

    @FXML
    private TableColumn<Client, String> colDni;

    @FXML
    private TableColumn<Client, Void> colSeleccionar;

    private ClientDaoImpl clientDao;

    ControllerParent controllerParent;

    public DashboardControllerHome(){
        this.clientDao = new ClientDaoImpl();
    }

    public void setContainerController(ControllerParent controller){
        this.controllerParent = controller;
    }

    @FXML
    private void initialize(){
        btnProcess.setOnAction(e->processDocument());
        btnBuscarRutaInput.setOnAction(e->seleccionarCarpeta(btnBuscarRutaInput, txtInputDir));
        btnBuscarRutaOutputPdf.setOnAction(e->seleccionarCarpeta(btnBuscarRutaOutputPdf, txtOutputDirPdf));
        btnBuscarRutaOutputTxt.setOnAction(e->seleccionarCarpeta(btnBuscarRutaOutputTxt, txtOutputDirTxt));
        btnRegistrarCliente.setOnAction(e->registerClient());
        loadClients();
    }


    public void loadClients() {
        try {
            List<Client> listClients = clientDao.findMany();
            System.out.println("listClients = " + listClients);

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

            colNombre.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colApellido.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            colSeleccionar.setCellFactory(param -> new TableCell<Client, Void>() {
                private final Button btn = new Button("Seleccionar");
                {
                    btn.setStyle("""
                        -fx-background-color: #0C1019;
                        -fx-text-fill: white;
                        -fx-background-radius: 4;
                        -fx-cursor: hand;
                        -fx-font-weight: bold;
                    """);
                    btn.setAlignment(Pos.CENTER);
                    btn.setOnMouseEntered(ev -> btn.setStyle("-fx-background-color: #1d4ed8; -fx-text-fill: white; -fx-background-radius: 4; -fx-cursor: hand;"));
                    btn.setOnMouseExited(ev -> btn.setStyle("-fx-background-color: #0C1019; -fx-text-fill: white; -fx-background-radius: 4;"));
                    btn.setOnAction(event -> {
                        Client client = getTableView().getItems().get(getIndex());
                        setIdCliente(client.getId());
                        tableClientes.refresh();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            });

            ObservableList<Client> observableList = FXCollections.observableArrayList(listClients);
            tableClientes.setItems(observableList);
            tableClientes.setRowFactory(tv -> new TableRow<Client>() {
                @Override
                protected void updateItem(Client client, boolean empty) {
                    super.updateItem(client, empty);

                    if (empty || client == null) {
                        setStyle("");
                    } else if (client.getId() == idCliente) {
                        setStyle("-fx-background-color: #d1fae5;"); // Verde pastel (Tailwind: green-100)
                    } else {
                        setStyle("");
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
            e.printStackTrace();
            ToastAlerts.error("Error", "No se pudo listar los clientes");
        }
    }

    private void seleccionarCarpeta(Button btnBuscarRuta, TextField outputDirectoryUsb){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta de destino");

        Stage stage = (Stage) btnBuscarRuta.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            outputDirectoryUsb.setText(selectedDirectory.getAbsolutePath());
        }
    }


    private void processDocument(){
        String inputDir = txtInputDir.getText();
        String outputDirPdf = txtOutputDirPdf.getText();
        String outputDirTxt = txtOutputDirTxt.getText();
        if (inputDir.isEmpty() || outputDirPdf.isEmpty() || outputDirTxt.isEmpty()) {
            ToastAlerts.warning("Campos Vacios", "Ingrese todas las rutas");
            return;
        }

        if (idCliente==0) {
            ToastAlerts.warning("Vacio","Cliente no seleccionado");
            return;
        }

        PDF pdf = new PDF(
                inputDir,
                outputDirPdf,
                outputDirTxt);

        try{
            pdf.trackFiles(idCliente);
        } catch (Exception e){
            ToastAlerts.error("Error", "Ocurrio un error al procesar los PDF");
        }

    }

    public void registerClient(){
        controllerParent.loadFormCreateClient();
    }

    public void setIdCliente(int idCliente){
        this.idCliente = idCliente;
    }
}
