package com.mongodb.poc.model;


import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PetEntity extends MongoTrackingRootEntity {
    protected String _animal;
    protected PetBed _bed = new PetBed();
    protected ObjectId _id;

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
        set("bed",_bed);
    }


    public String getAnimal() {
        return _animal;
    }

    public void setAnimal(String vAnimal) {
        if (_animal != vAnimal) {
            _animal = vAnimal;
            set("animal",_animal);
        }
    }


    public Bson Filter() {
        return Filters.eq("_id", getId());
    }

}


