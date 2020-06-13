import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Card } from '../index/card/card';
import { SeriesService } from '../series-service.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  username : string = "";
  greeting_text : string = "";
  queryParams : any;

  queryText : string = "";
  cardList : Array<Card> = [];
  _cardList : Array<Card> = [];
  ngOnInit(): void {
    let date = new Date();
    if (date.getHours() >= 18 && date.getHours() <= 21) {
      this.greeting_text = this.username + ", Guten Abend!";
    } else if (date.getHours() > 21 || date.getHours() <= 6) {
      this.greeting_text = this.username + ", Gute Nacht!";
    } else if (date.getHours() > 6 || date.getHours() < 18) {
      this.greeting_text = this.username + ", Guten Tag!";
    }

  }

  async search(query : string) {
    this._cardList = new Array<Card>();
    for(let card of this.cardList) {
      if(card.title.toLowerCase().indexOf(query.toLowerCase()) != -1) {
        this._cardList.push(card);
      }
    }
  }

  constructor(public route : ActivatedRoute, public service : SeriesService) {
    this.queryParams = this.route.queryParams;
    this.username = this.queryParams._value.username;
    this.cardList = this.service.getCardList();
  }
}
