package com.example.m_hike;

public class ModelObservation {
    private String hike_id, obser_id, obser_name, obser_time, obser_comment, obser_image;

    public ModelObservation(String hike_id, String obser_id, String obser_name,
                            String obser_time, String obser_comment, String obser_image) {
        this.hike_id = hike_id;
        this.obser_id = obser_id;
        this.obser_name = obser_name;
        this.obser_time = obser_time;
        this.obser_comment = obser_comment;
        this.obser_image = obser_image;
    }

    public String getHike_id() {
        return hike_id;
    }

    public void setHike_id(String hike_id) {
        this.hike_id = hike_id;
    }

    public String getObser_id() {
        return obser_id;
    }

    public void setObser_id(String obser_id) {
        this.obser_id = obser_id;
    }

    public String getObser_name() {
        return obser_name;
    }

    public void setObser_name(String obser_name) {
        this.obser_name = obser_name;
    }

    public String getObser_time() {
        return obser_time;
    }

    public void setObser_time(String obser_time) {
        this.obser_time = obser_time;
    }

    public String getObser_comment() {
        return obser_comment;
    }

    public void setObser_comment(String obser_comment) {
        this.obser_comment = obser_comment;
    }

    public String getObser_image() {
        return obser_image;
    }

    public void setObser_image(String obser_image) {
        this.obser_image = obser_image;
    }
}
