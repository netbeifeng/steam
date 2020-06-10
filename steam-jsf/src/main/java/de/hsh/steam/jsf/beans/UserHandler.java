/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.jsf.beans;

import de.hsh.steam.jsf.backend.application.Steamservices;
import java.util.Date;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;

/**
 *
 * @author Chang
 */
@ManagedBean(name="userhandler")
@SessionScoped
public class UserHandler implements Serializable {
    
    private String error;
    private String greetingtext;
    
    private String userListHtml = "";
    
    private Date date;
    
    private ArrayList<String> userList = new ArrayList<String>();
    
    private boolean night = false;
    
    public Date getDate() {
        return date;
    }
    
    public String getUserListHtml(CardHandler cardhandler) {
        if(cardhandler.getCardList().isEmpty())
            cardhandler.getAllCards();
        for(CardBean card : cardhandler.getCardList()) {
            if(!userList.contains(card.getEditBy())) {
                userList.add(card.getEditBy());
            }
        }
        for(String user : userList) {
            userListHtml += "<li><a id='"+user+"' href='/steam-jsf/faces/cateCard.xhtml?param=user&amp;user="+user+"'>"+user+"</a></li>";
        }
        return userListHtml;
    }
     
    public String backToMySteam() {
        if(night == false) {
            return "light";
        } else {
            return "dark";
        }
     }
    
    public String backToSearch() {
                if(night == false) {
            return "search_light";
        } else {
            return "search_dark";
        }
    }
    

    
    public boolean getNight() {
        return night;
    }
    
    public void setNight(boolean night) {
        this.night = night;
    }
    
    public String getError() {
        return error;
    }
    
    public String getGreetingText() {
        date = new Date();
        if(date.getHours() >= 18 && date.getHours() <= 21) {
            greetingtext = "Good Evening! Today is ";
        } else if (date.getHours() > 21 || date.getHours() <= 6) {
            greetingtext = "Good Night! Today is ";
        } else {
            greetingtext = "Good Morning! Today is ";
        }  
        return greetingtext;
    }
    
    public void print(String s) {
        System.out.println("Output: "+s);
    }
    
    
    public void setError(String error) {
        this.error = error;
    }
       
    public String clickEinloggen(UserBean user) {
        Steamservices steamservices = new Steamservices();
        System.out.println(user.getUsername());
        if(steamservices.login(user.getUsername(), user.getPassword()) == true) {
            return "light.xhtml";
        } else {
            error = "<script>alert(Einloggen geht schief.)</script>";
            return "error.xhtml";
        }
    }
    
    public String clickRegisterien(UserBean user) {
        Steamservices steamservices = new Steamservices();
//        System.out.println("A: "+user.getPasswordConfirm());
//        System.out.println("B: "+user.getPassword());
//        System.out.println(user.getPasswordConfirm().equals(user.getPassword()));
        if(!user.getPasswordConfirm().equals(user.getPassword())) {
            error = "<script>alert('Passwörter sind nicht gleich.');</script>";
            return "index.xhtml";
        } else {
            if(steamservices.newUser(user.getUsername(),user.getPassword()) == true) {
                error = "<script>alert('Erfolgreich registeriert.');</script>";
                return "index.xhtml";
            } else {
                error = "<script>alert('Fehler aufgetreten.');</script>";
                return "index.xhtml";
            }
        }
//        System.out.println(script);
//        return "index.xhtml";
//        if(steamservices.newUser(user.getUsername(),user.getPassword()) == true) {
//            return "";
//        } else {
//            return "index.xhtml";
//        }
    }
}
