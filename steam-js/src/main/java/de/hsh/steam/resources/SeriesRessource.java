package de.hsh.steam.resources;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;


import org.json.JSONObject;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

import de.hsh.steam.application.User;
import de.hsh.steam.application.Series;
import de.hsh.steam.application.Rating;
import de.hsh.steam.application.Steamservices;
import de.hsh.steam.application.Genre;
import de.hsh.steam.application.Score;
import de.hsh.steam.application.Streamingprovider;

@Path("/series")
public class SeriesRessource {
    Steamservices steamServices = new Steamservices();

    @GET
    @Path("/getSeries/{username}")
    public Response getSeriesByUsername(@PathParam("username") String username) {
        ArrayList<Series> list = steamServices.getAllSeriesOfUser(username);
        ArrayList<JSONObject> jsonList = new ArrayList();
        for(Series s : list) {
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("username",username);
           jsonObject.put("genre", s.getGenre());
           jsonObject.put("season", s.getNumberOfSeasons());
           jsonObject.put("platform", s.getStreamedBy());
           jsonObject.put("title", s.getTitle());
           Rating r = steamServices.getRating(s.getTitle(), username);
           jsonObject.put("score",r.getScore());
           jsonObject.put("remark",r.getRemark());
           jsonList.add(jsonObject);
        }
        return Response.status(200).entity(jsonList.toString()).build();
    }

    @GET
    @Path("/getSeries")
    public Response getSeries() {
        ArrayList<Series> list = steamServices.getAllSeries();
        ArrayList<JSONObject> jsonList = new ArrayList();
        for(Series s : list) {
           for(User u: s.getSeenBy()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username",u.getUsername());
                jsonObject.put("genre", s.getGenre());
                jsonObject.put("season", s.getNumberOfSeasons());
                jsonObject.put("platform", s.getStreamedBy());
                jsonObject.put("title", s.getTitle());
                Rating r = steamServices.getRating(s.getTitle(), u.getUsername());
                jsonObject.put("score",r.getScore());
                jsonObject.put("remark",r.getRemark());
                jsonList.add(jsonObject);
           }
        }
        return Response.status(200).entity(jsonList.toString()).build();
    }
    
    
    @POST
    @Path("/addOrChangeSeries")
    public Response addOrChangeNewSeries(@FormParam("username") String username,
                                 @FormParam("title") String title,
                                 @FormParam("season") int season,
                                 @FormParam("genre") String g,
                                 @FormParam("platform") String sp,
                                 @FormParam("score") String sc,
                                 @FormParam("remark") String remark) {
        try {
            Genre _g;
            Streamingprovider _sp;
            Score _sc;
            if(g.indexOf("Drama") != -1) {
               _g = Genre.Drama;
            } else if(g.indexOf("Comedy") != -1) {
                _g = Genre.Comedy;
            } else if(g.indexOf("Thriller") != -1) {
                _g = Genre.Thriller;
            } else if(g.indexOf("Documentary") != -1) {
                _g = Genre.Documentary;
            } else if(g.indexOf("Action") != -1) {
                _g = Genre.Action;
            } else {
                _g = Genre.ScienceFiction;
            }
            
            if(sp.indexOf("Netflix") != -1) {
                _sp = Streamingprovider.Netflix;
            } else if(sp.indexOf("Skye") != -1){
                _sp = Streamingprovider.Skye;
            } else {
                _sp = Streamingprovider.AmazonPrime;
            }
            
            if(sc.indexOf("Very") != -1) {
                _sc = Score.very_good;
            } else if(sc.indexOf("Bad") != -1) {
                _sc = Score.bad;
            } else if(sc.indexOf("Mediocre") != -1) {
                _sc = Score.mediocre;
            } else {
                _sc = Score.good;
            }
            if(season <= 0) {
                throw new IllegalArgumentException();
            }               
            steamServices.addOrModifySeries(title, season, _g, _sp, username, _sc, remark);
        } catch (Exception e) {
            return Response.status(200).entity("{\"status\":\"Error\"}").build();
        } finally {
            return Response.status(200).entity("{\"status\":\"Ok\"}").build();
        }
    }
    
//    @DELETE
//    @Path("/deleteSeries/{title}")
//    public Response deleteSeries(@PathParam("title") String title){
//        try{
//          steamServices.deleteSeries(title);
//        } catch (Exception e) {
//            return Response.status(200).entity("Error").build();
//        } finally {
//            return Response.status(200).entity("Ok").build();
//        }
//    }
}