package com.wegolook.schemasdk.model;

import java.util.List;

public class CompaniesItem{
    private List<Object> lineItems;
    private Object pipeline;
    private String companyId;
    private String name;
    private Company company;

    public void setLineItems(List<Object> lineItems){
        this.lineItems = lineItems;
    }

    public List<Object> getLineItems(){
        return lineItems;
    }

    public void setPipeline(Object pipeline){
        this.pipeline = pipeline;
    }

    public Object getPipeline(){
        return pipeline;
    }

    public void setCompanyId(String companyId){
        this.companyId = companyId;
    }

    public String getCompanyId(){
        return companyId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCompany(Company company){
        this.company = company;
    }

    public Company getCompany(){
        return company;
    }

    @Override
    public String toString(){
        return
                "CompaniesItem{" +
                        "lineItems = '" + lineItems + '\'' +
                        ",pipeline = '" + pipeline + '\'' +
                        ",companyId = '" + companyId + '\'' +
                        ",name = '" + name + '\'' +
                        ",company = '" + company + '\'' +
                        "}";
    }
}