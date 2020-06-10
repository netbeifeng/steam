/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.jsf.beans;


import de.hsh.steam.jsf.backend.application.Genre;
import java.io.Serializable;
import java.util.ArrayList;
import de.hsh.steam.jsf.backend.application.Series;
import de.hsh.steam.jsf.backend.application.User;
import de.hsh.steam.jsf.backend.application.Rating;
import de.hsh.steam.jsf.backend.application.Score;
import de.hsh.steam.jsf.backend.application.Streamingprovider;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import de.hsh.steam.jsf.backend.application.Steamservices;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author Chang
 */

@ManagedBean(name="cardhandler")
@SessionScoped
public class CardHandler implements Serializable {
    
    private ArrayList<CardBean> cardList = new ArrayList<CardBean>();
    private String allCardsHtml = "";
    
    private String query="";
    private String searchHtml = "";
    
    private String param;
    private String cate;
    private String cateHtml = "";
    
    public void setCate(String cate) {
        this.cate = cate;
    }
    
    public String getCate() {
        return cate;
    }
    
    public String getCateBySteamingProviderHtml(Streamingprovider streamingprovider) {
        this.cate = "Catalog By Platform: " + streamingprovider.name();
        if(cardList.isEmpty())
            getAllCards();
        cateHtml="";
        for(CardBean card : cardList) {
            if(card.getStreamingprovider().equals(streamingprovider)) {
                cateHtml += card.getCardHtml(false);
            }
        }
        return cateHtml;
    }    
    
    public String getCateByScoreHtml(Score score) {
        this.cate = "Catalog By Rating: " + score.name();
        if(cardList.isEmpty())
            getAllCards();
        cateHtml="";
        for(CardBean card : cardList) {
            if(card.getScore().equals(score)) {
                cateHtml += card.getCardHtml(false);
            }
        }
        return cateHtml;
    }
    
    public String getCateByGenreHtml(Genre genre) {
        this.cate = "Catalog By Genre: " + genre.name();
        if(cardList.isEmpty())
            getAllCards();
        cateHtml="";
        for(CardBean card : cardList) {
            if(card.getGenre().equals(genre)) {
                cateHtml += card.getCardHtml(false);
            }
        }
        return cateHtml;
    }
    
