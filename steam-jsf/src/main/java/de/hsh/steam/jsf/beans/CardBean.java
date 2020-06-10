/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.jsf.beans;

import de.hsh.steam.jsf.backend.application.Genre;
import de.hsh.steam.jsf.backend.application.Score;
import de.hsh.steam.jsf.backend.application.Streamingprovider;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import javax.validation.constraints.NotNull;  
import javax.validation.constraints.Min;  
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 *
 * @author Chang
 */
@ManagedBean(name="card")
@RequestScoped
public class CardBean implements Serializable {

    private int seriesId;
    private String posterPath;
    
    @NotNull
    private String title;
    
    @NotNull
    @Min(1)        
    private int season;
    
    @NotNull
    private Genre genre;
    
    @NotNull
    private Score score;
    
    @NotNull
    private Streamingprovider streamingprovider;
    
    private String remark;
    @NotNull
    private String editBy;
    
    private String html;
    
    private CardDetail detailInfo;
    private String detailHtml;
    
    private String styles;
    
    public String getStyles() {
        return styles;
    }
    
    public String getPosterPath() {
        return posterPath;
    }
    
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    
    public int getSeriesId() {
        return seriesId;
    }
    
    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }    
    
    public int getSeason() {
        return season;
    }
    
    public void setSeason(int season) {
        this.season = season;
    }    
    
    public Genre getGenre() {
        return genre;
    }
    
    public void setGenre(Genre genre) {
        this.genre = genre;
    }          
    
    public Score getScore() {
        return score;
    }
    
    public void setScore(Score score) {
        this.score = score;
    }              
    
    public Streamingprovider getStreamingprovider() {
        return streamingprovider;
    }
    
    public void setStreamingprovider(Streamingprovider streamingprovider) {
        this.streamingprovider = streamingprovider;
    }
    
    public String getEditBy() {
        return editBy;
    }
    
    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
    
    public void getDetailInfo() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet query = new HttpGet("https://api.themoviedb.org/3/tv/"+this.seriesId+"?api_key=4e530284712767cef8547ff7f02bc6ad");
        CloseableHttpResponse response = null;
        try{
            response = httpclient.execute(query);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        String result = null;
        HttpEntity entity = response.getEntity();
        try{
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject json_response = new JSONObject(result);
        detailInfo = new CardDetail();
        if(this.seriesId == -1) {
            detailInfo.setBackdropPath("http://projekt.alias.zone:801/res/bg.png");
            styles = "body {background: linear-gradient(to bottom, rgba(0, 0, 0, 1) 0%, rgba(0, 0, 0, 0.5) 50%, rgba(0, 0, 0, 0) 100%), url('"+detailInfo.getBackdropPath()+"') no-repeat center center fixed; background-size:cover; background-attachment:fixed;}";
            this.posterPath = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&amp;text=No+image+Available";
            detailInfo.setAirDate("- No Data -");
            detailInfo.setAliasName(" Season "+ this.season +" ");
            detailInfo.setEpisodeCount(0);
            detailInfo.setOverview("Sorry, No Overview.");
        } else {
//            detailInfo.setBackdropPath("http://image.tmdb.org/t/p/w400"+json_response.getString("backdrop_path"));
            if(json_response.has("backdrop_path")&& !json_response.get("backdrop_path").toString().equals("null")) 
                detailInfo.setBackdropPath("http://image.tmdb.org/t/p/w400"+json_response.getString("backdrop_path"));
            else
                detailInfo.setBackdropPath("http://projekt.alias.zone:801/res/bg.png");
            styles = "body {background: linear-gradient(to bottom, rgba(0, 0, 0, 1) 0%, rgba(0, 0, 0, 0.5) 50%, rgba(0, 0, 0, 0) 100%), url('"+detailInfo.getBackdropPath()+"') no-repeat center center fixed; background-size:cover; background-attachment:fixed;}";
            detailInfo.setSeasons(json_response.getJSONArray("seasons")); 
            if(json_response.getInt("number_of_seasons") == 1) {
                this.posterPath = "http://image.tmdb.org/t/p/w400" + json_response.getString("poster_path");
    //            if(json_response.has("first_air_date"))
                    detailInfo.setAirDate(json_response.getString("first_air_date"));
    //            else 
    //                detailInfo.setAirDate("1970-01-01");
    //            if(!json_response.get("name").toString().equals("null"))
                    detailInfo.setAliasName(json_response.getString("name")+": Season 1");
    //            else 
    //                detailInfo.setAliasName(" Season 1 ");
    //            System.out.println("*(H(*GH(*#*(TG"+json_response.get("number_of_episodes").toString());
    //            if(!json_response.get("number_of_episodes").toString().equals("null"))
                    detailInfo.setEpisodeCount(json_response.getInt("number_of_episodes"));
    //            else
    //                detailInfo.setEpisodeCount(0);
    //            if(!json_response.get("overview").toString().equals("null"))
                    detailInfo.setOverview(json_response.getString("overview"));
    //            else 
    //                detailInfo.setOverview("Sorry, No Overview.");
            } else if(json_response.getInt("number_of_seasons") == 0) {
                detailInfo.setBackdropPath("http://projekt.alias.zone:801/res/bg.png");
                styles = "body {background: linear-gradient(to bottom, rgba(0, 0, 0, 1) 0%, rgba(0, 0, 0, 0.5) 50%, rgba(0, 0, 0, 0) 100%), url('"+detailInfo.getBackdropPath()+"') no-repeat center center fixed; background-size:cover; background-attachment:fixed;}";
                this.posterPath = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&amp;text=No+image+Available";
                detailInfo.setAirDate("- No Data -");
                detailInfo.setAliasName(" Season "+ this.season +" ");
                detailInfo.setEpisodeCount(0);
                detailInfo.setOverview("Sorry, No Overview.");
            } else {
                if(detailInfo.getSeasons().getJSONObject(0).getString("name").indexOf("1") != -1) {
                    this.season--;
                }
                this.posterPath = "http://image.tmdb.org/t/p/w400" + detailInfo.getSeasons().getJSONObject(season).getString("poster_path");
    //            if(detailInfo.getSeasons().getJSONObject(season).has("first_air_date"))
    //                System.out.println(detailInfo.getSeasons().getJSONObject(season).toString());
                    detailInfo.setAirDate(detailInfo.getSeasons().getJSONObject(season).getString("air_date"));
    //            else 
    //                detailInfo.setAirDate("1970-01-01");
    //            if(!detailInfo.getSeasons().getJSONObject(season).get("name").toString().equals("null"))
                    detailInfo.setAliasName(detailInfo.getSeasons().getJSONObject(season).getString("name"));
    //            else 
    //                detailInfo.setAliasName(" Season 1 ");
    //            if(!detailInfo.getSeasons().getJSONObject(season).get("episode_count").toString().equals("null"))
                    detailInfo.setEpisodeCount(detailInfo.getSeasons().getJSONObject(season).getInt("episode_count"));
    //            else 
    //                detailInfo.setEpisodeCount(0);
    //            if(!detailInfo.getSeasons().getJSONObject(season).get("overview").toString().equals("null"))
                    detailInfo.setOverview(detailInfo.getSeasons().getJSONObject(season).getString("overview"));
    //            else 
    //                detailInfo.setOverview("Sorry, No Overview.");
                }
        }

    }
    
    public String getPoster() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet first_query = new HttpGet("https://api.themoviedb.org/3/search/tv?api_key=4e530284712767cef8547ff7f02bc6ad&query="+this.title.replace(" ","&nbsp;"));
        CloseableHttpResponse response = null;
        try{
            response = httpclient.execute(first_query);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        String result = null;
        HttpEntity entity = response.getEntity();
        try{
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JSONObject json_response = new JSONObject(result);
        if(json_response.getInt("total_results") == 0) {
            this.seriesId = -1;
            this.posterPath = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&amp;text=No+image+Available";
        } else {
            JSONArray json_results = json_response.getJSONArray("results");
//            this.seriesId = json_results.
                    
                    JSONObject first_result = json_results.getJSONObject(0);
                    this.seriesId = first_result.getInt("id");
                    if(first_result.has("poster_path") &&!first_result.get("poster_path").toString().equals("null"))
                        this.posterPath = "http://image.tmdb.org/t/p/w400" + first_result.getString("poster_path");
                    else 
                        this.posterPath = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&amp;text=No+image+Available";
                    HttpGet second_query = new HttpGet("https://api.themoviedb.org/3/tv/"+this.seriesId+"/season/"+this.season+"?api_key=4e530284712767cef8547ff7f02bc6ad");
                    try{
                        response = httpclient.execute(second_query);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    entity = response.getEntity();
                    try{
                        if (entity != null) {
                            result = EntityUtils.toString(entity);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONObject json_response_second_query = new JSONObject(result);

                    if(json_response_second_query.has("poster_path")&&!json_response_second_query.get("poster_path").toString().equals("null"))
                        this.posterPath = "http://image.tmdb.org/t/p/w400" + json_response_second_query.getString("poster_path");
                    else 
                        this.posterPath = "https://dummyimage.com/300x400/F8F8F8/727878.jpg&amp;text=No+image+Available";

                    System.out.println(posterPath);
        }
//        System.out.println(result);
        return result;
    }
    
    public String getCardDetailHtml() {
        getDetailInfo();
        detailHtml = "<div class=\"column-md\"><div><img src=\""+this.posterPath+"\" id=\"poster\" /></div></div>";
        detailHtml += "<div class=\"column-md\"><h1 id=\"title\">"+this.title+"<span id=\"season\"> ("+detailInfo.getAliasName()+")</span></h1><div class=\"row\">";
        if(this.score == Score.bad) 
            detailHtml += "<div class=\"column\" style=\"height:auto; width: 10rem;\">Rating: <span id=\"span_rating\" class=\"span_rating_bad\">"+this.score+" <i class=\"fas fa-star-half\"></i></span></div>";
        else if(this.score == Score.good) 
            detailHtml += "<div class=\"column\" style=\"height:auto; width: 10rem;\">Rating: <span id=\"span_rating\" class=\"span_rating_good\">"+this.score+" <i class=\"fas fa-star\"></i></span></div>";
        else if(this.score == Score.mediocre)
            detailHtml += "<div class=\"column\" style=\"height:auto; width: 10rem;\">Rating: <span id=\"span_rating\" class=\"span_rating_mediocre\">"+this.score+" <i class=\"fas fa-star\"><i class=\"fas fa-star-half\"></i></i></span></div>";
        else
            detailHtml += "<div class=\"column\" style=\"height:auto; width: 10rem;\">Rating: <span id=\"span_rating\" class=\"span_rating_veryGood\">"+this.score+" <i class=\"fas fa-star\"><i class=\"fas fa-star\"></i></i></span></div>";
        
        detailHtml += "<div class=\"column\" style=\"height:auto; width: 16rem;\">Released-Date: <span class=\"date\">"+detailInfo.getAirDate()+"</span></div>";
        detailHtml += "<div class=\"column\" style=\"height:auto; width: 10rem;\">Episode Count: <span class=\"episode_count\">"+detailInfo.getEpisodeCount()+"</span></div></div>";
        detailHtml += "<h3>Overview</h3><div id=\"overview\"><p id=\"beschreibung\">"+detailInfo.getOverview()+"</p></div>";

        detailHtml += "<div class=\"row\"><div class=\"column\" style=\"height: auto; width: 20rem;\"><h2><strong>Genre</strong></h2><span class=\"span_genre\">"+this.genre+"</span></div>";
        if(this.streamingprovider == Streamingprovider.Netflix)
            detailHtml += "<div class=\"column\" style=\"height: auto; width: 20rem;\"><h2><strong>Platform</strong></h2><span class=\"span_platform\"><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"200\" height=\"58.742\" viewBox=\"0 0 1024 276.742\"><path d=\"M140.803 258.904c-15.404 2.705-31.079 3.516-47.294 5.676L44.051 119.724v151.073C28.647 272.418 14.594 274.58 0 276.742V0h41.08l56.212 157.021V0h43.511v258.904zm85.131-157.558c16.757 0 42.431-.811 57.835-.811v43.24c-19.189 0-41.619 0-57.835.811v64.322c25.405-1.621 50.809-3.785 76.482-4.596v41.617l-119.724 9.461V0h119.724v43.241h-76.482v58.105zm237.284-58.104h-44.862V242.15c-14.594 0-29.188 0-43.239.539V43.242h-44.862V0H463.22l-.002 43.242zm70.266 55.132h59.187v43.24h-59.187v98.104h-42.433V0h120.808v43.241h-78.375v55.133zm148.641 103.507c24.594.539 49.456 2.434 73.51 3.783v42.701c-38.646-2.434-77.293-4.863-116.75-5.676V0h43.24v201.881zm109.994 49.457c13.783.812 28.377 1.623 42.43 3.242V0h-42.43v251.338zM1024 0l-54.863 131.615L1024 276.742c-16.217-2.162-32.432-5.135-48.648-7.838l-31.078-79.994-31.617 73.51c-15.678-2.705-30.812-3.516-46.484-5.678l55.672-126.75L871.576 0h46.482l28.377 72.699L976.705 0H1024z\" fill=\"#d81f26\"/></svg></span></div></div>";
        else if(this.streamingprovider == Streamingprovider.AmazonPrime) 
            detailHtml += "<div class=\"column\" style=\"height: auto; width: 20rem;\"><h2><strong>Platform</strong></h2><span class=\"span_platform\"><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"160.3\" height=\"50.3\" viewBox=\"0 0 800.3 246.3\"><path d=\"M396.5 246.3v-.4c.4-.5 1.1-.8 1.7-.7 2.9-.1 5.7-.1 8.6 0 .6 0 1.3.2 1.7.7v.4h-12z\" fill=\"#d1effa\"/><path d=\"M408.5 245.9c-4-.1-8-.1-12 0-5.5-.3-11-.5-16.5-.9-14.6-1.1-29.1-3.3-43.3-6.6-49.1-11.4-92.2-34.3-129.8-67.6-3.5-3.1-6.8-6.3-10.2-9.5-.8-.7-1.5-1.7-1.9-2.7-.6-1.4-.3-2.9.7-4s2.6-1.5 4-.9c.9.4 1.8.8 2.6 1.3 35.9 22.2 75.1 38.4 116.2 48 13.8 3.2 27.7 5.7 41.7 7.5 20.1 2.5 40.4 3.4 60.6 2.7 10.9-.3 21.7-1.3 32.5-2.7 25.2-3.2 50.1-8.9 74.2-16.9 12.7-4.2 25.1-9 37.2-14.6 1.8-1 4-1.3 6-.8 3.3.8 5.3 4.2 4.5 7.5-.1.4-.3.9-.5 1.3-.8 1.5-1.9 2.8-3.3 3.8-11.5 9-23.9 16.9-37 23.5-24.7 12.5-51.1 21.4-78.3 26.5-15.7 2.8-31.5 4.5-47.4 5.1zM260.4 43.2c2.5-1.5 5.1-3.1 7.8-4.5 7-3.6 14.8-5.4 22.7-5 5.7.3 10.9 1.9 14.9 6.1 3.8 3.9 5.2 8.7 5.6 13.9.1 1.1.1 2.2.1 3.4v51.8c0 4.5-.6 5.1-5.1 5.1h-12.2c-.8 0-1.6 0-2.4-.1-1.2-.1-2.2-1.1-2.4-2.3-.2-1.1-.2-2.2-.2-3.3V62c.1-1.9-.1-3.7-.6-5.5-.8-3.1-3.6-5.3-6.8-5.5-5.9-.4-11.8.8-17.2 3.3-.8.2-1.3 1-1.2 1.8v52.6c0 1 0 1.9-.2 2.9 0 1.4-1.1 2.4-2.5 2.4-1.5.1-3 .1-4.6.1h-10.6c-3.7 0-4.5-.9-4.5-4.6V62.2c0-1.7-.1-3.5-.5-5.2-.7-3.4-3.6-5.8-7-6-6-.4-12.1.8-17.5 3.4-.8.2-1.3 1.1-1.1 1.9v53.3c0 3.7-.8 4.5-4.5 4.5H197c-3.5 0-4.4-1-4.4-4.4V40.3c0-.8.1-1.6.3-2.4.4-1.2 1.6-1.9 2.8-1.9h12.5c1.8 0 2.9 1.1 3.5 2.8.5 1.4.8 2.7 1.3 4.2 1 0 1.6-.7 2.3-1.1 5.5-3.4 11.3-6.3 17.8-7.5 5-1 10-1 15 0 4.7 1 8.9 3.8 11.6 7.8.2.3.4.5.6.7-.1.1 0 .1.1.3z\" fill=\"#00a8e1\"/><path d=\"M467.7 93c.6-2 1.2-3.9 1.8-5.9 4.6-15.5 9.2-30.9 13.8-46.4l.6-1.8c.5-1.8 2.2-2.9 4-2.9h15.2c3.8 0 4.6 1.1 3.3 4.7l-6 15.9c-6.7 17.4-13.4 34.9-20.1 52.3-.2.6-.5 1.2-.7 1.8-.7 2.1-2.8 3.5-5 3.3-4.4-.1-8.8-.1-13.2 0-3.1.1-4.9-1.3-6-4.1-2.5-6.6-5.1-13.3-7.6-19.9-6-15.7-12.1-31.4-18.1-47.2-.6-1.2-1-2.6-1.3-3.9-.3-2 .4-3 2.4-3 5.7-.1 11.4 0 17 0 2.4 0 3.5 1.6 4.1 3.7 1.1 3.8 2.2 7.7 3.4 11.5 4.1 13.9 8.1 27.9 12.2 41.8-.1.1 0 .1.2.1z\" fill=\"#232f3e\"/><path d=\"M112.6 47c.7-.2 1.3-.6 1.7-1.2 1.8-1.8 3.7-3.5 5.7-5.1 5.2-4 11.7-6 18.2-5.5 2.6.1 3.5.9 3.7 3.4.2 3.4.1 6.9.1 10.3.1 1.4 0 2.7-.2 4.1-.4 1.8-1.1 2.5-2.9 2.7-1.4.1-2.7 0-4.1-.1-6.7-.6-13.2.7-19.5 2.8-1.4.5-1.4 1.5-1.4 2.6v48c0 .9 0 1.7-.1 2.6-.1 1.3-1.1 2.3-2.4 2.3-.7.1-1.5.1-2.2.1h-13c-.7 0-1.5 0-2.2-.1-1.3-.1-2.3-1.2-2.4-2.5-.1-.8-.1-1.6-.1-2.4V41c0-4.6.5-5.1 5.1-5.1h9.6c2.6 0 3.8.9 4.5 3.4s1.3 5 1.9 7.7zM580.4 148.4c6.6.2 13.1.6 19.5 2.3 1.8.5 3.5 1.1 5.2 1.9 2.3.9 3.8 3.1 4.1 5.5.4 2.8.5 5.7.3 8.6-1.3 17.1-6.6 33.6-15.4 48.3-3.2 5.3-7.1 10.1-11.6 14.3-.9.9-2 1.6-3.2 2-1.9.5-3.1-.5-3.2-2.4.1-1 .3-2 .7-3 3.5-9.4 6.9-18.7 9.6-28.4 1.6-5.3 2.7-10.7 3.4-16.2.2-2 .3-4 .1-6-.1-3.4-2.3-6.3-5.6-7.3-3.1-1-6.3-1.6-9.6-1.8-9.2-.4-18.4 0-27.5 1.2l-12.1 1.5c-1.3.1-2.5 0-3.2-1.2s-.4-2.4.3-3.6c.8-1.1 1.8-2.1 3-2.8 7.4-5.3 15.7-8.5 24.5-10.6 6.8-1.4 13.7-2.1 20.7-2.3z\" fill=\"#00a8e1\"/><path d=\"M538.5 75v36c-.2 2-1.1 2.9-3.1 3-5.4.1-10.7.1-16.1 0-2 0-2.9-1-3.1-2.9-.1-.6-.1-1.3-.1-1.9V40c.1-3.1.9-4 4-4h14.4c3.1 0 4 .9 4 4v35z\" fill=\"#232f3e\"/><path d=\"M151.6 74.8V39.3c.1-2.4 1-3.3 3.4-3.4 5.2-.1 10.4-.1 15.6 0 2.3 0 3 .7 3.2 3 .1.9.1 1.7.1 2.6v66.6c0 1.1-.1 2.2-.2 3.3-.1 1.3-1.1 2.2-2.4 2.3-.6.1-1.1.1-1.7.1h-13.9c-.5 0-.9 0-1.4-.1-1.4-.1-2.6-1.2-2.7-2.6-.1-.8-.1-1.6-.1-2.4.1-11.1.1-22.5.1-33.9zM163.2.1c1.6-.1 3.2.2 4.7.7 5.4 1.8 8.2 6.5 7.7 12.6-.4 5.2-4.3 9.4-9.5 10.2-2.2.4-4.5.4-6.7 0-5.7-1.1-9.9-5.3-9.5-12.5.6-7.1 5.3-11 13.3-11z\" fill=\"#00a8e1\"/><path d=\"M527.4.1c2-.2 4 .2 5.9 1 3.9 1.5 6.6 5.1 6.8 9.3.8 9.1-5.3 13.7-13.4 13.5-1.1 0-2.2-.2-3.3-.4-6.2-1.5-9.4-6.3-8.8-13.2.5-5.5 4.8-9.6 10.7-10.1.7-.1 1.4-.2 2.1-.1z\" fill=\"#232f3e\"/><path d=\"M76.7 66.6c-.4-5.2-1.8-10.3-3.9-15-4.1-8.6-10.4-14.9-20-17.1-11-2.4-20.9 0-29.9 6.7-.6.6-1.3 1.1-2.1 1.5-.2-.1-.4-.2-.4-.3-.3-1-.5-2-.8-3-.8-2.5-1.8-3.4-4.5-3.4-3 0-6.1.1-9.1 0-2.3-.1-4.4.2-6 2 0 35 0 70.1.1 105 1.3 2.1 3.3 2.5 5.6 2.4 3.6-.1 7.2 0 10.8 0 6.3 0 6.3 0 6.3-6.2v-28.5c0-.7-.3-1.5.4-2.1 5 3.9 11.1 6.3 17.4 6.9 8.8.9 16.8-1.3 23.5-7.3 4.9-4.5 8.5-10.3 10.4-16.7 2.7-8.2 2.9-16.5 2.2-24.9zM52.8 87.3c-.7 3.1-2.3 5.9-4.6 8-2.6 2.2-5.8 3.5-9.2 3.5-5.1.3-10.1-.8-14.6-3.2-1.1-.5-1.8-1.6-1.7-2.8V74.7c0-6 .1-12 0-18-.1-1.4.7-2.6 2-3.1 5.5-2.6 11.2-3.8 17.2-2.6 4.2.6 7.8 3.3 9.5 7.2 1.5 3.2 2.4 6.7 2.6 10.2.6 6.4.6 12.8-1.2 18.9z\" fill=\"#00a8e1\"/><path d=\"M800.1 82.2s0-.1 0 0c0-.1 0-.1 0 0zM800.2 68.8v.4c-.4-.4-.6-1-.4-1.5v-.8s0-.1.1-.1h-.1v-1h.2c0-.1-.1-.1-.1-.2-.2-1.9-.6-3.8-1.1-5.6-3.7-13.2-12-21.9-25.5-25.3-6.3-1.5-12.7-1.7-19.1-.7-13.5 2-23.2 9.2-27.9 22-4.6 12.2-4.5 25.6.1 37.8 4 11.1 12 18.1 23.5 21 6.1 1.5 12.5 1.9 18.8 1 21-2.5 29.7-18.4 31.1-32.2h-.1v-1.4c-.1-.6-.2-1.1.4-1.5v.2c0-.1.1-.3.2-.4V69c0-.1-.1-.1-.1-.2zm-24 19c-.6 2.1-1.5 4-2.8 5.8-2.2 3.1-5.7 5.1-9.5 5.4-1.9.2-3.8.2-5.7-.2-4.2-.8-7.7-3.6-9.4-7.5-1.5-3.1-2.4-6.5-2.7-9.9-.5-5.9-.6-11.8.8-17.6.5-2.3 1.5-4.6 2.7-6.6 2.2-3.6 6-5.9 10.2-6.2 1.9-.2 3.8-.2 5.7.2 4 .8 7.3 3.4 9.1 7.1 1.7 3.5 2.7 7.4 2.9 11.3.1 1.8.2 3.6.1 5.4.3 4.4-.2 8.7-1.4 12.8zM624.9.8H611c-3.8 0-4.5.7-4.5 4.5v32.4c0 .7.3 1.4-.2 2.1-.9-.1-1.4-.7-2.1-1.1-10.4-6.1-21.3-7.2-32.3-2.1-7.7 3.6-12.5 10.1-15.6 17.8-3 7.4-3.7 15.2-3.5 23.1 0 7.4 1.7 14.7 5 21.3 3.8 7.3 9.3 12.9 17.3 15.3 10.9 3.4 21.1 1.7 30.4-5.2.7-.4 1.1-1.1 2-1.3.5 1.1.9 2.3 1.1 3.5.4 1.6 1.8 2.7 3.5 2.7h2.4c3.6 0 7.1.1 10.6 0 2.8 0 3.6-.9 3.7-3.8V4.6c-.1-3.1-.9-3.8-3.9-3.8zm-18.3 73.6v18.2c.2 1.2-.5 2.3-1.6 2.8-4.8 2.7-10.3 3.8-15.7 3-4.6-.5-8.6-3.3-10.7-7.4-1.6-3.2-2.5-6.6-2.8-10.1-.8-6.3-.3-12.7 1.2-18.8.5-1.7 1.1-3.3 2-4.9 2.1-3.9 6.1-6.4 10.5-6.7 5.3-.5 10.6.5 15.4 2.7 1.2.4 1.9 1.6 1.8 2.9-.2 6.2-.1 12.2-.1 18.3z\" fill=\"#232f3e\"/><path d=\"M348 81.3c7.5 1.4 15.2 1.5 22.7.3 4.4-.6 8.6-1.9 12.5-4 4.5-2.6 7.8-6.2 9.2-11.2 3.5-12.6-1.9-25.3-15-30-6.4-2.1-13.2-2.8-19.9-1.9-15.8 1.8-26.1 10.5-30.8 25.6-3.3 10.3-2.9 20.8-.2 31.2 3.5 13.3 12.3 21.2 25.6 24 7.6 1.7 15.3 1.4 22.9.2 4-.7 8-1.7 11.8-3.2 2.3-.9 3.5-2.3 3.4-4.9-.1-2.4 0-4.9 0-7.4 0-3-1.2-3.9-4.1-3.2s-5.7 1.3-8.6 1.9c-6.2 1.3-12.6 1.3-18.8.2-8.5-1.7-14-9-13.5-18 .9.1 1.9.2 2.8.4zM345.5 66c.3-2.4 1-4.7 1.9-6.9 3-7.3 9.3-9.8 15.7-9.4 1.8.1 3.6.5 5.3 1.2 2.6 1.1 4.3 3.5 4.6 6.3.3 1.7.2 3.5-.3 5.2-1.2 3.6-4.1 5.1-7.6 5.8-2.1.5-4.3.7-6.5.5-3.9 0-7.9-.3-11.8-.9-1.5-.2-1.5-.2-1.3-1.8z\" fill=\"#00a8e1\"/><path d=\"M685.3 82.3c5.8-.4 11.6-1.5 16.8-4.3 5.3-2.6 9-7.5 10.1-13.3.7-3.6.7-7.4-.1-11-2.1-9-7.8-14.6-16.4-17.5-4.8-1.5-9.9-2.1-14.9-1.9-16.8.4-29.6 8.9-34.8 25.7-3.5 11.1-3 22.4.4 33.5 3.5 11.4 11.5 18.3 22.9 21.4 4.9 1.2 10 1.7 15 1.5 7.3-.1 14.6-1.5 21.5-4.1 2.9-1.1 3.6-2.1 3.6-5.2v-7.2c-.1-2.9-1.3-3.9-4.2-3.2-2.2.6-4.3 1.1-6.5 1.6-6.7 1.6-13.7 1.9-20.5.7-6.8-1.3-11.4-5.2-13.2-12-.5-2-.9-4-1.1-6.1.5 0 1 0 1.4.2 6.6 1.2 13.3 1.7 20 1.2zm-20.9-16c.7-3.9 1.6-7.7 4-10.9 3.7-4.9 8.8-6.3 14.6-5.7.5 0 .9.2 1.4.2 7 1.1 8.7 6.7 7.4 12.1-1 4-4.3 5.5-8 6.2-2 .4-4.1.6-6.2.5-4.1-.1-8.1-.4-12.1-1-.9-.1-1.3-.5-1.1-1.4z\" fill=\"#232f3e\"/></svg></span></div></div>";
        else 
            detailHtml += "<div class=\"column\" style=\"height: auto; width: 20rem;\"><h2><strong>Platform</strong></h2><span class=\"span_platform\"><svg xmlns=\"http://www.w3.org/2000/svg\"  width=\"102.5\" height=\"62.8\" viewBox=\"-19.11 -19.11 1101.22 675.22\"><path d=\"M394.906 0c-20.874 0-35.75 10.39-35.75 35.938v450.156c0 10.019 6.636 14.281 15.031 14.281h52.407c20.955 0 35.781-10.437 35.781-36V14.281C462.375 4.213 455.69 0 447.344 0h-52.438zM121.75 153.469C41.911 153.469 0 199.723 0 258.688c0 52.406 32.262 84.323 96.688 98.687l102.843 23c20.907 4.706 27.406 10.592 27.406 21.25 0 8.182-6.501 14.719-25.375 14.719H42.095C16.53 416.344 6.25 431.159 6.25 452v33.313c0 8.346 4.137 15.062 14.156 15.062h174.938c94.481 0 132.625-46.328 132.625-101.063 0-56.619-32.72-88.512-96.719-102.843l-102.875-23.063C107.468 268.734 101 262.847 101 252.156c0-8.149 6.485-14.656 25.375-14.656h153.031c25.498 0 35.813-14.86 35.813-35.75v-33.219c0-8.362-4.154-15.062-14.157-15.062H121.75zm514 0c-15.758 0-24.15 4.213-33.563 14.281l-133.5 143.344 119.75 174.5c6.92 10.068 12.695 14.781 23.157 14.781h71.343c10.675 0 16.875-4.264 16.875-13.25 0-5.51-2.676-9.09-6.218-14.156L588.437 317.313l79.844-86 157.625 233.062-69.687 147.313c-1.919 4.328-4.157 8.506-4.157 12.687 0 8.51 6.24 12.625 14.75 12.625h63.22c12.888 0 18.446-4.45 23.218-14.813l205.563-443.312c2.033-4.362 4.187-8.488 4.187-12.719 0-8.494-6.176-12.687-14.719-12.687h-63.719c-12.757 0-18.547 4.496-23.187 14.875l-89.906 199.594-125.407-197.532c-7.74-12.248-15.905-16.937-34.5-16.937H635.75z\" fill=\"#1e90ff\"/></svg></span></div></div>";
    
        detailHtml += "<div class=\"row\"><h2 class=\"remark_title\">Remark</h2><span class=\"remark\">"+this.remark+"</span></div></div>";
        return detailHtml;
    } 
    
            
    public String getCardHtml(boolean edit) {
        getPoster();
        html = "<div class=\"card-container\" data-series-id=\""+this.seriesId+"\"><div class=\"cover\"><img src=\""+this.posterPath+"\" class=\"film_pic\"/><span class=\"film_name\">"+this.title+"</span></div>";
        html += "<div class=\"back\"><div class=\"back-info\">";
        html += "<label class=\"text_titel\">Titel: <span class=\"span_titel\">"+this.title+"</span></label>";
        html += "<label class=\"text_season\">Season: <span class=\"span_season\">"+this.season+"</span></label>";
        html += "<label class=\"text_genre\">Genre: <span class=\"span_genre\">"+this.genre+" </span></label>";
        if(this.streamingprovider == Streamingprovider.Netflix)
            html += "<label class=\"text_platform\">Platform: <span class=\"span_platform\" style=\"color:red;font-family:'verdana';\">Netflix</span></label>";
        else if(this.streamingprovider == Streamingprovider.AmazonPrime)
            html += "<label class=\"text_platform\">Platform: <span class=\"span_platform\" style=\"color:#232f3e;font-family:'verdana';\">Amazon Prime</span></label>";
        else 
            html += "<label class=\"text_platform\">Platform: <span class=\"span_platform\" style=\"color:#1e90ff;font-family:'verdana';\">Skye </span></label>";
        
        if(this.score == Score.bad) 
            html += "<label class=\"text_rating\">Rating: <span class=\"span_rating_bad\">bad <i class=\"fas fa-star-half\"></i></span></label>";
        else if(this.score == Score.good) 
            html += "<label class=\"text_rating\">Rating: <span class=\"span_rating_good\">good <i class=\"fas fa-star\"></i><i class=\"fas fa-star-half\"></i></span></label>";
        else if(this.score == Score.mediocre)
            html += "<label class=\"text_rating\">Rating: <span class=\"span_rating_mediocre\">mediocre <i class=\"fas fa-star\"></i></span></label>";
        else
            html += "<label class=\"text_rating\">Rating: <span class=\"span_rating_veryGood\">very good <i class=\"fas fa-star\"></i><i class=\"fas fa-star\"></i></span></label>";
        
        html += "<label class=\"text_remark\">Remark: <span class=\"span_remark\">"+this.remark+"</span></label>";
        html += "<label class='text_username' style=\"font-size: 0.8rem;\">EditBy: <span class='span_username'>"+this.editBy+"</span></label>";
        if(edit == true)
            html += "</div><div class=\"icon-top\"><a class=\"poster_icon\" href=\""+this.posterPath+"\"><i class=\"far fa-images\"></i></a><a class=\"detail_icon\" href=\"detailCard.xhtml?editBy="+this.editBy+"&title="+this.title+"&season="+this.season+"&genre="+this.genre.name()+"&streamingprovider="+this.streamingprovider.name()+"&rating="+this.score.name()+"&remark="+this.remark+"&seriesId="+this.seriesId+"\"><i class=\"fas fa-info\"></i></a><a class=\"edit_icon\" href=\"editCard.xhtml?editBy="+this.editBy+"&title="+this.title+"&season="+this.season+"&genre="+this.genre.name()+"&streamingprovider="+this.streamingprovider.name()+"&rating="+this.score.name()+"&remark="+this.remark+"&seriesId="+this.seriesId+"\"><i class=\"fas fa-edit\"></i></a></div>";
        else 
            html += "</div><div class=\"icon-top\"><a class=\"poster_icon\" href=\""+this.posterPath+"\"><i class=\"far fa-images\"></i></a><a class=\"detail_icon\" href=\"detailCard.xhtml?editBy="+this.editBy+"&title="+this.title+"&season="+this.season+"&genre="+this.genre.name()+"&streamingprovider="+this.streamingprovider.name()+"&rating="+this.score.name()+"&remark="+this.remark+"&seriesId="+this.seriesId+"\"><i class=\"fas fa-info\"></i></a></div>";
        html += "<div class=\"icon-bottom\" style=\"display:none;\"><i id=\"save_icon\" class=\"fas fa-save\"></i></div></div></div>";
        return html;
    }
    
    public void makeCardFromURL(UserBean user) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                System.out.println(paramMap.toString());
        this.seriesId = Integer.parseInt(paramMap.get("seriesId"));
        this.title = paramMap.get("title");

        this.editBy = user.getUsername();
        System.out.println(this.editBy);
        this.remark = paramMap.get("remark");
        this.season = Integer.parseInt(paramMap.get("season"));
        String genre = paramMap.get("genre");
        
        if(genre.equals("Thriller")) {
            this.genre = Genre.Thriller;
        } else if(genre.equals("ScienceFiction")) {
            this.genre = Genre.ScienceFiction;
        } else if(genre.equals("Drama")) {
            this.genre = Genre.Drama;
        } else if(genre.equals("Action")) {
            this.genre = Genre.Action;
        } else if(genre.equals("Comedy")) {
            this.genre = Genre.Comedy;
        } else {
            this.genre = Genre.Documentary;
        }
        
        String rating = paramMap.get("rating");
        
        if(rating.equals("very_good")) {
            this.score = Score.very_good;
        } else if (rating.equals("good")) {
            this.score = Score.good;
        } else if (rating.equals("bad")) {
            this.score = Score.bad;
        } else {
            this.score = Score.mediocre;
        }
        
        String streamingprovider = paramMap.get("streamingprovider");
        
        if(streamingprovider.equals("Netflix")) {
            this.streamingprovider = Streamingprovider.Netflix;
        } else if (streamingprovider.equals("Skye")) {
            this.streamingprovider = Streamingprovider.Skye;
        } else {
            this.streamingprovider = Streamingprovider.AmazonPrime;
        }
    }
    

}
