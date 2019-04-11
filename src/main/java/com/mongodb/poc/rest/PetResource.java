package com.mongodb.poc.rest;

import com.mongodb.poc.model.FamilyManager;
import com.mongodb.poc.model.PetEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;


@Path("pets")
public class PetResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPets() {
        FamilyManager _manager = new FamilyManager();

        List<PetEntity> pets = _manager.getPets();

        GenericEntity<List<PetEntity>> response = new GenericEntity<List<PetEntity>>(pets) {
        };

        return Response.status(200).entity(response).build();
    }
}