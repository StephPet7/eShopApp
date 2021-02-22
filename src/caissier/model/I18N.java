/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Shaquillo
 */
public final class I18N {
    private static final ObjectProperty<Locale> locale;
    
    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }
    
    public static List<Locale> getSupportedLocales(){
        return new ArrayList<>(Arrays.asList(Locale.ENGLISH, Locale.FRENCH));
    }
    
    public static Locale getDefaultLocale(){
        Locale sysDefault= Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH ;
    }
    
    public static Locale getLocale(){
        return locale.get();
    }
    
    public static void setLocale(Locale locale){
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }
    
    public static ObjectProperty<Locale> localeProperty(){
        return locale;
    }
    
    public static String get(final String key){
        ResourceBundle rs = ResourceBundle.getBundle("caissier/language/Bundle", getLocale());
        return rs.getString(key);
    }
    
    public static StringBinding createStringBinding(final String key){
        return Bindings.createStringBinding(() -> get(key), locale);
    }
    
    public static StringBinding createStringBinding(Callable<String> func){
        return Bindings.createStringBinding(func, locale);
    }
}
