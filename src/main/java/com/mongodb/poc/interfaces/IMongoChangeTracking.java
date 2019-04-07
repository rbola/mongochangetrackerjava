package com.mongodb.poc.interfaces;

public interface IMongoChangeTracking {

    boolean getIsTracking();

    void startTracking();

    void clearTrackedChanges();
}
