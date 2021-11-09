package com.wegolook.schemasdk.model;

public class DeliveryOptionsItem{
    private boolean standard;
    private int lookerDays;
    private Price price;
    private String itemCode;
    private String name;
    private int days;
    private String id;

    public void setStandard(boolean standard){
        this.standard = standard;
    }

    public boolean isStandard(){
        return standard;
    }

    public void setLookerDays(int lookerDays){
        this.lookerDays = lookerDays;
    }

    public int getLookerDays(){
        return lookerDays;
    }

    public void setPrice(Price price){
        this.price = price;
    }

    public Price getPrice(){
        return price;
    }

    public void setItemCode(String itemCode){
        this.itemCode = itemCode;
    }

    public String getItemCode(){
        return itemCode;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDays(int days){
        this.days = days;
    }

    public int getDays(){
        return days;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    @Override
    public String toString(){
        return
                "DeliveryOptionsItem{" +
                        "standard = '" + standard + '\'' +
                        ",lookerDays = '" + lookerDays + '\'' +
                        ",price = '" + price + '\'' +
                        ",itemCode = '" + itemCode + '\'' +
                        ",name = '" + name + '\'' +
                        ",days = '" + days + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}
