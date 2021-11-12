package presentation;

import Model.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monitor.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    Subject monitor = new Subject();
    Observer obsP = new PinObserver(monitor);


    @FXML
    private BorderPane userBorderPane;

    @FXML
    TextField UserPinFX;

    @FXML
    private Button btnSuperPage;

    @FXML
    Label pinMessage;

    private int pin;



    public void btnBackAction(ActionEvent event) {
        System.out.println("User Back pressed");
        goToLoginPage();

    }



    // Tried to use this way to do it but it caused a issue were the user page just wouldn't load up.
//    protected UserController(){
//        Properties p1 = new Properties();
//
//        try(FileInputStream ip1 = new FileInputStream("config1.properties")){
//            p1.load(ip1);
//            pin = Integer.parseInt(p1.getProperty("pin"));
//        }
//        catch (FileNotFoundException e){
//            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
//        }
//        catch (IOException e){
//            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
//        }
//    }


    // This way I sanitised the input pin and didn't hard code the credential into the code. Used a config file to store it and read it from there.
    // The above piece of code was adapted and put into the below method so that it worked.
    int count = 5;
    public void setBtnSuperPage(ActionEvent event) throws IOException{
        Properties p1 = new Properties();

        try(FileInputStream ip1 = new FileInputStream("config1.properties")){
            p1.load(ip1);
            pin = Integer.parseInt(p1.getProperty("pin"));
        }
        catch (FileNotFoundException e){
            showMessage("File is missing so, pin cannot be verified");
            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
        }
        catch (IOException e){
            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
        }

        int uPin = Integer.parseInt(UserPinFX.getText());
        // This is the clean/sanitised way that it should be done.
//        if(UserPinFX.getText()=="") {
//            showMessage("Pin cannot be blank.");
//            count--;
//        }
//        else if(UserPinFX.getText().contains("^[a-z][a-zA-Z]*$")){
//            showMessage("Format incorrect");
//            count--;
//
//        }
//        else

            if (uPin == pin) {
                Parent root = FXMLLoader.load(getClass().getResource("super.fxml"));
                Stage registerStage = new Stage();
                registerStage.initStyle((StageStyle.UNDECORATED));
                registerStage.setScene(new Scene(root));
                registerStage.show();
                closeWindow();
                System.out.println("Super Page upgrade button pressed.");
            }
        // The credentials should be sanitised and not hard coded. This is the wrong way to do it and inputs should be sanitised.
//         I have Included the pin number to be assigned for this specific upgrade in a file called config1.properties, I will
//         Call it from their rather than to have it hard coded like below.
//        if(uPin == 2323){
//            Parent root = FXMLLoader.load(getClass().getResource("super.fxml"));
//            Stage registerStage = new Stage();
//            registerStage.initStyle((StageStyle.UNDECORATED));
//            registerStage.setScene(new Scene(root));
//            registerStage.show();
//            closeWindow();
//            System.out.println("Super Page button pressed.");
//        }
        else{
            if((UserPinFX.getText().length()<4)||(UserPinFX.getText().length()>4)){
                count--;
                showMessage("Pin isn't right, try entering the correct upgrade pin. " + "\n" + "Attempts left : " + count);
                if (count == 0) {
                    btnSuperPage.setVisible(false);
                    showMessage("Please log out and log back in to try again.");
                }
            }
        }
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
        Stage stage = (Stage) userBorderPane.getScene().getWindow();
        stage.close();
    }

    private String getCurrentUser(){
        String user="";
        try (InputStream input = new FileInputStream("userConfig.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            user = prop.getProperty("userName");
            System.out.println("user"+ user);
        } catch (IOException ex) {
            monitor.setLog(new Logger("warn", ex.getMessage()));
        }
        return user;
    }

    private void showMessage(String msg){pinMessage.setText(msg);}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monitor.setLog(new Logger("info", "user page accessed"));
    }


}
