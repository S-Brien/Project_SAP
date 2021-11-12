package presentation;

import Model.Logger;
import Model.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monitor.*;
import persistence.StaffDAO;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class SuperController implements Initializable {
    Subject monitor = new Subject();

    //Set up a new Observer file called PinObserver to monitor where I have re-engineered the code relate to the pin.
//    For example in user and super I'm getting the user of the app to enter a pin. I want to find out if there's any issues related to this caught
//    It sends the logs to the pinUsage.txt file in the tree. 
    Observer osP = new PinObserver(monitor);

    @FXML
    private BorderPane superBorderPane;

    @FXML
    private Button superBtnVid;

    @FXML
    private TextField superPinFX;

//    @FXML
//    private WebView webView;


    @FXML
    Label sMsg;

    private String type;


    public void btnBackAction(){
        System.out.println("super Back pressed");
        goToLoginPage();
        monitor.setLog(new Logger("info", "The back button has been pressed."));
    }
    public void goToLoginPage(){

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



    public void closeWindow(){
        Stage stage = (Stage) superBorderPane.getScene().getWindow();
        stage.close();
    }







//    public void setBtnVideo(ActionEvent event) throws IOException{
//        Properties p1 = new Properties();
//        //webView.setVisible(false);
//        try(
//                FileInputStream ip1 = new FileInputStream("config1.properties")){
//            p1.load(ip1);
//            type = p1.getProperty("role");
//        }
//        catch (FileNotFoundException e){
//            showMessage("File can't be found, try again later");
//            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
//        }
//        catch(IOException e){
//            monitor.setLog(new Logger("warn", e.getCause().getMessage()));
//        }
//
//            int count = 10;
//        int pin = Integer.parseInt(superPinFX.getText());
//        if(StaffDAO.validatePin(superPinFX.getText())){
//            //if(type == "role") {
//                //webView.setVisible(true);
//            //}
//        }
//        else if(!(StaffDAO.validatePin(superPinFX.getText()))){
//            count--;
//            showMessage("Pin is incorrect " + "\n" + "Tries Remaning : " + count);
//
//
//            }
//        else{
//            if(count == 0){
//                btnVideo.setVisible(false);
//                showMessage("Please log out and try again.");
//            }
//        }
//    }

//    public void goToVideoPage() {
//        int pin = Integer.parseInt(superPinFX.getText());
//        if (StaffDAO.validatePin(superPinFX.getText())) {
//
//            try {
//                Parent root = FXMLLoader.load(getClass().getResource("video.fxml"));
//                Stage registerStage = new Stage();
//                registerStage.initStyle(StageStyle.UNDECORATED);
//                registerStage.setScene(new Scene(root));
//                registerStage.show();
//                closeWindow();
//
//            } catch (Exception ex) {
//                monitor.setLog(new Logger("warn", ex.getMessage()));
//            }
//
//        }
//    }

//    Tried lots of different ways to get this to Work, I left some of the code that I tried out.
//    Some of the coding I couldn't get my head around. The logic seemed ok in my head but maybe not in theory.
//    I tried what I could and tried not to compromise the App in the process.


    public void setSuperBtnVid(ActionEvent event) throws IOException{
        int count = 10;
        int pin = Integer.parseInt(superPinFX.getText());
        if(StaffDAO.validatePin(superPinFX.getText())){
                Parent root = FXMLLoader.load(getClass().getResource("video.fxml"));
                Stage registerStage = new Stage();
                registerStage.setScene(new Scene(root));
                registerStage.show();
                closeWindow();
                System.out.println("Privilege Button Pressed");
            monitor.setLog(new Logger("info", "Video page will come next."));
        }
        else{
            // Trying to get stuff to work has been a real pain.
//            count--;
            //showMessage("Pin incorrect," + "\n"+ "if you can't remember your pin " + "\n" +"please contact admin " );
            //if (count == 0){
              //  superBtnVid.setVisible(false);
                //showMessage("Logout and log back in to try again.");
            if(!(StaffDAO.validatePin(superPinFX.getText()))){
                //count--;
                monitor.setLog(new Logger("info", "Pin has been caught as Incorrect. This may cause an issue for the running of the app. If they enter anything except" +
                        "A number because of the Integer.parseInt() it causes the app to crash internally, look at the problems on the system out log."));
                showMessage("Pin incorrect," + "\n"+ "if you can't remember your pin " + "\n" +"please contact admin ");
            }
        }

        //}
    }


    private void showMessage(String msg){sMsg.setText(msg);}




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monitor.setLog(new Logger("info", "Super page accessed"));
          //webView.setVisible(false);


//
//        WebEngine webEngine = webView.getEngine();
//
//
//        webEngine.load("http://www.youtube.com/embed/evd2mLwMjho_o?autoplay=1");
//
//        WebHistory webHistory = webEngine.getHistory();
//        webView.setPrefSize(268,268);

        //System.out.println(webEngine.getUserAgent());
    }
}
