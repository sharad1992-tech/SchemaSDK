package com.wegolook.schemalibrary;

public class ReportsItem{
    private String environment;
    private Object id;
    private boolean enabled;

    public void setEnvironment(String environment){
        this.environment = environment;
    }

    public String getEnvironment(){
        return environment;
    }

    public void setId(Object id){
        this.id = id;
    }

    public Object getId(){
        return id;
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
                "ReportsItem{" +
                        "environment = '" + environment + '\'' +
                        ",_id = '" + id + '\'' +
                        ",enabled = '" + enabled + '\'' +
                        "}";
    }
}
