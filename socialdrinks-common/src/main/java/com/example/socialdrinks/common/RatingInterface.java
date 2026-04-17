package com.example.socialdrinks.common;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RatingInterface {

    @GET
    @Path("/rate/{id}")
    Integer getRate(@PathParam("id") Long id);

}
