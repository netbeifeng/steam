package de.hsh.steam.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import de.hsh.steam.application.Steamservices;
import jdk.nashorn.internal.objects.annotations.Getter;

/**
 *
 * @author
 */
@Path("/javaee8")
public class JavaEE8Resource {
    Steamservices steamServices;

    @GET
    @Path("/ping")
    //steam_service/javaee8/ping
    public Response ping() {
        return Response
                .ok("ping")
                .build();
    }

    @POST
    @Path("/post/{para}")
    //@Consumes(MediaType.TEXT_XML)
    public Response postStrMsg(@PathParam("para") String msg) {
        String output = "POST: Jersey say : " + msg;
        return Response.status(200).entity(output).build();
    }
}
