package com.mongodb.poc.rest;

import com.mongodb.client.FindIterable;
import com.mongodb.poc.model.FamilyManager;
import com.mongodb.poc.model.PetEntity;

import javax.ws.rs.*;
import java.util.*;


@Path("pet")
public class PetResource {
    @GET
    @Produces("application/json")
    public List<PetEntity> getPets() {
        FamilyManager _manager = new FamilyManager();
        FindIterable<PetEntity> dbPets = _manager.getPets();
        return dbPets.into(new ArrayList<>());
    }
}