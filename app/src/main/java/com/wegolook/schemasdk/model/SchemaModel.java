package com.wegolook.schemasdk.model;

import java.util.List;

public class SchemaModel{
    private List<LineItemsItem> lineItems;
    private String createdAt;
    private List<CompaniesItem> companies;
    private Author author;
    private List<GroupsItem> groups;
    private String id;
    private String authorId;
    private List<FieldsItem> fields;
    private Info info;
    private String status;
    private String updatedAt;

    public void setLineItems(List<LineItemsItem> lineItems){
        this.lineItems = lineItems;
    }

    public List<LineItemsItem> getLineItems(){
        return lineItems;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setCompanies(List<CompaniesItem> companies){
        this.companies = companies;
    }

    public List<CompaniesItem> getCompanies(){
        return companies;
    }

    public void setAuthor(Author author){
        this.author = author;
    }

    public Author getAuthor(){
        return author;
    }

    public void setGroups(List<GroupsItem> groups){
        this.groups = groups;
    }

    public List<GroupsItem> getGroups(){
        return groups;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setAuthorId(String authorId){
        this.authorId = authorId;
    }

    public String getAuthorId(){
        return authorId;
    }

    public void setFields(List<FieldsItem> fields){
        this.fields = fields;
    }

    public List<FieldsItem> getFields(){
        return fields;
    }

    public void setInfo(Info info){
        this.info = info;
    }

    public Info getInfo(){
        return info;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    @Override
    public String toString(){
        return
                "SchemaModel{" +
                        "lineItems = '" + lineItems + '\'' +
                        ",createdAt = '" + createdAt + '\'' +
                        ",companies = '" + companies + '\'' +
                        ",author = '" + author + '\'' +
                        ",groups = '" + groups + '\'' +
                        ",_id = '" + id + '\'' +
                        ",authorId = '" + authorId + '\'' +
                        ",fields = '" + fields + '\'' +
                        ",info = '" + info + '\'' +
                        ",status = '" + status + '\'' +
                        ",updatedAt = '" + updatedAt + '\'' +
                        "}";
    }
}
