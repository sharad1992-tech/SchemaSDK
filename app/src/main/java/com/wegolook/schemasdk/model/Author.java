package com.wegolook.schemasdk.model;

public class Author{
    private String firstName;
    private String lastName;
    private String avatarUrl;

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl(){
        return avatarUrl;
    }

    @Override
    public String toString(){
        return
                "Author{" +
                        "firstName = '" + firstName + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",avatarUrl = '" + avatarUrl + '\'' +
                        "}";
    }
}
