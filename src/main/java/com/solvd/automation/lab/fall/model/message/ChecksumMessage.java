package com.solvd.automation.lab.fall.model.message;



public class ChecksumMessage {
    private String loginOfSender;
    private String loginOfRecipient;
    private int checksum;

    public ChecksumMessage(String loginOfSender, String loginOfRecipient, int checksum) {
        this.loginOfSender = loginOfSender;
        this.loginOfRecipient = loginOfRecipient;
        this.checksum = checksum;
    }

    public String getLoginOfSender() {
        return loginOfSender;
    }

    public void setLoginOfSender(String loginOfSender) {
        this.loginOfSender = loginOfSender;
    }

    public String getLoginOfRecipient() {
        return loginOfRecipient;
    }

    public void setLoginOfRecipient(String loginOfRecipient) {
        this.loginOfRecipient = loginOfRecipient;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    @Override
    public String toString() {
        return "{\"loginFrom\":\""+ loginOfSender + "\",\"loginTo\":\"" + loginOfRecipient + "\",\"checksum\":" + checksum +'}';
    }
}
