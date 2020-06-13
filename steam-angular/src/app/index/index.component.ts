import { Component, OnInit, HostListener, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SeriesService } from '../series-service.service';
import { Card } from './card/card';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
  providers: [SeriesService]
})
export class IndexComponent implements OnInit {
  username : string = "";
  greeting_text : string = "";
  queryParams : any;
  cardList : Array<Card> = [];

  toBeAddCard : Card = new Card(this.username,"Muster",1,"Action","Netflix","Kommentar hier","good",-1,"https://dummyimage.com/300x400/F8F8F8/727878.jpg&text=No+image+Available");

  inAddMode : boolean = false;

  // ngAfterViewInit(){
  //   document.getElementById("_body").style.height = document.body.scrollHeight+400+'px';
  //   console.log(document.body.offsetHeight)
  //   console.log(document.body.scrollHeight)
  // }

  constructor(public route : ActivatedRoute, private service : SeriesService) {
    // console.log(this);
    this.queryParams = this.route.queryParams;
    this.username = this.queryParams._value.username;
    // console.log(this.username);
    // for(let card of service.getCardList()) {
    //   this.cardList.push(new CardComponent(service));
    // }
    this.cardList = service.getCardList();
  }

  // @HostListener('window:resize', ['$event']) onResize(event) {
  //   console.log(event.target.innerHeight);
  //   console.log(window.innerHeight)
  // }

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

  saveCard(card : Card) {
    this.inAddMode = false;
    card.username = this.username;
    // alert("A")
    this.service.getIdOfSeries(card.title).then(data => { 
      console.log(data)
      if(data.total_results >= 1) {
        card.seriesId = data.results[0].id;
        card.posterLink = "http://image.tmdb.org/t/p/w400" + data.results[0].poster_path;
        this.service.getPosterOfSeries(card.seriesId,card.season).then(_data => {
          console.log(_data)
          if(_data.poster_path) {
            card.posterLink = "http://image.tmdb.org/t/p/w400" + _data.poster_path;
            console.log(card.posterLink);
            this.service.addCard(card);
          } else {
            this.service.addCard(card);
          }
        });
      } else {
        card.seriesId = -1;
        card.posterLink = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&text=No+image+Available";
        this.service.addCard(card);
      }
    });
    this.toBeAddCard = new Card(this.username,"Muster",1,"Action","Netflix","Kommentar hier","good",-1,"https://dummyimage.com/300x400/F8F8F8/727878.jpg&text=No+image+Available");
  }

  switchToAddMode() {
    console.log(this.inAddMode);
    this.inAddMode = true;
  }

  switchToEditMode(card : Card) {
    card.editMode = true;
  }

  switchToViewMode(card : Card) {
    card.editMode = false;
    
    this.service.getIdOfSeries(card.title).then(data => { 
      console.log(data)
      if(data.total_results >= 1) {
        card.seriesId = data.results[0].id;
        card.posterLink = "http://image.tmdb.org/t/p/w400" + data.results[0].poster_path;
        this.service.getPosterOfSeries(card.seriesId,card.season).then(_data => {
          console.log(_data)
          if(_data.poster_path) {
            card.posterLink = "http://image.tmdb.org/t/p/w400" + _data.poster_path;
            console.log(card.posterLink);
            this.service.addCard(card);
          } else {
            this.service.addCard(card);
          }
        });
      } else {
        card.seriesId = -1;
        card.posterLink = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&text=No+image+Available";
        this.service.addCard(card);
      }
    });
  }



}
