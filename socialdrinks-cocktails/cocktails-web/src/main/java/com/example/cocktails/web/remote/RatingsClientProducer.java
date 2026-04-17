package com.example.cocktails.web.remote;

import com.example.socialdrinks.common.*;
import jakarta.enterprise.context.*;
import jakarta.enterprise.inject.*;
import org.eclipse.microprofile.rest.client.*;

import java.net.*;

@ApplicationScoped
public class RatingsClientProducer {

    @Produces
    @ApplicationScoped
    @RatingsClient
    public RatingInterface createClient() {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create("http://localhost:8085/api"))
                .build(RatingInterface.class);
    }

    public void closeClient(@Disposes @RatingsClient RatingInterface client) throws Exception {
        if (client instanceof AutoCloseable closeable) {
            closeable.close();
        }
    }
}
