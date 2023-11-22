package com.example.m_hike;

public class ModelHike {
    private String user_id, hike_id, hike_name, hike_location, hike_date, hike_parking, hike_length, hike_level, hike_description;

    public ModelHike(String user_id,String hike_id, String hike_name, String hike_location,String hike_date,String hike_parking,
                     String hike_length,String hike_level,String hike_description){

        this.user_id = user_id;
        this.hike_id = hike_id;
        this.hike_name = hike_name;
        this.hike_location = hike_location;
        this.hike_date = hike_date;
        this.hike_parking = hike_parking;
        this.hike_length = hike_length;
        this.hike_level = hike_level;
        this.hike_description = hike_description;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHike_id() {
        return hike_id;
    }

    public void setHike_id(String hike_id) {
        this.hike_id = hike_id;
    }

    public String getHike_name() {
        return hike_name;
    }

    public void setHike_name(String hike_name) {
        this.hike_name = hike_name;
    }

    public String getHike_location() {
        return hike_location;
    }

    public void setHike_location(String hike_location) {
        this.hike_location = hike_location;
    }

    public String getHike_date() {
        return hike_date;
    }

    public void setHike_date(String hike_date) {
        this.hike_date = hike_date;
    }

    public String getHike_parking() {
        return hike_parking;
    }

    public void setHike_parking(String hike_parking) {
        this.hike_parking = hike_parking;
    }

    public String getHike_length() {
        return hike_length;
    }

    public void setHike_length(String hike_length) {
        this.hike_length = hike_length;
    }

    public String getHike_level() {
        return hike_level;
    }

    public void setHike_level(String hike_level) {
        this.hike_level = hike_level;
    }

    public String getHike_description() {
        return hike_description;
    }

    public void setHike_description(String hike_description) {
        this.hike_description = hike_description;
    }
}
