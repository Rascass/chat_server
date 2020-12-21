package com.solvd.automation.lab.fall.model.message;

public class Response implements IResponse {
    private int code;
    private String description;

    public Response(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{\"code\":\""+code+"\",\"description\":" + description +'}';
    }
}
