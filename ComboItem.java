/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Items;

/**
 *
 * @author EUGENE
 */
public class ComboItem {

      String key;
      String value;
      
 //this is the combo item oonstructor 
      
      public ComboItem(String k ,String v){
      
          this.key = k;
          this.value = v;
          
      }
    
      @Override 
      public String toString(){
      
        return this.key;
        
      }
      
      public String getKey(){
       
          return this.key;
      }

     public String getValue(){
        
        return this.value;
         
        }

}

