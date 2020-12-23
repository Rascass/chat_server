package com.solvd.automation.lab.fall.model.message;

public class ChecksumToResponse implements IResponse {
    private int code;
    private String checkSumToDescription;
    private int checkSum;



    public ChecksumToResponse(int code, String checkSumToDescription, int checkSum) {
        this.code = code;
        this.checkSumToDescription = checkSumToDescription;
        this.checkSum = checkSum;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCheckSumToDescription() {
        return checkSumToDescription;
    }

    public void setCheckSumToDescription(String checkSumToDescription) {
        this.checkSumToDescription = checkSumToDescription;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    @Override
    public String toString() {
        return "{\"code\":\""+code+"\",\"checkSumToDescription\":" + checkSumToDescription +"\",\"checkSum\":" + checkSum + '}';
    }
}
