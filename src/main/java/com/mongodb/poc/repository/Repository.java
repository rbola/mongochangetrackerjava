package com.mongodb.poc.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientURI;
import com.mongodb.poc.model.PetEntity;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.ResourceBundle;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Repository {

    private static final String _collectionPetName = "pet";
    private static final String _databaseName = "family";
    private static MongoClient _client;
    private static MongoDatabase _database;
    private static MongoCollection<PetEntity> _collectionpet;
    private String _connectionString;

    public Repository() {
        _connectionString = getConnectionString();
        _client = new MongoClient(new MongoClientURI(_connectionString));
        _database = _client.getDatabase(_databaseName);

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        _collectionpet = _database.getCollection(_collectionPetName, PetEntity.class).withCodecRegistry(pojoCodecRegistry);


    }

    public void clearPet() {
        Bson filter = new BsonDocument();
        _collectionpet.deleteMany(filter);
    }

    public void savePets(List<PetEntity> entity) {


        _collectionpet.insertMany(entity);
    }

    public PetEntity getPet(ObjectId id) {

        Bson filter = and(eq("_id", id));

        return _collectionpet.find(filter).first();
    }

    public FindIterable<PetEntity> getPets() {

        return _collectionpet.find();
    }

    public void updatePet(PetEntity pet) {
        Bson doc = pet.getUpdate​Document();

        _collectionpet.updateOne(pet.Filter(), doc);
    }


    private String getConnectionString() {

        _connectionString = System.getenv("conn");

        if (_connectionString == null) {

            ResourceBundle rb = ResourceBundle.getBundle("config");
            _connectionString = "mongodb+srv://" + rb.getString("mongodbuser") + ":" + rb.getString("mongodbpassword") + "@" + rb.getString("cluster") + "/test?retryWrites=true";
        }

        return _connectionString;

    }

}
