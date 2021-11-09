package com.wegolook.schemasdk.model;

import java.util.List;

public class ContextsItem {
    private String environment;
    private List<Conditions> conditions;
    private String render;

    public void setEnvironment(String environment){
        this.environment = environment;
    }

    public String getEnvironment(){
        return environment;
    }

    public void setConditions(List<Conditions> conditions){
        this.conditions = conditions;
    }

    public List<Conditions> getConditions(){
        return conditions;
    }

    public void setRender(String render){
        this.render = render;
    }

    public String getRender(){
        return render;
    }

    @Override
    public String toString(){
        return
                "ContextsItem{" +
                        "environment = '" + environment + '\'' +
                        ",conditions = '" + conditions + '\'' +
                        ",render = '" + render + '\'' +
                        "}";
    }
}