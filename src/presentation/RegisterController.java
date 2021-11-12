package presentation;

import Model.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monitor.FileObserver;
import monitor.Observer;
import monitor.SQLiteObserver;
import monitor.Subject;
import register.Password;
import register.PasswordFactory;
import register.RegisterChecker;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static persistence.StaffDAO.addUser;

public class RegisterController implements Initializable {

    Subject monitor = new Subject();
    Observer obsi = new SQLiteObserver(monitor);
    Observer obs2 = new FileObserver(monitor);

    @FXML
    private AnchorPane registerAnchorPane;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private Button btnRegistration;

    @FXML
    private TextField userNameTextfield;

    @FXML
    private TextField pinTextfield;

    @FXML
    private TextField emailTextfield;

    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private PasswordField confirmPasswordTextfield;

    @FXML
    private Label messageLabel;


    public void btnRegistrationAction(ActionEvent event) {
        System.out.println("Registration Pressed");
        String userName = userNameTextfield.getText();
        String pass1 = passwordTextfield.getText();
        String pass2 = confirmPasswordTextfield.getText();
        String pin = pinTextfield.getText();
        String UserEmail = emailTextfield.getText();
        showMessage("");

        boolean validUser = RegisterChecker.confirmUserName(userName);
        boolean validPin = RegisterChecker.confirmValidPin(pin);
        boolean validEmail = RegisterChecker.confirmEmail(UserEmail);

        String msg = validUser ? " UserName in Use " : "";
        msg += validPin ? "" : " invalid pin ";
        msg += validEmail ? "" : " Invalid E-Mail address ";
        msg += confirmPassword(pass1,pass2);
        showMessage(msg);
        monitor.setLog(new Logger("info", msg));

        if(msg.trim().equals("OK")){
            System.out.println("recording new user");
            try {
                addUser(userName, pass1, pin , UserEmail);
                monitor.setLog(new Logger("info", "new user added"));
                goToLoginPage();
            } catch (SQLException e) {
                monitor.setLog(new Logger("warn", e.getMessage()));
            }
        }
    }


    @FXML
    private Button btnCancelRegistration;


    public void btntnCancelRegistrationAction(ActionEvent event) {
        goToLoginPage();
        closeWindow();
    }

    private void goToLoginPage() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
            closeWindow();

        } catch (Exception ex) {
            monitor.setLog(new Logger("warn", ex.getMessage()));
        }

    }

    public String confirmPassword(String pass1, String pass2){

        PasswordFactory pf = new PasswordFactory();
        Password pwd1 = pf.getTest("PWDSAME");
        Password pwd2 = pf.getTest("PWDLENGTH");
        Password pwd3 = pf.getTest("PWDCOMPROMISED");

        List<Password> list = new ArrayList<Password>();
        list.add(pwd1);
        list.add(pwd2);
        list.add(pwd3);
        String message = "";

        for(Password p :list){
            if(p.checkPassword(pass1,pass2)){
                message =p.getMessage();
                System.out.println(" message "+message);
                return message;
            }
            message = "OK";

        }
        return message;
    }

    private void showMessage(String msg) {
        messageLabel.setText(msg);
    }

    private void resetPassword() {
        passwordTextfield.setText("");
        confirmPasswordTextfield.setText("");

    }

    private void closeWindow() {
        Stage stage = (Stage) registerAnchorPane.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Init Resister ");
        monitor.setLog(new Logger("info", "Register attempt could be made"));

    }
}
