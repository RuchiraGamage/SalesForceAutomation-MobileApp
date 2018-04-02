package com.example.salinda.salseforseautomation.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("Id")
    private int Id;
    @SerializedName("FirstName")
    private String FirstName;
    @SerializedName("LastName")
    private String LastName;
    @SerializedName("Email")
    private String Email;
    @SerializedName("UserType")
    private String UserType;
    @SerializedName("status")
    private String Status;
    @SerializedName("Image")
    private String Image;
    @SerializedName("Token")
    private String Token;

    public int getId(){return Id;}
    public void setId(int Id){this.Id = Id;}

    public String getFirstName(){return FirstName;}
    public void setFirstName(String FirstName){this.FirstName = FirstName;}

    public String getLastName(){return LastName;}
    public void setLastName(String LastName){this.LastName = LastName;}

    public String getEmail(){return Email;}
    public void setEmail(String Email){this.Email = Email;}

    public String getUserType(){return UserType;}
    public void setUserType(String UserType){this.UserType = UserType;}

    public String getStatus(){return Status;}
    public void setStatus(String Status){this.Status = Status;}

    public String getImage() {return Image;}

    public void setImage(String image) {Image = image;}

    public String getToken() {return Token;}
    public void setToken(String token) {Token = token;}
}
