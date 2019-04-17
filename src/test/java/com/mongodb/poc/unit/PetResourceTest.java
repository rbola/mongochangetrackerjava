package com.mongodb.poc.unit;

import com.mongodb.poc.model.FamilyManager;
import com.mongodb.poc.model.PetBed;
import com.mongodb.poc.model.PetEntity;
import com.mongodb.poc.rest.PetResource;


import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PetResourceTest {

    @Mock
    FamilyManager mockedManager;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private List<PetEntity> getPets() {
        List<PetEntity> pets = new ArrayList<>();

        PetEntity pet1 = new PetEntity();
        PetEntity pet2 = new PetEntity();

        pet1.setAnimal("DogTest");
        PetBed bed1 = new PetBed();
        bed1.setDescription("Pillow Test");
        pet1.setBed(bed1);


        pet2.setAnimal("Cat Test");
        PetBed bed2 = new PetBed();
        bed2.setDescription("Pet Cave Test");
        pet2.setBed(bed2);

        pets.add(pet1);
        pets.add(pet2);

        return pets;
    }


    @Test
    public void testGetPets() {
        when(mockedManager.getPets()).thenReturn(this.getPets());

        List<PetEntity> pets = mockedManager.getPets();
        Assert.assertEquals(pets.get(0).getIsTracking(), false);
    }
}
