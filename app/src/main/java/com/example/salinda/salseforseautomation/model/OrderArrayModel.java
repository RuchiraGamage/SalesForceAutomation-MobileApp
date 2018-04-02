package com.example.salinda.salseforseautomation.model;

import com.google.gson.annotations.SerializedName;

public class OrderArrayModel {
    @SerializedName("ProductId")
    private int ProductId;
    @SerializedName("ProductQuantity")
    private int ProductQuantity;
    @SerializedName("OrderType")
    private String OrderType;

    public OrderArrayModel(){}

    public OrderArrayModel(int ProductId, int ProductQuantity, String OrderType){
        this.ProductId = ProductId;
        this.ProductQuantity = ProductQuantity;
        this.OrderType = OrderType;
    }

    public int getProductId() {return ProductId;}
    public void setProductId(int productId) {ProductId = productId;}

    public int getProductQuantity() {return ProductQuantity;}
    public void setProductQuantity(int productQuantity) {ProductQuantity = productQuantity;}

    public String getOrderType() {return OrderType;}
    public void setOrderType(String orderType) {OrderType = orderType;}
}
