package com.solvd.automation.lab.fall.model.message;

public class RegistrationResponse implements IResponse {
    private int code;
    private String regDescription;

    public RegistrationResponse(int code, String regDescription) {
        this.code = code;
        this.regDescription = regDescription;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getRegDescription() {
        return regDescription;
    }
    public void setRegDescription(String description) {
        this.regDescription = description;
    }

    @Override
    public String toString() {
        return "{\"code\":\""+code+"\",\"regDescription\":" + regDescription +'}';
    }
}
