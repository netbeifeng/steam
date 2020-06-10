/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.jsf.beans;

import org.json.JSONArray;



/**
 *
 * @author Chang
 */

public class CardDetail {
    private String air_date;
    private int episode_count;
    private String overview;
    private String alias_name;
    private String backdrop_path;
    private JSONArray seasons;
    
    public JSONArray getSeasons() {
        return seasons;
    }
    
    public void setSeasons(JSONArray seasons) {
        this.seasons = seasons;
    }
    
    public String getBackdropPath() {
        return backdrop_path;
    }
    
    public void setBackdropPath(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
    
    public String getAirDate() {
        return air_date;
    }
    
    public void setAirDate(String air_date) {
        this.air_date = air_date;
    }
    
    public int getEpisodeCount() {
        return episode_count;
    }
    
    public void setEpisodeCount(int episode_count) {
        this.episode_count = episode_count;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    public String getAliasName() {
        return alias_name;
    }
    
    public void setAliasName(String alias_name) {
        this.alias_name = alias_name;
    }
}
