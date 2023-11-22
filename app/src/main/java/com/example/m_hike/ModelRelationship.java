package com.example.m_hike;

public class ModelRelationship {
    String RelationshipId;
    String User1;
    String User2;
    String RelationStatus;

    public ModelRelationship(String relationshipId, String user1, String user2, String relationStatus) {
        RelationshipId = relationshipId;
        User1 = user1;
        User2 = user2;
        RelationStatus = relationStatus;
    }

    public String getRelationshipId() {
        return RelationshipId;
    }

    public void setRelationshipId(String relationshipId) {
        RelationshipId = relationshipId;
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

    public String getRelationStatus() {
        return RelationStatus;
    }

    public void setRelationStatus(String relationStatus) {
        RelationStatus = relationStatus;
    }
}
