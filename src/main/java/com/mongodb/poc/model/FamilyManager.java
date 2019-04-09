package com.mongodb.poc.model;

import com.mongodb.client.FindIterable;
import com.mongodb.poc.repository.Repository;
import org.bson.types.ObjectId;

import java.util.List;

public class FamilyManager {

    private  Repository _repo;


    public FamilyManager() {
        _repo = new Repository();
    }

    public void clearPet(){
        _repo.clearPet();
    }

    public void savePets(List<PetEntity> pets){
        _repo.savePets(pets);
    }

    public PetEntity getPet(ObjectId id){
        PetEntity pet=  _repo.getPet(id);
        pet.startTracking();
        pet.getBed().startTracking();
        return  pet;
    }

    public FindIterable<PetEntity> getPets(){
       return _repo.getPets();
    }

    public void updatePet(PetEntity pet){
         _repo.updatePet(pet);
    }

}
