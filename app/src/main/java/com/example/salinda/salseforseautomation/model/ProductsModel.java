package com.example.salinda.salseforseautomation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductsModel implements Serializable {
    @SerializedName("Id")
    private int Id;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Brand")
    private String Brand;
    @SerializedName("Category")
    private String Category;
    @SerializedName("ManufacturePrice")
    private float ManufacturePrice;
    @SerializedName("RetailPrice")
    private float Price;
    @SerializedName("Quantity")
    private int Quantity;
    @SerializedName("DiscountType")
    private String DiscountType;
    @SerializedName("Image")
    private String Image;

    public ProductsModel() {}

    public ProductsModel(int Id,String Name,String Brand,String Category,float ManufacturePrice,float Price,int Quantity, String DiscountType, String Image) {
        this.Id = Id;
        this.Name = Name;
        this.Brand = Brand;
        this.Category = Category;
        this.ManufacturePrice = ManufacturePrice;
        this.Price = Price;
        this.Quantity = Quantity;
        this.DiscountType = DiscountType;
        this.Image = Image;
    }

    public int getId() {return Id;}
    public void setId(int Id) {this.Id = Id;}

    public String getName() {return Name;}
    public void setName(String Name) {this.Name = Name;}

    public String getBrand() {return Brand;}
    public void setBrand(String Brand) {this.Brand = Brand;}

    public String getCategory() {return Category;}
    public void setCategory(String Category) {this.Category = Category;}

    public float getManufacturePrice() {return ManufacturePrice;}
    public void setManufacturePrice(float ManufacturePrice) {this.ManufacturePrice = ManufacturePrice;}

    public float getPrice() {return Price;}
    public void setPrice(float Price) {this.Price = Price;}

    public int getQuantity() {return Quantity;}
    public void setQuantity(int Quantity) {this.Quantity = Quantity;}

    public String getDiscountType(){return DiscountType;}
    public void setDiscountType(String DiscountType){this.DiscountType = DiscountType;}

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
