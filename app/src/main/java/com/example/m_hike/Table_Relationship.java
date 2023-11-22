package com.example.m_hike;

public class Table_Relationship {
    public static final String TABLE_NAME = "RELATIONSHIP";
    public static final String RELATIONSHIP_ID = "RELATION_ID";
    public static final String USER_1 = "USER_1";
    public static final String USER_2 = "USER_2";
    public static final String STATUS = "STATUS";
    public static final String CREATE_TABLE_RELATIONSHIP = "CREATE TABLE " + TABLE_NAME + " ("
            + RELATIONSHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STATUS + " TEXT NOT NULL, "
            + USER_1 + " INTEGER, "
            + USER_2 + " INTEGER, "
            + "FOREIGN KEY (" + USER_1 + ") REFERENCES " + Table_User.TABLE_NAME + "(" + Table_User.USER_ID + "), "
            + "FOREIGN KEY (" + USER_2 + ") REFERENCES " + Table_User.TABLE_NAME + "(" + Table_User.USER_ID + ")"
            + ")";
}
