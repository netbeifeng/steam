/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.jsf.beans;

/**
 *
 * @author Chang
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.validation.constraints.NotNull;   
import javax.validation.constraints.Size;  

@ManagedBean(name="user")
@SessionScoped
public class UserBean  implements Serializable {

    @NotNull
    @Size(min=3,max=12)
    private String username;
    
    @NotNull
    @Size(min=3,max=12)
    private String password;
    
    @NotNull
    @Size(min=3,max=12)
    private String passwordConfirm;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
    
    
//        public boolean clickEinloggen() {
//        Steamservices steamservices = new Steamservices();
//        System.out.println(this.getUsername()+"  "+this.getPassword());
//        return steamservices.login(this.getUsername(), this.getPassword());
//    }
}


