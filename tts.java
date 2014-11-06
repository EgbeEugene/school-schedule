package Items;


import com.sun.speech.freetts.*;
import java.io.*;



public class tts
{
    
    private static final  String VOICENAME="kevin16";
   
    public void sayText( String Text){
       
         Voice voice ;
         
         VoiceManager vm = VoiceManager.getInstance();
         
         voice = vm.getVoice(VOICENAME);
         voice.allocate();
         
         try {
            
             voice.speak(Text);
             
        } catch (Exception e) {
        
        
            System.out.println("unable to speack oh!!!");
        
        }
       
    
        
    }
    

}
