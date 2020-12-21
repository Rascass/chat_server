package com.solvd.automation.lab.fall.model.message;

public class SearchMessage {
    String searchLogin;

    public SearchMessage(String searchLogin) {
        this.searchLogin = searchLogin;
    }

    public String getSearchLogin() {
        return searchLogin;
    }

    public void setSearchLogin(String searchLogin) {
        this.searchLogin = searchLogin;
    }

    @Override
    public String toString() {
        return "{\"contactLogin\":\""+ searchLogin +"\"}";
    }
    //{"searchLogin":"searchLogin"}
}
