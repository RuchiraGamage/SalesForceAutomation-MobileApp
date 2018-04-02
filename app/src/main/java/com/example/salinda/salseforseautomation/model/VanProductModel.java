package com.example.salinda.salseforseautomation.model;


import com.google.gson.annotations.SerializedName;

public class VanProductModel {
    @SerializedName("ProductId")
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
    @SerializedName("VanQuantity")
    private int Quantity;
    @SerializedName("DiscountType")
    private String DiscountType;
    @SerializedName("Image")
    private String Image;

    public VanProductModel(int id, String name, String brand, String category, float manufacturePrice, float price, int quantity, String discountType, String image) {
        Id = id;
        Name = name;
        Brand = brand;
        Category = category;
        ManufacturePrice = manufacturePrice;
        Price = price;
        Quantity = quantity;
        DiscountType = discountType;
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public float getManufacturePrice() {
        return ManufacturePrice;
    }

    public void setManufacturePrice(float manufacturePrice) {
        ManufacturePrice = manufacturePrice;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
