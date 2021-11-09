package com.wegolook.schemalibrary;

import com.google.gson.annotations.SerializedName;

public class FormDataItem {

    @SerializedName("id")
    private String id;

    public boolean isSystemGenerated() {
        return IsSystemGenerated;
    }

    public void setSystemGenerated(boolean systemGenerated) {
        IsSystemGenerated = systemGenerated;
    }

    @SerializedName("value")
    private String value;

    @SerializedName("IsSystemGenerated")
    private boolean IsSystemGenerated;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
