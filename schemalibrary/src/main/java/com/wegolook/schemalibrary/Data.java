package com.wegolook.schemalibrary;

import java.util.ArrayList;

public class Data {

    public ArrayList<SchemaModel> getSchemaManyVersions() {
        return schemaManyVersions;
    }

    public void setSchemaManyVersions(ArrayList<SchemaModel> schemaManyVersions) {
        this.schemaManyVersions = schemaManyVersions;
    }

    private ArrayList<SchemaModel> schemaManyVersions;
}
