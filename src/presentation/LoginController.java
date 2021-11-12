package presentation;

import Model.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monitor.FileObserver;
import monitor.Observer;
import monitor.SQLiteObserver;
import monitor.Subject;

import java.io.*;
import java.net.URL;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static persistence.StaffDAO.*;


public class LoginController implements Initializable {

    Subject monitor = new Subject();
    Observer obsi = new SQLiteObserver(monitor);
    Observer obs2 = new FileObserver(monitor);


    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button btnCancel;

    public void btnCancelAction() {
        showMessage("");
        closeWindow();

    }

    @FXML
    private Button btnLogin;




    public void btnLoginAction() {
        showMessage("Trying To Login");
        monitor.setLog(new Logger("info", "Login attempt"));

        if (usernameTextfield.getText().equals("") || passwordTextField.getText().equals("")) {
            showMessage("Enter valid Username and or Password");
            return;
        }
        if (validateUser(usernameTextfield.getText(), passwordTextField.getText())) {
            showMessage("Login successful");
            monitor.setLog(new Logger("info", "login successful"));
            String role = getRole(usernameTextfield.getText());
            goToHomePage(role);
        } else {
            if(validateLockedState(usernameTextfield.getText())){
                showMessage("Invalid Credentials");
                System.out.println(updateLockedAttempt(usernameTextfield.getText()));
                monitor.setLog(new Logger("info", "Invalid credentials"));
            }else{
                showMessage("Account Locked contact Administrator");
                monitor.setLog(new Logger("info", "Invalid credentials"));
            }
        }

    }

    @FXML
    private Button btnRegister;

    public void btnRegisterAction(ActionEvent event) {
        showMessage("");
        monitor.setLog(new Logger("info", "leaving login page"));
        createAccount();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private static void transferMessage(String message) {

    }

    private void createAccount() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();

        } catch (Exception ex) {
            monitor.setLog(new Logger("warn", ex.getCause().getMessage()));
        }

    }

    private void setUserToFile(String name){
        try (OutputStream output = new FileOutputStream("userConfig.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("userName", name);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }


    }

    private void goToHomePage(String page) {


        try {
            setUserToFile(usernameTextfield.getText());

            Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
            closeWindow();

        } catch (Exception ex) {
            monitor.setLog(new Logger("warn", ex.getMessage()));
        }

    }

    private boolean validateUser(String name, String pwd) {

        boolean result = false;
        result = persistence.StaffDAO.validateUser(name, pwd);
        return result;

    }

    private void reset() {
        usernameTextfield.setText("");
        passwordTextField.setText("");
    }

    private void showMessage(String msg) {
        messageLabel.setText(msg);
    }

    private void closeWindow() {
        monitor.setLog(new Logger("info", "exit program"));
        Stage stage = (Stage) loginAnchorPane.getScene().getWindow();
        stage.close();
    }

    private void tranferData(String data) {
        monitor.setLog(new Logger("info", "data in " + data));
        showMessage(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monitor.setLog(new Logger("info", "Login Controller accessed"));

    }
}
