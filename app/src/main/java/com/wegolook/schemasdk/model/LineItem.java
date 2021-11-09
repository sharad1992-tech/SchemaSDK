package com.wegolook.schemasdk.model;

public class LineItem{
    private String glAccountNumber;
    private String itemCode;
    private String name;
    private String category;
    private String taxCode;

    public void setGlAccountNumber(String glAccountNumber){
        this.glAccountNumber = glAccountNumber;
    }

    public String getGlAccountNumber(){
        return glAccountNumber;
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

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setTaxCode(String taxCode){
        this.taxCode = taxCode;
    }

    public String getTaxCode(){
        return taxCode;
    }

    @Override
    public String toString(){
        return
                "LineItem{" +
                        "glAccountNumber = '" + glAccountNumber + '\'' +
                        ",itemCode = '" + itemCode + '\'' +
                        ",name = '" + name + '\'' +
                        ",category = '" + category + '\'' +
                        ",taxCode = '" + taxCode + '\'' +
                        "}";
    }
}
