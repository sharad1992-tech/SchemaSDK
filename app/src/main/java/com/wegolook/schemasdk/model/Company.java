package com.wegolook.schemasdk.model;

public class Company{
    private String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return
                "Company{" +
                        "name = '" + name + '\'' +
                        "}";
    }
}