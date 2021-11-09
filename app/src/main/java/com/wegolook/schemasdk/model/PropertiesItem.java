package com.wegolook.schemasdk.model;

public class PropertiesItem{
    private String name;
    private String label;
    private String value;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return
                "PropertiesItem{" +
                        "name = '" + name + '\'' +
                        ",label = '" + label + '\'' +
                        ",value = '" + value + '\'' +
                        "}";
    }
}
