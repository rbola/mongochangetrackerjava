package com.mongodb.poc.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.client.model.Filters;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;


public class PetEntity extends MongoTrackingRootEntity {
    protected String _animal;
    protected PetBed _bed = new PetBed();
    protected ObjectId _id;

    @JsonIgnore
    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        _id = id;
    }


    public PetBed getBed() {
        return _bed;
    }

    public void setBed(PetBed bed) {
        _bed = bed;
        set("bed", _bed);
    }

    @BsonIgnore
    public String getUpdates() {
        Bson doc = this.getUpdate​Document();
        return doc == null ? null : doc.toString();
    }

    public String getAnimal() {
        return _animal;
    }

    public void setAnimal(String vAnimal) {
        if (_animal != vAnimal) {
            _animal = vAnimal;
            set("animal", _animal);
        }
    }


    public Bson Filter() {
        return Filters.eq("_id", getId());
    }

}


