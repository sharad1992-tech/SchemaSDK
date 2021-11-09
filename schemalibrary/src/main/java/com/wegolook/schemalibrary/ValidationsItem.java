package com.wegolook.schemalibrary;

public class ValidationsItem{
    private String id;
    private Object value;
    private boolean enabled;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setValue(Object value){
        this.value = value;
    }

    public Object getValue(){
        return value;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public boolean getEnabled(){
        return enabled;
    }

    @Override
    public String toString(){
        return
                "ValidationsItem{" +
                        "id = '" + id + '\'' +
                        ",value = '" + value + '\'' +
                        ",enabled = '" + enabled + '\'' +
                        "}";
    }
}

