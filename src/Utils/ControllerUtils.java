/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Isaac
 */
public class ControllerUtils{

    public static void setProfil(Label username, Label nameProfil, Label idProfil, JFXButton showProfil) {
        if(username!=null)username.setText(login.LoginController.gestionnaire.getLogin());
        if(nameProfil!=null)nameProfil.setText(login.LoginController.gestionnaire.getLogin());
        if(idProfil!=null)idProfil.setText(login.LoginController.gestionnaire.getMatricule());
        
        /***************** Gerer le button show More ici */
    }
    
     public void launchInterface(String path){
    }
    
    public static void launchTransition( Node b){
    ScaleTransition st = new ScaleTransition(Duration.seconds(2), b);
	st.setFromX(.8);
	st.setFromY(.8);
	st.setToX(1.6);
	st.setToY(1.6);
	st.play();
	st.setOnFinished(e -> {
		if(!b.isHover()) {
			ScaleTransition st2 = new ScaleTransition(Duration.seconds(1), b);
			st2.setToX(1);
			st2.setToY(1);
			st2.play();
		}
	});
	
	FadeTransition ft = new FadeTransition(Duration.seconds(1), b);
	ft.setFromValue(.2);
	ft.setToValue(1);
	ft.play();	
    
    }
    
    public static void openTransition( Node b){
	transition(b,true);
    }
    
    public static void closeTransition( Node b){
        transition(b,false);
    }
    
    public static void transition(Node b, boolean open){
        ScaleTransition st = new ScaleTransition(Duration.seconds(.1), b);
	st.setFromX((open?.8:1));
	st.setFromY((open?.8:1));
	st.setToX((open?1:0));
	st.setToY((open?1:0));
	
	
	FadeTransition ft = new FadeTransition(Duration.seconds(.1), b);
	ft.setFromValue((open?.2:1));
	ft.setToValue((open?1:0));
        
        
        st.play();
	ft.play();
    }
    
    public static LocalDate getLocalDate(Date d){
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
     public static Date getDate(LocalDate d){
        return Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static String castId(Integer id){
        return id.toString().substring(0, 3) + "-" + id.toString().substring(3, 6);
    }
    
    public static void showNotification(String title,String text,Boolean isError,Pos position,float duration)
    {
            ImageView img;
            if(isError)
            {
                img = new ImageView(new Image("/resources/img/errorNotification.png"));
            }else
            {
                img = new ImageView(new Image("/resources/img/confirmNotification.png"));
            }
            
            img.setScaleX(0.6);
            img.setScaleY(0.6);
            
            Notifications notificationBuilder = Notifications.create()
                    .title(title)
                    .text(text)
                    .graphic(img)
                    .hideAfter(Duration.seconds(duration))
                    .position(position)
                    .onAction((ActionEvent e) -> {
                        System.out.println("Notifications Succeeded");
            });
            
           notificationBuilder.show();   
    }
    
    public static void print(Object value)
    {
        System.out.println(value);
    }
    
    public static void changeLocaleOfDate(JFXDatePicker date){
        date.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
    }
    
}

