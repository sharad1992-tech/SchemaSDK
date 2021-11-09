package com.wegolook.schemalibrary;

import com.google.gson.annotations.SerializedName;

public class ImageDataItem {
    @SerializedName("DocID")
    private Integer docID;
    @SerializedName("FieldID")
    private String fieldID;
    @SerializedName("DocUrl")
    private String docUrl;

    public Integer getDocID() {
        return docID;
    }

    public void setDocID(Integer docID) {
        this.docID = docID;
    }

    public String getFieldID() {
        return fieldID;
    }

    public void setFieldID(String fieldID) {
        this.fieldID = fieldID;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
}
