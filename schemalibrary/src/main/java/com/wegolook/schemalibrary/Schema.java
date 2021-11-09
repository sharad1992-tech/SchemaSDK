package com.wegolook.schemalibrary;

import com.google.gson.annotations.SerializedName;

public class Schema {

    @SerializedName("data")
    private Data data;

    public void setData(Data data){
        this.data = data;
    }

    public Data getData(){
        return data;
    }
}