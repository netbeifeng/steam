import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'
@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  username:string = "";
  greeting_text:string = "";
  constructor(public route : ActivatedRoute) {
    // console.log(this);
    this.username = this.route.queryParams._value.username;
    // console.log(this.username);
  }

  ngOnInit(): void {
    let date = new Date();
    if (date.getHours() >= 18 && date.getHours() <= 21) {
      this.greeting_text = this.username + ", Guten Abend!";
    } else if (date.getHours() > 21 || date.getHours() <= 6) {
      this.greeting_text = this.username + ", Gute Nacht!!";
    } else if (date.getHours() > 6 || date.getHours() < 18) {
      this.greeting_text = this.username + ", Guten Tag!";
    }
  }



}
