package com.example.salinda.salseforseautomation.model;

import java.io.Serializable;

public class PQModel implements Serializable{
    private int ProductId;
    private int Quantity;
    private String OrderType;
    private String Name;
    private String Brand;
    private float Price;

    public PQModel(){}

    public PQModel(int ProductId, int Quantity, String OrderType, String Name, String Brand, float Price){
        this.ProductId = ProductId;
        this.Quantity = Quantity;
        this.OrderType = OrderType;
        this.Name = Name;
        this.Brand = Brand;
        this.Price = Price;
    }

    public int getProductId(){return ProductId;}
    public void setProductId(int ProductId){this.ProductId = ProductId;}

    public int getQuantity(){return Quantity;}
    public void setQuantity(int Quantity){this.Quantity = Quantity;}

    public String getOrderType(){return OrderType;}
    public void setOrderType(String OrderType){this.OrderType = OrderType;}

    public String getName(){return Name;}
    public void setName(String Name){this.Name = Name;}

    public String getBrand(){return Brand;}
    public void setBrand(String Brand){this.Brand = Brand;}

    public float getPrice(){return Price;}
    public void setPrice(float Price){this.Price = Price;}
}
