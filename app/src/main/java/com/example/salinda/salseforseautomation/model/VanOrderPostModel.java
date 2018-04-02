package com.example.salinda.salseforseautomation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VanOrderPostModel {
    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Remarks")
    @Expose
    private String Description;
    @SerializedName("UserId")
    @Expose
    private int UserId;
    @SerializedName("OutletId")
    @Expose
    private int OutletId;
    @SerializedName("Longitude")
    @Expose
    private float Longitude;
    @SerializedName("Latitude")
    @Expose
    private float Latitude;
    @SerializedName("PaymentType")
    @Expose
    private String PaymentType;
    @SerializedName("Amount")
    @Expose
    private float Amount;
    @SerializedName("abc")
    @Expose
    private List<OrderArrayModel> Array;

    public VanOrderPostModel(String date, String discription, int userId, int outletId, float longitude, float latitude, String paymentType, float amount, List<OrderArrayModel> array) {
        this.Date = date;
        this.Description = discription;
        this.UserId = userId;
        this.OutletId = outletId;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.PaymentType = paymentType;
        this.Amount = amount;
        this.Array = array;
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

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getOutletId() {
        return OutletId;
    }

    public void setOutletId(int outletId) {
        OutletId = outletId;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public List<OrderArrayModel> getArray() {
        return Array;
    }

    public void setArray(List<OrderArrayModel> array) {
        Array = array;
    }
}
