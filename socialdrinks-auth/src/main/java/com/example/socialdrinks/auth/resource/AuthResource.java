/*
 * JBoss, Home of Professional Open Source
 * Copyright 2018, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.socialdrinks.auth.resource;

import com.example.socialdrinks.auth.service.*;
import com.nimbusds.jwt.*;
import jakarta.annotation.security.*;
import jakarta.inject.*;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.*;

import java.security.*;
import java.text.*;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);

    @Inject
    private JwtManager jwtManager;

    @Inject
    private UserService service;

    @Inject
    private SecurityContext securityContext;

    //Security constraints are defined in web.xml

    @GET
    @Path("/customer")
    @RolesAllowed("customer")
    public String getCustomerJSON() {
        return "{\"path\":\"customer\",\"result\":" + sayHello() + "}";
    }

    @GET
    @Path("/user")
    @RolesAllowed("user")
    public String getUserJSON() {
        return "{\"path\":\"user\",\"result\":" + sayHello() + "}";
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    public String getAdminJSON() {
        return "{\"path\":\"admin\",\"result\":" + sayHello() + "}";
    }

    @GET
    @Path("/public")
    public String getPublicJSON() {
        return "{\"path\":\"public\",\"result\":" + sayHello() + "}";
    }

    @GET
    @PermitAll
    @Path("/claims")
    public Response demonstrateClaims(@HeaderParam("Authorization") String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                JWT j = JWTParser.parse(auth.substring(7));
                return Response.ok(j.getJWTClaimsSet().getClaims())
                        .build(); //Note: nimbusds converts token expiration time to milliseconds
            } catch (ParseException e) {
                LOGGER.warn(e.toString());
                return Response.status(400).build();
            }
        }
        return Response.noContent().build(); //no jwt means no claims to extract
    }

    @POST
    @Path("/login")
    public Response postJWT(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        LOGGER.info("Authenticating {}", username);
        try {
            User user = service.authenticate(username, password);
            if (user != null) {
                if (user.getName() != null) {
                    LOGGER.info("Generating JWT for org.jboss.user {}", user.getName());
                }
                String token = jwtManager.createJwt(user.getName(), user.getRoles());
                return Response.ok(
                        Json.createObjectBuilder()
                                .add("token", token)
                                .build()
                ).build();
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private String sayHello() {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String principalName = userPrincipal == null ? "anonymous" : userPrincipal.getName();
        return "\"Hello " + principalName + "!\"";
    }

}
