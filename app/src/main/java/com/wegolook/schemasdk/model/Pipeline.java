package com.wegolook.schemasdk.model;

public class Pipeline{
    private boolean skipPrep;
    private boolean autoSolicit;
    private boolean skipQA;
    private boolean skipVerification;

    public void setSkipPrep(boolean skipPrep){
        this.skipPrep = skipPrep;
    }

    public boolean isSkipPrep(){
        return skipPrep;
    }

    public void setAutoSolicit(boolean autoSolicit){
        this.autoSolicit = autoSolicit;
    }

    public boolean isAutoSolicit(){
        return autoSolicit;
    }

    public void setSkipQA(boolean skipQA){
        this.skipQA = skipQA;
    }

    public boolean isSkipQA(){
        return skipQA;
    }

    public void setSkipVerification(boolean skipVerification){
        this.skipVerification = skipVerification;
    }

    public boolean isSkipVerification(){
        return skipVerification;
    }

    @Override
    public String toString(){
        return
                "Pipeline{" +
                        "skipPrep = '" + skipPrep + '\'' +
                        ",autoSolicit = '" + autoSolicit + '\'' +
                        ",skipQA = '" + skipQA + '\'' +
                        ",skipVerification = '" + skipVerification + '\'' +
                        "}";
    }
}
