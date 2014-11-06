/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Items;

import java.text.SimpleDateFormat;
import java.util.*;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author EUGENE
 */

public class Timer {
    
    
    private final SimpleDateFormat sdf  = new SimpleDateFormat("hh:mm");
    private int   currentSecond;
    private Calendar calendar;

    
    
    private void reset(){
        calendar = Calendar.getInstance();
        currentSecond = calendar.get(Calendar.SECOND);
    }
    
      public void start( javafx.scene.control.Label label){
        
          reset();
        
        java.util.Timer timer = new java.util.Timer();
       
label.setText( String.format("%s:%02d", sdf.format(calendar.getTime()), currentSecond ));

timer.schedule(new TimerTask() {
@Override

public void run() {
    
        Platform.runLater(new Runnable() {
     
        public void run() {
           
       label.setText( String.format("%s:%02d", sdf.format(calendar.getTime()), currentSecond ));
        currentSecond++;  
      
       
       }
    });

}
}, 3*1000);
                

      label.setVisible(true);}

}
