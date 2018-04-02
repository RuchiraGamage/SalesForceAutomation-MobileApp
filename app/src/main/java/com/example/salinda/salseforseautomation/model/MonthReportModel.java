package com.example.salinda.salseforseautomation.model;


import com.google.gson.annotations.SerializedName;

public class MonthReportModel {
    @SerializedName("Name")
    private String Name;
    @SerializedName("Brand")
    private String Brand;
    @SerializedName("TotalQuantity")
    private int TotalQuantity;
    @SerializedName("Amount")
    private float Amount;

    public MonthReportModel(String name, String brand, int totalQuantity, float amount) {
        Name = name;
        Brand = brand;
        TotalQuantity = totalQuantity;
        Amount = amount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }
}
