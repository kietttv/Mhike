package com.example.m_hike;

public class Table_Share {
    public static final String TABLE_NAME = "SHARE";
    public static final String SHARE_ID = "SHARE_ID";
    public static final String USER_1 = "USER_1";
    public static final String USER_2 = "USER_2";
    public static final String HIKE_ID = "HIKE_ID";
    public static final String NOTIFICATION = "NOTIFICATION";
    public static final String CREATE_TABLE_SHARE = "CREATE TABLE " + TABLE_NAME + " ("
            + SHARE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HIKE_ID + " TEXT NOT NULL, "
            + USER_1 + " INTEGER, "
            + USER_2 + " INTEGER, "
            + NOTIFICATION + " INTEGER, "
            + "FOREIGN KEY (" + HIKE_ID + ") REFERENCES " + Table_Hike.TABLE_NAME + "(" + Table_Hike.HIKE_ID + "), "
            + "FOREIGN KEY (" + USER_1 + ") REFERENCES " + Table_User.TABLE_NAME + "(" + Table_User.USER_ID + "), "
            + "FOREIGN KEY (" + USER_2 + ") REFERENCES " + Table_User.TABLE_NAME + "(" + Table_User.USER_ID + ")"
            + ")";

}
