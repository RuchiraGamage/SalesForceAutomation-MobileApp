package com.example.salinda.salseforseautomation.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MonthlyReportModel implements Serializable{
    @SerializedName("Amount")
    private float amount;
    @SerializedName("month")
    private int month;
    @SerializedName("year")
    private int year;

    public MonthlyReportModel(float amount, int month, int year) {
        this.amount = amount;
        this.month = month;
        this.year = year;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
