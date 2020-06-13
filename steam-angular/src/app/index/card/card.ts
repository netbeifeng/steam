// 

export class Card {

    username : String = null; 
    title : string = "";
    season : number = -1;
    genre : string = "";
    platform : string = "";
    remark : string = "";
    rating : string = "";
    editMode : boolean = false;

    posterLink : string = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&text=No+image+Available";
    seriesId : number = -1;

    constructor(
        username : string,
        title : string,
        season : number,
        genre : string,
        platform : string,
        remark : string,
        rating : string,
        seriesId : number,
        posterLink : string) {
            this.seriesId = seriesId;
            this.posterLink = posterLink;
            this.username = username;
            this.title = title;
            this.season = season;
            this.genre = genre;
            this.platform = platform;
            this.remark = remark;
            this.rating = rating;
        }


    
}