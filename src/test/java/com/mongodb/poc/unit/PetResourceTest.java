package com.mongodb.poc.unit;

import com.mongodb.poc.model.PetBed;
import com.mongodb.poc.model.PetEntity;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

public class PetResourceTest extends JerseyTest {

    @Path("pet")
    public static class PetResource {
        @GET
        @Produces("application/json")
        public List<PetEntity> getPets() {
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
            return pets;
        }
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    public Application configure() {
        return new ResourceConfig(PetResource.class);
    }

    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext.forServlet(new ServletContainer(
                new ResourceConfig(PetResource.class))).build();
    }

    @Test
    public void testGetPetREST(){
        Response output = target("pet").request(MediaType.APPLICATION_JSON_TYPE).get();
        System.out.println(output.readEntity(String.class));
        Assert.assertEquals("should return status 200", 200, output.getStatus());
    }
}
