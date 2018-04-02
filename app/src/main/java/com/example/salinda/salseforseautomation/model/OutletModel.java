package com.example.salinda.salseforseautomation.model;

import com.google.gson.annotations.SerializedName;

public class OutletModel {
    @SerializedName("Id")
    private int Id;
    @SerializedName("Name")
    private String Name;
    @SerializedName("ContactNo")
    private String ContactNo;
    @SerializedName("Barcode")
    private String Barcode;
    @SerializedName("Longitude")
    private float Longitude;
    @SerializedName("Latitude")
    private float Latitude;
    @SerializedName("OwnerName")
    private String OwnerName;
    @SerializedName("Email")
    private String Email;
    @SerializedName("Status")
    private String Status;
    @SerializedName("RouteId")
    private int RouteId;

    public OutletModel() {}

    public OutletModel(int Id, String Name, String ContactNo, String Barcode, float Longitude, float Latitude, String OwnerName,
                       String Email, String Status, int RouteId){
        this.Id = Id;
        this.Name = Name;
        this.ContactNo = ContactNo;
        this.Barcode = Barcode;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
        this.OwnerName = OwnerName;
        this.Email = Email;
        this.Status = Status;
        this.RouteId = RouteId;
    }

    public int getId() {return Id;}
    public void setId(int Id) {this.Id = Id;}

    public String getName() {return Name;}
    public void setName(String Name) {this.Name = Name;}

    public String getContactNo() {return ContactNo;}
    public void setContactNo(String ContactNo) {this.ContactNo = ContactNo;}

    public String getBarcode() {return Barcode;}
    public void setBarcode(String Barcode) {this.Barcode = Barcode;}

    public float getLongitude() {return Longitude;}
    public void setLongitude(float Longitude) {this.Longitude = Longitude;}

    public float getLatitude() {return Latitude;}
    public void setLatitude(float Latitude) {this.Latitude = Latitude;}

    public String getOwnerName() {return OwnerName;}
    public void setOwnerName(String OwnerName) {this.OwnerName = OwnerName;}

    public String getEmail() {return Email;}
    public void setEmail(String Email) {this.Email = Email;}

    public String getStatus() {return Status;}
    public void setStatus(String Status) {this.Status = Status;}

    public int getRouteId() {return RouteId;}
    public void setRouteId(int RouteId) {this.RouteId = RouteId;}


}