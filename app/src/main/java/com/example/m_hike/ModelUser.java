package com.example.m_hike;

public class ModelUser{
    private String user_id;
    private  String user_email;
    private  String user_name;
    private String user_dayOfBirth;
    private String user_gender;
    private String user_password;
    private String user_image;

    public ModelUser(String user_id, String user_email, String user_name, String user_dayOfBirth, String user_gender, String user_password, String user_image) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_dayOfBirth = user_dayOfBirth;
        this.user_gender = user_gender;
        this.user_password = user_password;
        this.user_image = user_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_dayOfBirth() {
        return user_dayOfBirth;
    }

    public void setUser_dayOfBirth(String user_dayOfBirth) {
        this.user_dayOfBirth = user_dayOfBirth;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}