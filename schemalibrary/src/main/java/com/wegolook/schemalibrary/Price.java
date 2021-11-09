package com.wegolook.schemalibrary;

public class Price{
    private int amount;
    private String currency;

    public void setAmount(int amount){
        this.amount = amount;
    }

    public int getAmount(){
        return amount;
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
                "Price{" +
                        "amount = '" + amount + '\'' +
                        ",currency = '" + currency + '\'' +
                        "}";
    }
}
