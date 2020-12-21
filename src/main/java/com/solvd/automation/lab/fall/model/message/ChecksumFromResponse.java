package com.solvd.automation.lab.fall.model.message;

public class ChecksumFromResponse implements AbstractMessage {
    private int code;
    private String checkSumFromDescription;

    public ChecksumFromResponse(int code, String checkSumFromDescription) {
        this.code = code;
        this.checkSumFromDescription = checkSumFromDescription;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCheckSumFromDescription() {
        return checkSumFromDescription;
    }

    public void setCheckSumFromDescription(String checkSumFromDescription) {
        this.checkSumFromDescription = checkSumFromDescription;
    }

    @Override
    public String toString() {
        return "{\"code\":\""+code+"\",\"checkSumFromDescription\":" + checkSumFromDescription +'}';
    }
}
