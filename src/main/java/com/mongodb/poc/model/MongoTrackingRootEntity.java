package com.mongodb.poc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mongodb.client.model.Updates;
import com.mongodb.poc.interfaces.IMongoChangeTracking;
import com.mongodb.poc.interfaces.IMongoTrackingNestedElement;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.conversions.Bson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class MongoTrackingRootEntity implements IMongoChangeTracking {

    private boolean isTracking;
    private Date lastModified;
    final static Logger logger = LogManager.getLogManager().getLogger(MongoTrackingRootEntity.class.getName());

    @JsonIgnore
    public Bson getUpdate​Document() {
        Bson doc = null;
        List<Bson> updates = new ArrayList<>();

        Field[] aClassFields = this.getClass().getDeclaredFields();
        for (Field f : aClassFields) {

            if (!(IMongoTrackingNestedElement.class.isAssignableFrom(f.getType())))
                continue;

            try {
                IMongoTrackingNestedElement cDoc = (IMongoTrackingNestedElement) f.get(this);

                Bson update = cDoc.getUpdate​Document(f.getName().replace("_",""));

                if (update == null)
                    continue;

                updates.add(update);
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, e.getMessage());
                return null;
            }
        }

        if (updates.size() > 0) doc = Updates.combine(updates);

        return doc;

    }


    @BsonIgnore
    public boolean getIsTracking() {
        return this.isTracking;
    }

    public void startTracking() {
        isTracking = true;

        Field[] aClassFields = this.getClass().getDeclaredFields();
        for (Field f : aClassFields) {

            if (!(IMongoTrackingNestedElement.class.isAssignableFrom(f.getType())))
                continue;
            try {
                IMongoChangeTracking cDoc = (IMongoChangeTracking) f.get(this);
                cDoc.startTracking();
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, e.getMessage());
                break;
            }

        }
    }

    public void clearTrackedChanges() {


        Field[] aClassFields = this.getClass().getDeclaredFields();
        for (Field f : aClassFields) {

            if (!(IMongoTrackingNestedElement.class.isAssignableFrom(f.getType())))
                continue;

            try {
                IMongoChangeTracking cDoc = (IMongoChangeTracking) f.get(this);
                cDoc.clearTrackedChanges();
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, e.getMessage());
                break;
            }

        }
    }


    protected <TField> Bson set(String fName,TField field) {
        Bson doc = getUpdate​Document();

        if (!isTracking)
            return doc;

        doc = doc == null ? Updates.set(fName, field) : Updates.combine(doc, Updates.set(fName, field));

        return doc;
    }


    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date modified) {
        this.lastModified = modified;
    }


}
