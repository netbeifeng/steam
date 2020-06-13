import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
// import axios from 'axios';
// import qs from 'qs';
// const axios = require('axios');
// const qs = require('qs');

const user_header = new HttpHeaders()
  .set('Content-Type', 'application/x-www-form-urlencoded');

@Injectable({
  providedIn: 'root'
})

export class UserService {
  host = 'http://localhost:8080/steam-0.1/steam_service';
  Urls= {
    Login: this.host + '/user/login', 
    Register: this.host +  '/user/register', 
  };
  
  constructor(private http: HttpClient) { }

  async login(username:string,password:string) {
    let response;

    let body = 'username='+username+'&password='+password;

    return this.http.post<any>(this.Urls.Login, body, {'headers':user_header} ).toPromise();
  //   var response;
  //   const login = async () => {
  //       try {
  //           // request data object
  //           const data = {
  //               username: username,
  //               password: password
  //           };
    
  //           // set the headers
  //           const config = {
  //               headers: {
  //                   'Content-Type': 'application/x-www-form-urlencoded'
  //               }
  //           };
    
  //           const res = await axios.post(this.Urls.Login, qs.stringify(data), config);
  //           console.log(res.data);
  //           response = res.data;
  //       } catch (err) {
  //           console.error(err);
  //       }
  //   };
  //   login();
  //   return response;
  }

  register(username:string,password:string) {
    let response;

    let body = 'username='+username+'&password='+password;

    return this.http.post<any>(this.Urls.Register, body , {'headers':user_header} ).toPromise();
  //     var response;
  //     const register = async () => {
  //       try {
  //           // request data object
  //           const data = {
  //               username: username,
  //               password: password
  //           };
    
  //           // set the headers
  //           const config = {
  //               headers: {
  //                   'Content-Type': 'application/x-www-form-urlencoded'
  //               }
  //           };
    
  //           const res = await axios.post(this.Urls.Register, qs.stringify(data), config);
  //           console.log(res.data);
  //           response = res.data;
  //       } catch (err) {
  //           console.error(err);
  //       }
  //   };
  //   register();
  //   return response;
  }
}
