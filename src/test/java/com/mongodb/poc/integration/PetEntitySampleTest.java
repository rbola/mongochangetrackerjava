package com.mongodb.poc.integration;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.poc.model.FamilyManager;
import com.mongodb.poc.model.PetBed;
import com.mongodb.poc.model.PetEntitySample;
import org.bson.types.ObjectId;
import org.junit.*;


import java.util.ArrayList;
import java.util.List;


public class PetEntitySampleTest {

    private FamilyManager _manager;


    private void init() {
        _manager = new FamilyManager();
        prePopulateDB();

    }

    private void prePopulateDB() {

        FindIterable<PetEntitySample> dbPets = _manager.getPets();
        if (dbPets.first() == null) {
            List<PetEntitySample> pets = new ArrayList<>();

            PetEntitySample pet1 = new PetEntitySample();
            PetEntitySample pet2 = new PetEntitySample();

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

        ObjectId dogId = null;
        FindIterable<PetEntitySample> pets = _manager.getPets();
        MongoCursor<PetEntitySample> pCursor = pets.iterator();

        try {
            while (pCursor.hasNext()) {
                PetEntitySample pet = pCursor.next();
                if (pet.getAnimal().equals("Dog")) {
                    dogId = pet.getId();
                    break;
                }
            }
        } finally {
            pCursor.close();
        }
        PetEntitySample pet1 = _manager.getPet(dogId);
        PetBed bed1 = pet1.getBed();
        bed1.setDescription("Big " + bed1.getDescription());
        pet1.setBed(bed1);

        _manager.updatePet(pet1);


        PetEntitySample pet1Updated = _manager.getPet(dogId);

        Assert.assertEquals(pet1.getBed().getDescription(), pet1Updated.getBed().getDescription());
    }
}
