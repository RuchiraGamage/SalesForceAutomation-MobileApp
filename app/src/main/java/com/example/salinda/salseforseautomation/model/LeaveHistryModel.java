package com.example.salinda.salseforseautomation.model;


import com.google.gson.annotations.SerializedName;

public class LeaveHistryModel {
    @SerializedName("Id")
    private int Id;
    @SerializedName("StartingDate")
    private String StartingDate;
    @SerializedName("EndingDate")
    private String EndingDate;
    @SerializedName("Reason")
    private String Reason;
    @SerializedName("status")
    private String status;
    @SerializedName("UserId")
    private int UserId;

    public LeaveHistryModel(int id, String startingDate, String endingDate, String reason, String status, int userId) {
        Id = id;
        StartingDate = startingDate;
        EndingDate = endingDate;
        Reason = reason;
        this.status = status;
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStartingDate() {
        return StartingDate;
    }

    public void setStartingDate(String startingDate) {
        StartingDate = startingDate;
    }

    public String getEndingDate() {
        return EndingDate;
    }

    public void setEndingDate(String endingDate) {
        EndingDate = endingDate;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}

