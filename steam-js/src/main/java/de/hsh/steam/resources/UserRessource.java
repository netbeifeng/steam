package de.hsh.steam.resources;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

import de.hsh.steam.application.Steamservices;

@Path("/user")
public class UserRessource {
    Steamservices steamServices = new Steamservices();
    
    @POST
    @Path("/login")
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) {
        if (steamServices.login(username, password)) {
            return Response.status(200).entity("{\"status\":\"suc\"}").build();
        } else {
            return Response.status(200).entity("{\"status\":\"fail\"}").build();
        }
    }


    @POST
    @Path("/register")
    public Response addUser(
            @FormParam("username") String username,
            @FormParam("password") String password) {
        if (steamServices.validRegisterData(username, password)) {
            if(steamServices.newUser(username, password)){
                return Response.status(200).entity("{\"status\":\"suc\"}").build();
            } else {
                return Response.status(200).entity("{\"status\":\"existed\"}").build();
            }
        } else {
            return Response.status(200).entity("{\"status\":\"illegal\"}").build();
        }
    }
    
//    @GET
//    @Path("/getUsers")
//    public Response getUsers() {
//        return Response.status(200).entity(steamServices.getAllUser()).build();
//    }
}