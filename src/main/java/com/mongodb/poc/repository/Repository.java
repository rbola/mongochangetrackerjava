package com.mongodb.poc.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientURI;
import com.mongodb.poc.model.PetEntitySample;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Repository {

    private static final String _collectionPetName = "pet";
    private static final String _databaseName = "family";
   // private static final String _connectionString = "mongodb+srv://entitytracking:LdwCL2a9ZWHl5jTK@prodteam1-kx1de.mongodb.net/test?retryWrites=true";
    private static MongoClient _client;
    private static MongoDatabase _database;
    private static MongoCollection<PetEntitySample> _collectionpet;

    public Repository() {

        String _connectionString = getConnectionString();
        _client = new MongoClient(new MongoClientURI(_connectionString));
        _database = _client.getDatabase(_databaseName);

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        _collectionpet = _database.getCollection(_collectionPetName, PetEntitySample.class).withCodecRegistry(pojoCodecRegistry);


    }

    public void clearPet() {
        Bson filter = new BsonDocument();
        _collectionpet.deleteMany(filter);
    }

    public void savePets(List<PetEntitySample> entity) {


        _collectionpet.insertMany(entity);
    }

    public PetEntitySample getPet(ObjectId id) {

        Bson filter = and(eq("_id", id));

        return _collectionpet.find(filter).first();
    }

    public FindIterable<PetEntitySample> getPets() {

        return _collectionpet.find();
    }

    public void updatePet(PetEntitySample pet) {
        Bson doc = pet.getUpdate​Document();
        _collectionpet.updateOne(pet.Filter(), doc);
    }

    private String getConnectionString(){

        ResourceBundle rb = ResourceBundle.getBundle("config");

        return "mongodb+srv://"+rb.getString("mongodbuser")+":"+rb.getString("mongodbpassword")+"@"+rb.getString("cluster")+"/test?retryWrites=true";


    }

}
