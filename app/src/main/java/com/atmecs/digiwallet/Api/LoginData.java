package com.atmecs.digiwallet.Api;

public class LoginData {

    public String phoneNumber;
    public String password;
    public String userId;
    public String message;
    public String forgotPasswordPhoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getForgotPasswordPhoneNumber() {
        return forgotPasswordPhoneNumber;
    }

    public void setForgotPasswordPhoneNumber(String forgotPasswordPhoneNumber) {
        this.forgotPasswordPhoneNumber = forgotPasswordPhoneNumber;
    }
}
