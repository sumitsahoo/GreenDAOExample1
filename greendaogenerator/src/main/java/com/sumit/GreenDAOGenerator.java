package com.sumit;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDAOGenerator {
    public static void main(String args[]){
        Schema schema = new Schema(1, "com.sumit.greendaoexample.model");

        Entity person = schema.addEntity("Person");
        person.addIdProperty();
        person.addStringProperty("name");
        person.addStringProperty("comment");

        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
