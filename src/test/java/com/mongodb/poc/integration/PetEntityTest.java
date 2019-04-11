package com.mongodb.poc.integration;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.poc.model.FamilyManager;
import com.mongodb.poc.model.PetBed;
import com.mongodb.poc.model.PetEntity;

import org.junit.Test;
import org.bson.types.ObjectId;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;



public class PetEntityTest {

    private FamilyManager _manager;


    private void init() {
        _manager = new FamilyManager();
        prePopulateDB();

    }

    private void prePopulateDB() {
        List<PetEntity> dbPets = _manager.getPets();
        if (dbPets.size()>0) {
            List<PetEntity> pets = new ArrayList<>();

            PetEntity pet1 = new PetEntity();
            PetEntity pet2 = new PetEntity();

            pet1.setAnimal("Dog");
            PetBed bed1 = new PetBed();
            bed1.setDescription("Pillow");
            pet1.setBed(bed1);


            pet2.setAnimal("Cat");
            PetBed bed2 = new PetBed();
            bed2.setDescription("Pet Cave");
            pet2.setBed(bed2);

            pets.add(pet1);
            pets.add(pet2);

            _manager.savePets(pets);
        }
    }

    @Test
    public void testUpdatePet1() {
        init();
        List<PetEntity> pets = _manager.getPets();

        ObjectId dogId=pets.get(0).getId();
        PetEntity pet1 = _manager.getPet(dogId);
        PetBed bed1 = pet1.getBed();
        bed1.setDescription("Big " + bed1.getDescription());
        pet1.setBed(bed1);

        _manager.updatePet(pet1);
        PetEntity pet1Updated = _manager.getPet(dogId);

        Assert.assertEquals(pet1.getBed().getDescription(), pet1Updated.getBed().getDescription());
    }


}
