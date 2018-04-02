package com.example.salinda.salseforseautomation.model;


public class SQLiteOrderItemModel {

    protected int Id;
    protected int OrderId;
    protected int ProductId;
    protected int ProductQuantity;
    protected String OrderType;

    public SQLiteOrderItemModel(){

    }

    public SQLiteOrderItemModel(int Id, int OrderId, int ProductId, int ProductQuantity, String OrderType){
        this.Id = Id;
        this.OrderId = OrderId;
        this.ProductId = ProductId;
        this.ProductQuantity = ProductQuantity;
        this.OrderType = OrderType;
    }

    public int getId(){return Id;}
    public void setId(int Id){this.Id = Id;}

    public int getOrderId(){return OrderId;}
    public void setOrderId(int OrderId){this.OrderId = OrderId;}

    public int getProductId(){return ProductId;}
    public void setProductId(int ProductId){this.ProductId = ProductId;}

    public int getProductQuantity(){return ProductQuantity;}
    public void setProductQuantity(int ProductQuantity){this.ProductQuantity = ProductQuantity;}

    public String getOrderType(){return OrderType;}
    public void setOrderType(String OrderType){this.OrderType = OrderType;}
}
