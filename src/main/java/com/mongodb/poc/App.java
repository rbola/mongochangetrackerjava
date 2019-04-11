package com.mongodb.poc;


import com.mongodb.poc.rest.PetResource;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


import org.glassfish.grizzly.http.server.*;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class App {

    private static URI getBaseURI() {
        try {
            return new URI("http://localhost:8080");
        } catch (URISyntaxException e) {
            return null;
        }

    }

    public static final URI BASE_URI = getBaseURI();

    public static void main(String[] args) throws IOException {

        final ResourceConfig resourceConfig = new ResourceConfig(PetResource.class);

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        server.start();


    }
}