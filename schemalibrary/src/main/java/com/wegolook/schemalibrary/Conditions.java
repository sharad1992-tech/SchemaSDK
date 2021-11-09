package com.wegolook.schemalibrary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Conditions {

    @SerializedName("as")
    private String as;
    @SerializedName("any")
    private List<Any> any = null;

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public List<Any> getAny() {
        return any;
    }

    public void setAny(List<Any> any) {
        this.any = any;
    }

    public class Any {

        @SerializedName("all")
        private List<All> all = null;

        public List<All> getAll() {
            return all;
        }

        public void setAll(List<All> all) {
            this.all = all;
        }

    }

    public class All {

        @SerializedName("field")
        private String field;
        @SerializedName("operator")
        private String operator;
        @SerializedName("value")
        private List<String> value = null;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }

    }
}
