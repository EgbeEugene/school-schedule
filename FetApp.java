/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fetapp;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import fetapp.DatabaseHelper;


/**
 *
 * @author angy
 */

public class FetApp extends Application {
   
    SimpleDateFormat dateOnly;
    Date dNow ;
 DatabaseHelper db;
 
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fetHbGui.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        
         Image applicationIcon = new Image(getClass().getResourceAsStream("/images/award.png"));
         stage.getIcons().add(applicationIcon);
         stage.setTitle("FETHb");
  
       
        
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws SQLException {
       
        
        launch(args);
      FetApp fetapp = new FetApp();

      
    }
   
}
