package com.example.salinda.salseforseautomation.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PreOrderPostModel {
    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Remarks")
    @Expose
    private String Remarks;
    @SerializedName("DeliveryDate")
    @Expose
    private String DeliveryDate;
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
    @SerializedName("abc")
    @Expose
    private List<OrderArrayModel> Array;


    public PreOrderPostModel(String Date, String Remarks, String DeliveryDate, int UserId, int OutletId, float Longitude, float Latitude, List<OrderArrayModel> Array){
        this.Date = Date;
        this.Remarks = Remarks;
        this.DeliveryDate = DeliveryDate;
        this.UserId = UserId;
        this.OutletId = OutletId;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.Array = Array;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
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

    public List<OrderArrayModel> getArray() {
        return Array;
    }

    public void setArray(List<OrderArrayModel> array) {
        Array = array;
    }

}