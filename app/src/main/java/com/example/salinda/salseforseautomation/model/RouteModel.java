package com.example.salinda.salseforseautomation.model;


import com.google.gson.annotations.SerializedName;

public class RouteModel {
    @SerializedName("Id")
    private int Id;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Description")
    private String Description;

    public RouteModel(int id, String name, String description) {
        Id = id;
        Name = name;
        Description = description;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

