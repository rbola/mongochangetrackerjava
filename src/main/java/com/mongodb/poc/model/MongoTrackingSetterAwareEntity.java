package com.mongodb.poc.model;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.conversions.Bson;

import java.util.List;

public abstract class MongoTrackingSetterAwareEntity
{
    private Bson updateDocument;
    private  List<Bson>  updateDocuments;

    @BsonIgnore
    protected Bson getUpdate​Document (){
        return updateDocument;
    }

    @BsonIgnore
    protected void setUpdate​Document (Bson uDoc){
        updateDocument=uDoc;
    }

    @BsonIgnore
    protected  List<Bson> getUpdateDocuments() {
        return updateDocuments;
    }

    @BsonIgnore
    protected void setUpdateDocuments(List<Bson> uDocs) {
        updateDocuments= uDocs;
    }


}