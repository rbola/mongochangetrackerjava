package com.mongodb.poc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "updateDocument" })
public class PetBed extends MongoTrackingNestedEntity {
    protected String _desc;

    public String getDescription() {
        return _desc;
    }

    public void setDescription(String vDesc) {
        if (_desc != vDesc) {
            _desc = vDesc;
            set("description",_desc);
        }
    }

}