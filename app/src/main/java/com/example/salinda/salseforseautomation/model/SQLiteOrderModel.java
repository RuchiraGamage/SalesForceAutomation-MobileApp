package com.example.salinda.salseforseautomation.model;

public class SQLiteOrderModel {

    protected int Id;
    protected String Date;
    protected String Description;
    protected int UserId;
    protected int OutletId;
    protected float Longitude;
    protected float Latitude;
    protected String PaymentType;
    protected float Amount;
    protected String DeliveryDate;
    protected String OrderType;

    public SQLiteOrderModel(){

    }

    public SQLiteOrderModel(int Id, String Date, String Description, int UserId, int OutletId, float Longitude, float Latitude, String PaymentType,
                            float Amount, String DeliveryDate, String OrderType){
        this.Id = Id;
        this.Date = Date;
        this.Description = Description;
        this.UserId = UserId;
        this.OutletId = OutletId;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.PaymentType = PaymentType;
        this.Amount = Amount;
        this.DeliveryDate = DeliveryDate;
        this.OrderType = OrderType;
    }

    public int getId(){return Id;}
    public void setId(int Id){this.Id = Id;}

    public String getDate(){return Date;}
    public void setDate(String Date){this.Date = Date;}

    public String getDescription(){return Description;}
    public void setDescription(String Description){this.Description = Description;}

    public int getUserId(){return UserId;}
    public void setUserId(int UserId){this.UserId = UserId;}

    public int getOutletId(){return OutletId;}
    public void setOutletId(int OutletId){this.OutletId = OutletId;}

    public float getLongitude(){return Longitude;}
    public void setLongitude(float Longtide){this.Longitude = Longtide;}

    public float getLatitude(){return Latitude;}
    public void setLatitude(float Latitude){this.Latitude = Latitude;}

    public String getPaymentType(){return PaymentType;}
    public void setPaymentType(String PaymentType){this.PaymentType = PaymentType;}

    public float getAmount(){return Amount;}
    public void setAmount(float Amount){this.Amount = Amount;}

    public String getDeliveryDate(){return DeliveryDate;}
    public void setDeliveryDate(String DeliveryDate){this.DeliveryDate = DeliveryDate;}

    public String getOrderType(){return OrderType;}
    public void setOrderType(String OrderType){this.OrderType = OrderType;}
}
