package com.wegolook.schemalibrary;

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