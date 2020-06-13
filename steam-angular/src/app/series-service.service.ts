import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Card } from './index/card/card';
import { HttpHeaders } from '@angular/common/http';

const header = new HttpHeaders()
  .set('Content-Type', 'application/x-www-form-urlencoded');


@Injectable({
  providedIn: 'root'
})
export class SeriesService {
  host = 'http://localhost:8080/steam-0.1/steam_service';
  Urls= {
    GetAllCards: this.host + '/series/getSeries', 
    AddCard: this.host + '/series/addOrChangeSeries'
  };

  cardList : Array<Card> = [];

  constructor(private http : HttpClient) { }

  getIdOfSeries(query : string) {
    return this.http.get<any>('https://api.themoviedb.org/3/search/tv?api_key=4e530284712767cef8547ff7f02bc6ad&query='+query).toPromise();
  }

  getPosterOfSeries(seriesId : number, season : number) {
    return this.http.get<any>('https://api.themoviedb.org/3/tv/'+seriesId+'/season/'+season+'?api_key=4e530284712767cef8547ff7f02bc6ad').toPromise();
  }

  addNewCard(card : Card) {
    let body = "username="+card.username+"&title="+card.title+"&season="+card.season+"&genre="+card.genre+"&platform="+card.platform+"&score="+card.rating+"&remark="+card.remark;
    return this.http.post<any>(this.Urls.AddCard, body, {'headers':header} ).toPromise();
  }

   getCardList() {
    // let arr = new Array();
     console.log(this.http.get<any>(this.Urls.GetAllCards).subscribe(data => {
      for(let card of data) {
        let posterLink = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&text=No+image+Available";
        let seriesId = -1;
        this.getIdOfSeries(card.title).then(data => { 
          if(data.total_results >= 1) {
            seriesId = data.results[0].id;
            posterLink = "http://image.tmdb.org/t/p/w400" + data.results[0].poster_path;
            // console.log(seriesId);
            this.getPosterOfSeries(seriesId,card.season).then(_data => {
              
              if(_data.poster_path) {
                posterLink = "http://image.tmdb.org/t/p/w400" + _data.poster_path;
                this.cardList.push(new Card(card.username,card.title,card.season,card.genre,card.platform,card.remark,card.score,seriesId,posterLink));
              } else {
                this.cardList.push(new Card(card.username,card.title,card.season,card.genre,card.platform,card.remark,card.score,seriesId,posterLink));
              }
            });
          } else {
            this.cardList.push(new Card(card.username,card.title,card.season,card.genre,card.platform,card.remark,card.score,seriesId,posterLink));
          }
        });
        
      }
    }));
    this.testCards();
    return this.cardList;
  }



  testCards() {
    // this.cardList.push(new Card("shirosya","Game Of Throne",1,"Action","Netflix","No REMARK","very_good"));
    // this.cardList.push(new Card("shirosya","Game Of Throne",2,"Drama","Skye","No REMsaAaRK","bad"));
    // this.cardList.push(new Card("shirosya","Game Of Throne",3,"Thriller","Netflix","No REMARK","mediocre"));
    // this.cardList.push(new Card("shirosya","Game Of Throne",4,"Action","Skye","No REMARK","good"));
  }

  addCard(card : Card) {
    let index = -1;

    for(let i = 0; i < this.cardList.length; i++) {
      if(this.cardList[i].title == card.title) {
        this.cardList.splice(i,1);
      }
    }
    this.addNewCard(card).then(data => {
      console.log(data.status);
      if(data.status) {
        console.log(data.status);
        this.cardList.push(card);
      }
    });
  }
  // createCard(
  //   username : string,
  //   title : string,
  //   season : number,
  //   genre : string,
  //   platform : string,
  //   remark : string,
  //   rating : string
  // ) {
  //   return new Card(username,title,season,genre,platform,remark,rating);
  // }



  // getCardList() {
  //   this.cardList.push(this.createCard("shirosya","Game Of Throne",1,"Action","Netflix","No REMARK","Good"));
  //   this.cardList.push(this.createCard("shirosya","Wjhdfa",1,"Acti12on","Netflix","No RE125MARK","Good"));
  //   this.cardList.push(this.createCard("shirosya","AS325h",1,"aF","fngf","12o dsg","125"));
  //   return this.cardList;
  // }
  
}
