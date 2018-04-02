package com.example.salinda.salseforseautomation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutstandingModel {
    @SerializedName("Amount")
    @Expose
    private float Amount;
    @SerializedName("OutletId")
    @Expose
    private int OutletId;

    public OutstandingModel(){

    }

    public OutstandingModel(float Amount, int OutletId){
        this.Amount = Amount;
        this.OutletId = OutletId;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public int getOutletId() {
        return OutletId;
    }

    public void setOutletId(int outletId) {
        OutletId = outletId;
    }
}

