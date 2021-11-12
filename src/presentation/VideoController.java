package presentation;

import Model.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monitor.FileObserver;
import monitor.Observer;
import monitor.SQLiteObserver;
import monitor.Subject;

public class VideoController implements Initializable {
    Subject monitor = new Subject();
    Observer obsi = new SQLiteObserver(monitor);
    Observer obs2 = new FileObserver(monitor);

    @FXML
    public Button vidBtn;

    @FXML
    private AnchorPane VideoPane;

    @FXML
    private WebView webView;

//    @FXML
//    private Button vidExitBtn;



    public void closeWindow(){
        Stage stage = (Stage) VideoPane.getScene().getWindow();
        stage.close();
    }


    public void vidBtnAction(){
        System.out.println("Video back button pressed");
        goToSuperPage();
    }

    // Couldn't get the WebView to stop playing. It was a pain but wanted to leave the code here to show I at least made an effort.
//    private void setVidExitBtn(){
//        System.out.println("Exit Button Pressed");
//        closeWindow();
//        webView.getEngine().load(null);
//    }

    public void goToSuperPage(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("super.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root));
            registerStage.show();
            closeWindow();

        } catch(Exception ex){
            monitor.setLog(new Logger("warn", ex.getMessage()));
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = webView.getEngine();


        webEngine.load("http://www.youtube.com/watch?v=dQw4w9WgXcQ");
        //embed/evd2mLwMjho_o?autoplay=1

        WebHistory webHistory = webEngine.getHistory();
        webView.setPrefSize(600,550);


    }
}
