package com.wegolook.schemasdk.model;

import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

public class GroupsItem {
    private String groupType;

    private List<Object> metadata;
    private LinearLayout childLayout;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    private ImageView imageView;

    public LinearLayout getChildLayout() {
        return childLayout;
    }

    public void setChildLayout(LinearLayout childLayout) {
        this.childLayout = childLayout;
    }

    public boolean isExpended() {
        return isExpended;
    }

    public void setExpended(boolean expended) {
        isExpended = expended;
    }

    private String name;
    private boolean isExpended = false;

    public List<FieldsItem> getField() {
        return field;
    }

    public void setField(List<FieldsItem> field) {
        this.field = field;
    }

    private List<FieldsItem> field;

    public void setGroupType(String groupType){
        this.groupType = groupType;
    }

    public String getGroupType(){
        return groupType;
    }

    public void setMetadata(List<Object> metadata){
        this.metadata = metadata;
    }

    public List<Object> getMetadata(){
        return metadata;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return
                "GroupsItem{" +
                        "groupType = '" + groupType + '\'' +
                        ",metadata = '" + metadata + '\'' +
                        ",name = '" + name + '\'' +
                        "}";
    }
}