package com.nagarro;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import com.arangodb.mapping.ArangoJack;

public class Main {
    /*public static void main(String[] args) {

        ArangoDB arangoDB = new ArangoDB.Builder()
                .serializer(new ArangoJack())
                .user("root")
                .password("tudor")
                .build();

        String dbName = "phonebookDB";

//        try {
//            arangoDB.createDatabase(dbName);
//            System.out.println("Database created: " + dbName);
//        } catch(ArangoDBException e) {
//            System.err.println("Failed to create database: " + dbName + "; " + e.getMessage());
//        }

        String collectionName = "firstCollection";
//        try {
//            CollectionEntity myArangoCollection=arangoDB.db(dbName).createCollection(collectionName);
//            System.out.println("Collection created: " + myArangoCollection.getName());
//        } catch(ArangoDBException e) {
//            System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
//        }
        BaseDocument myObject = new BaseDocument();
        myObject.setKey("myKey");
        myObject.addAttribute("a", "Foo");
        myObject.addAttribute("b", 42);
        try {
            arangoDB.db(dbName).collection(collectionName).insertDocument(myObject);
            System.out.println("Document created");
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
        }
    }
     */
}
