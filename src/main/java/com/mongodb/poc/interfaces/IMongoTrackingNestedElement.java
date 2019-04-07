package com.mongodb.poc.interfaces;


import org.bson.conversions.Bson;

public interface IMongoTrackingNestedElement {

    Bson getUpdate​Document(String path);
}
