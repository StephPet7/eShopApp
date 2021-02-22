/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.Ticket_de_caisse;


/**
 *
 * @author rodriguenana
 */



 


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileOutputStream;

 
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;




public class QRcode {

    
    private String QRcodeInfo = null;
    private String url = null;
    private String result = null;
    
    
    public QRcode(String QRcodeInfo){ 
        
        super();
        this.QRcodeInfo = QRcodeInfo;
        this.url = new String("qrcode.PNG");
        // this.url = new String("C://royalgame//QRCodeRoyalGame.PNG");
            
            }
    public String getImageQRCode(){
       
           return this.result;
    
        }
    
    public static boolean Create(String QRcodeInfo, String url){
    
        try{
            ByteArrayOutputStream out = QRCode.from(QRcodeInfo).to(ImageType.PNG).withSize(75, 75).stream();
           // FileOutputStream fout = new FileOutputStream(new File(url));
              FileOutputStream fout = new FileOutputStream(new File(url));
 
            fout.write(out.toByteArray());
 
            fout.flush();
            fout.close();    
            
            }   catch (Exception e) {
            // Do Logging
        	e.printStackTrace(); 
                return false;}
        
       return true;
        }
    /*
      public static void main(String args[]){
      
      
      QRcode.Create("ROYAL GAME", "C://ROYALGAME//QRCode_ticket.PNG");
      
      }  */
       

}
