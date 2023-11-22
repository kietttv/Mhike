package com.example.m_hike;

public class ModelShare {
    String ShareId;
    String User1;
    String User2;
    String HikeId;
    String Notification;

    public ModelShare(String shareId, String user1, String user2, String hikeId, String Notification) {
        ShareId = shareId;
        User1 = user1;
        User2 = user2;
        HikeId = hikeId;
        Notification = Notification;
    }

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }

    public String getShareId() {
        return ShareId;
    }

    public void setShareId(String shareId) {
        ShareId = shareId;
    }

    public String getUser1() {
        return User1;
    }

    public void setUser1(String user1) {
        User1 = user1;
    }

    public String getUser2() {
        return User2;
    }

    public void setUser2(String user2) {
        User2 = user2;
    }

    public String getHikeId() {
        return HikeId;
    }

    public void setHikeId(String hikeId) {
        HikeId = hikeId;
    }
}
