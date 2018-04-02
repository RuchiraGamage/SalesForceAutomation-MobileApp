package com.example.salinda.salseforseautomation.model;

import com.google.gson.annotations.SerializedName;

public class LeaveModel {
    @SerializedName("StartingDate")
    private String StartingDate;
    @SerializedName("EndingDate")
    private String EndingDate;
    @SerializedName("Reason")
    private String Reason;
    @SerializedName("UserId")
    private int UserId;

    public LeaveModel(String startingDate, String endingDate, String reason, int userId) {
        StartingDate = startingDate;
        EndingDate = endingDate;
        Reason = reason;
        UserId = userId;
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

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
