package com.example.salinda.salseforseautomation.Message.Chat;


public class UserModel {


    int id;
    private String user_name,user_type,city,image;

    public UserModel(int id, String user_name, String user_type, String city, String image) {
        this.id = id;
        this.user_name = user_name;
        this.user_type = user_type;
        this.city = city;
        this.image = image;
    }

    public UserModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
