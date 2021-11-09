package com.wegolook.schemalibrary;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class FieldsItem {
    private ArrayList<ReportsItem> reports;
    private ArrayList<Object> metadata;



    private String defaultValue;

    private Object tooltip;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    private String imageId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    private String fileId;


    public LinearLayout getFieldLayout() {
        return fieldLayout;
    }

    public void setFieldLayout(LinearLayout fieldLayout) {
        this.fieldLayout = fieldLayout;
    }

    private int index;
    private LinearLayout fieldLayout;


    public RelativeLayout getImageDescriptionLayout() {
        return imageDescriptionLayout;
    }

    public void setImageDescriptionLayout(RelativeLayout imageDescriptionLayout) {
        this.imageDescriptionLayout = imageDescriptionLayout;
    }

    public LinearLayout getPreviewLayout() {
        return previewLayout;
    }

    private RelativeLayout imageDescriptionLayout;

    public void setPreviewLayout(LinearLayout previewLayout) {
        this.previewLayout = previewLayout;
    }

    public RelativeLayout getImageLayout() {
        return imageLayout;
    }

    public void setImageLayout(RelativeLayout imageLayout) {
        this.imageLayout = imageLayout;
    }

    private View view;
    private LinearLayout previewLayout;
    private RelativeLayout imageLayout;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    private byte[] imageArray;
    private String imageName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getGroupPos() {
        return groupPos;
    }

    public void setGroupPos(int groupPos) {
        this.groupPos = groupPos;
    }

    private Object tooltipId;
    private ArrayList<ContextsItem> contexts;
    private String label;
    private int groupPos;
    private int fieldPos;

    public int getFieldPos() {
        return fieldPos;
    }

    public void setFieldPos(int fieldPos) {
        this.fieldPos = fieldPos;
    }

    public byte[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(byte[] imageArray) {
        this.imageArray = imageArray;
    }

    private Object uniqueFieldId;

    public boolean isReadType() {
        return isReadType;
    }

    public void setReadType(boolean readType) {
        isReadType = readType;
    }

    private String component;
    private ArrayList<Options> options;
    private Object highlightedField;
    private String id;
    private ArrayList<ValidationsItem> validations;
    private ArrayList<PropertiesItem> properties;
    private String group;
    private boolean isReadType =false;

    public void setReports(ArrayList<ReportsItem> reports){
        this.reports = reports;
    }

    public ArrayList<ReportsItem> getReports(){
        return reports;
    }

    public void setMetadata(ArrayList<Object> metadata){
        this.metadata = metadata;
    }

    public ArrayList<Object> getMetadata(){
        return metadata;
    }

    public void setDefaultValue(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue(){
        return defaultValue;
    }

    public void setTooltip(Object tooltip){
        this.tooltip = tooltip;
    }

    public Object getTooltip(){
        return tooltip;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    public void setTooltipId(Object tooltipId){
        this.tooltipId = tooltipId;
    }

    public Object getTooltipId(){
        return tooltipId;
    }

    public void setContexts(ArrayList<ContextsItem> contexts){
        this.contexts = contexts;
    }

    public ArrayList<ContextsItem> getContexts(){
        return contexts;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public void setUniqueFieldId(Object uniqueFieldId){
        this.uniqueFieldId = uniqueFieldId;
    }

    public Object getUniqueFieldId(){
        return uniqueFieldId;
    }

    public void setComponent(String component){
        this.component = component;
    }

    public String getComponent(){
        return component;
    }

    public void setOptions(ArrayList<Options> options){
        this.options = options;
    }

    public ArrayList<Options> getOptions(){
        return options;
    }

    public void setHighlightedField(Object highlightedField){
        this.highlightedField = highlightedField;
    }

    public Object getHighlightedField(){
        return highlightedField;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setValidations(ArrayList<ValidationsItem> validations){
        this.validations = validations;
    }

    public ArrayList<ValidationsItem> getValidations(){
        return validations;
    }

    public void setProperties(ArrayList<PropertiesItem> properties){
        this.properties = properties;
    }

    public ArrayList<PropertiesItem> getProperties(){
        return properties;
    }

    public void setGroup(String group){
        this.group = group;
    }

    public String getGroup(){
        return group;
    }
}