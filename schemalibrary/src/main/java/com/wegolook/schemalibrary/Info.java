package com.wegolook.schemalibrary;

import java.util.List;

public class Info{
    private EstimatedTime estimatedTime;
    private Object isConsumer;
    private String country;
    private String clientWarning;
    private String schemaDescription;
    private List<String> requiredEquipment;
    private String schemaGroup;
    private String schemaName;
    private List<DeliveryOptionsItem> deliveryOptions;
    private List<String> tags;
    private Pipeline pipeline;
    private String subSchema;
    private String currency;

    public void setEstimatedTime(EstimatedTime estimatedTime){
        this.estimatedTime = estimatedTime;
    }

    public EstimatedTime getEstimatedTime(){
        return estimatedTime;
    }

    public void setIsConsumer(Object isConsumer){
        this.isConsumer = isConsumer;
    }

    public Object getIsConsumer(){
        return isConsumer;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return country;
    }

    public void setClientWarning(String clientWarning){
        this.clientWarning = clientWarning;
    }

    public String getClientWarning(){
        return clientWarning;
    }

    public void setSchemaDescription(String schemaDescription){
        this.schemaDescription = schemaDescription;
    }

    public String getSchemaDescription(){
        return schemaDescription;
    }

    public void setRequiredEquipment(List<String> requiredEquipment){
        this.requiredEquipment = requiredEquipment;
    }

    public List<String> getRequiredEquipment(){
        return requiredEquipment;
    }

    public void setSchemaGroup(String schemaGroup){
        this.schemaGroup = schemaGroup;
    }

    public String getSchemaGroup(){
        return schemaGroup;
    }

    public void setSchemaName(String schemaName){
        this.schemaName = schemaName;
    }

    public String getSchemaName(){
        return schemaName;
    }

    public void setDeliveryOptions(List<DeliveryOptionsItem> deliveryOptions){
        this.deliveryOptions = deliveryOptions;
    }

    public List<DeliveryOptionsItem> getDeliveryOptions(){
        return deliveryOptions;
    }

    public void setTags(List<String> tags){
        this.tags = tags;
    }

    public List<String> getTags(){
        return tags;
    }

    public void setPipeline(Pipeline pipeline){
        this.pipeline = pipeline;
    }

    public Pipeline getPipeline(){
        return pipeline;
    }

    public void setSubSchema(String subSchema){
        this.subSchema = subSchema;
    }

    public String getSubSchema(){
        return subSchema;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public String getCurrency(){
        return currency;
    }

    @Override
    public String toString(){
        return
                "Info{" +
                        "estimatedTime = '" + estimatedTime + '\'' +
                        ",isConsumer = '" + isConsumer + '\'' +
                        ",country = '" + country + '\'' +
                        ",clientWarning = '" + clientWarning + '\'' +
                        ",schemaDescription = '" + schemaDescription + '\'' +
                        ",requiredEquipment = '" + requiredEquipment + '\'' +
                        ",schemaGroup = '" + schemaGroup + '\'' +
                        ",schemaName = '" + schemaName + '\'' +
                        ",deliveryOptions = '" + deliveryOptions + '\'' +
                        ",tags = '" + tags + '\'' +
                        ",pipeline = '" + pipeline + '\'' +
                        ",subSchema = '" + subSchema + '\'' +
                        ",currency = '" + currency + '\'' +
                        "}";
    }
}