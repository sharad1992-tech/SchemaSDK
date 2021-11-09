package com.wegolook.schemalibrary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SchemaResponse {
    @SerializedName("FormData")
    private ArrayList<FormDataItem> formData;

    public ArrayList<FormDataItem> getCustomData() {
        return CustomData;
    }

    public void setCustomData(ArrayList<FormDataItem> customData) {
        CustomData = customData;
    }

    @SerializedName("Schema")
    private Schema schema;

    @SerializedName("CustomData")
    private ArrayList<FormDataItem> CustomData;

    @SerializedName("ImageData")
    private ArrayList<ImageDataItem> ImageData;

    public void setFormData(ArrayList<FormDataItem> formData){
        this.formData = formData;
    }
    public ArrayList<FormDataItem> getFormData(){
        return formData;
    }

    public void setSchema(Schema schema){
        this.schema = schema;
    }

    public Schema getSchema(){
        return schema;
    }

    public ArrayList<ImageDataItem> getImageData() {
        return ImageData;
    }
    public void setImageData(ArrayList<ImageDataItem> imageData) {
        ImageData = imageData;
    }
}
