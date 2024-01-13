package com.example.hospitalfinderapp;

public class NewAppointmentClass {
    private String userId;
    private String appId;
    private String userName;
    private String hospitalName;
    private String hospitalId;
    private String appDate;
    private String appTime;

    public NewAppointmentClass() {
    }

    public NewAppointmentClass(String userId, String appId, String userName, String hospitalName, String hospitalId, String appDate, String appTime) {
        this.userId = userId;
        this.appId = appId;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId = hospitalId;
        this.appDate = appDate;
        this.appTime = appTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }
}
