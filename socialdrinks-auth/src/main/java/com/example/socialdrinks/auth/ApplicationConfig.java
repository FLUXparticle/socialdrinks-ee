package com.example.socialdrinks.auth;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.auth.*;

@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    // Leer, dient nur zur Aktivierung von JAX-RS
}
