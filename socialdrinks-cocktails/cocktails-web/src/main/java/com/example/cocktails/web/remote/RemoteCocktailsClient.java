package com.example.cocktails.web.remote;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.rest.client.inject.*;

import java.util.*;

@Path("/api")
@RegisterRestClient(configKey = "cocktails-api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RemoteCocktailsClient {

    @GET
    @Path("/cocktails")
    List<RemoteCocktailSummary> getAllCocktails();

    @GET
    @Path("/cocktails/{id}")
    RemoteCocktailDetails getCocktail(@PathParam("id") long id);
}
