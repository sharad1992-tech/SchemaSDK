package com.wegolook.schemasdk.model;

public class LineItemsItem{
    private LineItem lineItem;
    private String lineItemId;
    private LookerFee lookerFee;
    private Price price;
    private Conditions conditions;
    private boolean required;
    private boolean base;

    public void setLineItem(LineItem lineItem){
        this.lineItem = lineItem;
    }

    public LineItem getLineItem(){
        return lineItem;
    }

    public void setLineItemId(String lineItemId){
        this.lineItemId = lineItemId;
    }

    public String getLineItemId(){
        return lineItemId;
    }

    public void setLookerFee(LookerFee lookerFee){
        this.lookerFee = lookerFee;
    }

    public LookerFee getLookerFee(){
        return lookerFee;
    }

    public void setPrice(Price price){
        this.price = price;
    }

    public Price getPrice(){
        return price;
    }

    public void setConditions(Conditions conditions){
        this.conditions = conditions;
    }

    public Conditions getConditions(){
        return conditions;
    }

    public void setRequired(boolean required){
        this.required = required;
    }

    public boolean isRequired(){
        return required;
    }

    public void setBase(boolean base){
        this.base = base;
    }

    public boolean isBase(){
        return base;
    }

    @Override
    public String toString(){
        return
                "LineItemsItem{" +
                        "lineItem = '" + lineItem + '\'' +
                        ",lineItemId = '" + lineItemId + '\'' +
                        ",lookerFee = '" + lookerFee + '\'' +
                        ",price = '" + price + '\'' +
                        ",conditions = '" + conditions + '\'' +
                        ",required = '" + required + '\'' +
                        ",base = '" + base + '\'' +
                        "}";
    }
}
