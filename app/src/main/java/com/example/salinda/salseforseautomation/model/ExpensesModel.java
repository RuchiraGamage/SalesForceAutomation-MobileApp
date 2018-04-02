package com.example.salinda.salseforseautomation.model;

import com.google.gson.annotations.SerializedName;

public class ExpensesModel {
    @SerializedName("Date")
    private String Date;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Amount")
    private float Amount;
    @SerializedName("UserId")
    private int UserId;

    public ExpensesModel(String date, String description, float amount, int userId) {
        Date = date;
        Description = description;
        Amount = amount;
        UserId = userId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
