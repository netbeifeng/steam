import { Component, OnInit } from '@angular/core';
import { UserService } from '../user-service.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  ngOnInit(): void {
  }

  title:string = 'STEAM';

  //form_register
  username_register:string = "";
  password_register:string = "";
  password_register_confirm:string = "";

  //form_login
  username:string = "";
  password:string = "";

  isRegister:boolean = false;
  tips:boolean = false;

  constructor(private userservice: UserService) {}

  clickSwitch(){
    this.isRegister = !this.isRegister;
  }

  async clickRegister() {
    if(this.password_register == this.password_register_confirm) {
      //doRegisger()
      let res = await this.userservice.register(this.username_register,this.password_register);
      console.log(res);
      if(res.status.indexOf('suc') != -1) {
        this.isRegister = !this.isRegister;
      } else if(res.status.indexOf('existed') != -1) {
        alert("Dieser Account existiert bereits!");
      } else {
        alert("Bitte geben Sie 6-12 Ziffern oder Buchstaben ein.");
      }
      this.tips = false;
    } else {
      this.tips = true;
      this.password_register = "";
      this.password_register_confirm = "";
    }
  }

  async clickLogin() {
    //doLogin()
    // alert()
    let res = await this.userservice.login(this.username,this.password);
    console.log(res);
    if(res.status.indexOf("suc") != -1) {
      //Redirect
      window.location.replace("index?username="+this.username);
    } else {
      alert("Falsche Benutzername oder Password.");
    }
  }
}
