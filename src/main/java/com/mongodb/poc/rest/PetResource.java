package com.mongodb.poc.rest;

import com.mongodb.poc.model.FamilyManager;
import com.mongodb.poc.model.PetBed;
import com.mongodb.poc.model.PetEntity;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;


@Path("pets")
public class PetResource {


    public FamilyManager getManager(){
        return new FamilyManager();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPets() {
        FamilyManager _manager = getManager();

        List<PetEntity> pets = _manager.getPets();

        GenericEntity<List<PetEntity>> response = new GenericEntity<List<PetEntity>>(pets) {
        };

        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/{id}/{bed}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyPetAnimal(@PathParam("id") ObjectId id, @PathParam("bed") String bed) {
        FamilyManager _manager = getManager();

        PetEntity pet = _manager.getPet(id);
        PetBed bed1 = pet.getBed();
        bed1.setDescription(bed);
        pet.setBed(bed1);

        _manager.updatePet(pet);

        GenericEntity<PetEntity> response = new GenericEntity<PetEntity>(pet) {
        };
        return Response.status(200).entity(response).build();
    }
}
