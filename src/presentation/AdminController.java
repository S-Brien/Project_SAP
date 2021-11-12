package presentation;

import Model.Logger;
import Model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monitor.FileObserver;
import monitor.Observer;
import monitor.SQLiteObserver;
import monitor.Subject;
import persistence.StaffDAO;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.ComboBoxTableCell.*;

public class AdminController implements Initializable {
    Subject monitor = new Subject();
    Observer obsi = new SQLiteObserver(monitor);
    Observer obs2 = new FileObserver(monitor);

    @FXML
    private AnchorPane adminAncorPane;

    @FXML
    private Label msgLabel;

    @FXML
    private TableView<Staff> tableView;

    @FXML
    private TableColumn<Staff, String> userNameColumn;

    @FXML
    private TableColumn<Staff, Staff.ROLE> roleColumn;

    @FXML
    private TableColumn<Staff, String> attemptsColumn;

    @FXML
    private TableColumn<Staff, Staff.LOCK> isLockedColumn;

    //Not Used Info Coming from Database StaffDAO.getAllStaff();
    private ObservableList<Staff> getStaff(){
        String LOCKED = "LOCKED";
        ObservableList<Staff> staff = FXCollections.observableArrayList();
        staff.add(new Staff("admin","admin",LOCKED, "5"));
        staff.add(new Staff("super","super",LOCKED, "3"));
        staff.add(new Staff("user","user",LOCKED, "3"));
        return staff;
    }


    /*
    how to edit TabelColumn User name firstNameCellEdit() Calle4d in Scenebuilder On Edit Commit
    public void userNameCellEdit (TableColumn.CellEditEvent editCell){
        Staff staffSelected = tableView.getSelectionModel().getSelectedItem();
        staffSelected.setUserName(editCell.getNewValue().toString());
    }
    */


    public void btnBackAction(){
        System.out.println("Admin Back pressed");
        goToLoginPage();


    }
    private void goToLoginPage(){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
            closeWindow();

        } catch(Exception ex){
            monitor.setLog(new Logger("warn", ex.getMessage()));
        }

    }
    private void closeWindow(){
        Stage stage = (Stage) adminAncorPane.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monitor.setLog(new Logger("info", "admin page accessed"));
        msgLabel.setText("");

        userNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("UserName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("Role"));
        attemptsColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("LockAttempt"));
        isLockedColumn.setCellValueFactory(new PropertyValueFactory<>("lock"));

        tableView.setItems(StaffDAO.getAllStaff());
        tableView.setEditable(true);


        isLockedColumn.setCellFactory(forTableColumn(Staff.LOCK.values()));
        isLockedColumn.setOnEditCommit(e -> {
            Staff s = e.getRowValue();
            e.getRowValue().setLock(e.getNewValue());
            msgLabel.setText(StaffDAO.updateLockedData(s.getUserName(),e.getNewValue().toString()));
            System.out.println("UserName"+s.getUserName()+"Locked"+ e.getNewValue()+" attempts "+ s.getRole());
            //refresh TabelView data after update;
            tableView.setItems(StaffDAO.getAllStaff());
        });

        roleColumn.setCellFactory(forTableColumn(Staff.ROLE.values()));
        roleColumn.setOnEditCommit(e -> {
            Staff s = e.getRowValue();
            e.getRowValue().setRole(e.getNewValue());
            msgLabel.setText(StaffDAO.updateRole(s.getUserName(),e.getNewValue().toString()));
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("UserName"+s.getUserName()+" Role "+ e.getNewValue()+" role new  "+ s.getRole());
            //refresh TabelView data after update;
            tableView.setItems(StaffDAO.getAllStaff());
        });
    }
}