    public String getCateByUserHtml(String user) {
        this.cate = "Catalog By User: " + user;
        if(cardList.isEmpty())
            getAllCards();
        cateHtml="";
        for(CardBean card : cardList) {
            if(card.getEditBy().equals(user)) {
                cateHtml += card.getCardHtml(false);
            }
        }
        return cateHtml;
//        return allCardsHtml;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
    public String getQuery() {
        return query;
    }
    
    public String getSearchHtml() {
        getAllCards();
        searchHtml = "";
        System.out.println("Size is:" + cardList.size());
        for(CardBean card: cardList) {
//            System.out.println("111111111:  " +card.getTitle().toLowerCase());
//            System.out.println("222222222:  " +query.toLowerCase());
            if(card.getTitle().toLowerCase().indexOf(query.toLowerCase()) != -1) {
                searchHtml += card.getCardHtml(false);
            }
        }
        return searchHtml;
    }
    
    public void deleteCard(CardBean card) {
        for(CardBean _card : cardList) {
            if(_card.getTitle().equals(card.getTitle())) {
                cardList.remove(_card);
            }
        }
    }
    
    public String editCard(CardBean card,UserBean user,UserHandler userhandler) {
        System.out.println("Edit Card.");
//        System.out.println(card.getTitle());
//        System.out.println(card.getSeason());
//        System.out.println(card.getGenre());
//        System.out.println(card.getStreamingprovider());
//        System.out.println(card.getEditBy());
//        System.out.println(card.getScore());
//        System.out.println(card.getRemark());
        Steamservices steamservices = new Steamservices();
        steamservices.addOrModifySeries(card.getTitle(), card.getSeason(), card.getGenre(), card.getStreamingprovider(), user.getUsername(), card.getScore(), card.getRemark());
        if(userhandler.getNight() == true) {
            return "dark.xhtml";
        }
        return "light.xhtml";
    }
    
    public String addNewCard(CardBean card,UserBean user,UserHandler userhandler) {
        System.out.println("Add New Card.");
        Steamservices steamservices = new Steamservices();
        card.setEditBy(user.getUsername());
        steamservices.addOrModifySeries(card.getTitle(), card.getSeason(), card.getGenre(), card.getStreamingprovider(), card.getEditBy(), card.getScore(), card.getRemark());
//        cardList.add(card);
        if(userhandler.getNight() == true) {
            return "dark.xhtml";
        }
        return "light.xhtml";
    }
    
    public String getAllCardsHtml() {
        getAllCards();
        System.out.println("cardList size" +cardList.size());
        for(CardBean card: cardList) {
            allCardsHtml += card.getCardHtml(true);
        }
        return allCardsHtml;
    }
    
    public void test() {
        CardBean b = new CardBean();

        b.setEditBy("2145125");
                 b.setGenre(Genre.Action);
                 b.setTitle("Game Of Throne");
                 b.setSeason(5);
                 b.setStreamingprovider(Streamingprovider.AmazonPrime);
                 b.setRemark("aga");
                 b.setScore(Score.mediocre);
        cardList.add(b);
    }
    
    public void getAllCards() {
        if(this.cardList.isEmpty()) {
            Steamservices steamservices = new Steamservices();
        ArrayList<Series> series = steamservices.getAllSeries();
            for(Series s: series) {
    //            System.out.println("??????????????????????????!!!!!!!!!!!!");
                for(User u: s.getSeenBy()) {
                     Rating r = steamservices.getRating(s.getTitle(), u.getUsername());
                     CardBean card = new CardBean();
                     card.setEditBy(u.getUsername());
                     card.setGenre(s.getGenre());
                     card.setTitle(s.getTitle());
                     card.setSeason(s.getNumberOfSeasons());
                     card.setStreamingprovider(s.getStreamedBy());
                     card.setRemark(r.getRemark());
                     card.setScore(r.getScore());
                     cardList.add(card);
                }
            }
        }
    }
    
    public ArrayList<CardBean> getCardList() {
        return this.cardList;
    }
    
    public String navigateToDarkMode() {
        return "dark.xhtml";
    }
    
    public String navigateToLightMode() {
        return "light.xhtml";
    }
    
    public String setParam(String user) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        param = paramMap.get("param");
        if(param.equals("user")) {
            if(!paramMap.containsKey("user"))
                return getCateByUserHtml(user);
            else 
                return getCateByUserHtml(paramMap.get("user"));
        } else if(param.equals("genre")) {
            String genre = paramMap.get("genre");
            if(genre.equals("thriller")) 
                return getCateByGenreHtml(Genre.Thriller);
            else if(genre.equals("sciencefiction")) 
                return getCateByGenreHtml(Genre.ScienceFiction);
            else if(genre.equals("drama")) 
                return getCateByGenreHtml(Genre.Drama);
            else if(genre.equals("action")) 
                return getCateByGenreHtml(Genre.Action);
            else if(genre.equals("comedy")) 
                return getCateByGenreHtml(Genre.Comedy);
            else
                return getCateByGenreHtml(Genre.Documentary);
        } else if(param.equals("platform")) {
            String platform = paramMap.get("platform");
            if(platform.equals("netflix"))
                return getCateBySteamingProviderHtml(Streamingprovider.Netflix);
            else if(platform.equals("amazonprime"))
                return getCateBySteamingProviderHtml(Streamingprovider.AmazonPrime);
            else 
                return getCateBySteamingProviderHtml(Streamingprovider.Skye);
        } else {
            String score = paramMap.get("score");
            if(score.equals("verygood"))
                return getCateByScoreHtml(Score.very_good);
            else if(score.equals("good"))
                return getCateByScoreHtml(Score.good);
            else if(score.equals("mediorce"))
                return getCateByScoreHtml(Score.mediocre);
            else
                return getCateByScoreHtml(Score.bad);
        } 
    }
}
