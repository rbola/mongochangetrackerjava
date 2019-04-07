package com.mongodb.poc.model;

public class PetBed extends MongoTrackingNestedEntity {
    private String _desc;

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