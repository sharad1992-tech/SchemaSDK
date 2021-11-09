package com.wegolook.schemasdk.model;

public class EstimatedTime{
    private int min;
    private int max;

    public void setMin(int min){
        this.min = min;
    }

    public int getMin(){
        return min;
    }

    public void setMax(int max){
        this.max = max;
    }

    public int getMax(){
        return max;
    }

    @Override
    public String toString(){
        return
                "EstimatedTime{" +
                        "min = '" + min + '\'' +
                        ",max = '" + max + '\'' +
                        "}";
    }
}
