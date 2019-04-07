package com.mongodb.poc.model;

import com.mongodb.client.model.Updates;
import com.mongodb.poc.interfaces.IMongoChangeTracking;
import com.mongodb.poc.interfaces.IMongoTrackingNestedElement;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.conversions.Bson;

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class MongoTrackingNestedEntity implements IMongoChangeTracking, IMongoTrackingNestedElement {

    protected final Map<String, Object> SetOps = new HashMap<>();

    private boolean isTracking;
    private Date lastModified;
    final static Logger logger = LogManager.getLogManager().getLogger(MongoTrackingNestedEntity.class.getName());


    public Bson getUpdate​Document(String path) {
        Bson doc = null;
        List<Bson> updates = new ArrayList<>();

        Field[] aClassFields = this.getClass().getDeclaredFields();
        for (Field f : aClassFields) {

            if (!(IMongoChangeTracking.class.isAssignableFrom(f.getType())))
                continue;
            try {
                IMongoTrackingNestedElement cDoc = (IMongoTrackingNestedElement) f.get(this);

                Bson tDoc = cDoc.getUpdate​Document(path +"."+ f.getName());

                if (tDoc == null)
                    continue;

                updates.add(tDoc);
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, e.getMessage());
                return null;
            }

        }

        for (Map.Entry<String, Object> pair : SetOps.entrySet()) {

            updates.add(Updates.set(path +"."+ pair.getKey(), pair.getValue()));
        }
        if (updates.size() > 0) doc = Updates.combine(updates);

        return doc;

    }


    @BsonIgnore
    public boolean getIsTracking() {
        return isTracking;
    }

    public void startTracking() {
        isTracking = true;

        Field[] aClassFields = this.getClass().getDeclaredFields();
        for (Field f : aClassFields) {

            if (!(IMongoChangeTracking.class.isAssignableFrom(f.getType())))
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
        SetOps.clear();

        Field[] aClassFields = this.getClass().getDeclaredFields();
        for (Field f : aClassFields) {

            if (!(IMongoChangeTracking.class.isAssignableFrom(f.getType())))
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


    protected <TField> void set(String fName,TField field) {
        if (!isTracking)
            return;
        SetOps.put(fName, field);
    }


    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date modified) {
        this.lastModified = modified;
    }

}
