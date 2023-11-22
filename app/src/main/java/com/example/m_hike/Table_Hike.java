package com.example.m_hike;

public class Table_Hike {
    public static final String TABLE_NAME = "HIKE";
    public static final String HIKE_USER_ID = "HIKE_USER_ID";
    public static final String HIKE_ID = "HIKE_ID";
    public static final String HIKE_NAME = "HIKE_NAME";
    public static final String HIKE_LOCATION = "HIKE_LOCATION";
    public static final String HIKE_DATE = "HIKE_DATE";
    public static final String HIKE_PARKING = "HIKE_PARKING";
    public static final String HIKE_LENGTH = "HIKE_LENGTH";
    public static final String HIKE_LEVEL = "HIKE_LEVEL";
    public static final String HIKE_DESCRIPTION = "HIKE_DESCRIPTION";

    public static final String CREATE_TABLE_HIKE = "CREATE TABLE " + TABLE_NAME + "( "
            + HIKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HIKE_NAME + " TEXT, "
            + HIKE_LOCATION + " TEXT, "
            + HIKE_DATE + " TEXT, "
            + HIKE_PARKING + " TEXT, "
            + HIKE_LENGTH + " TEXT, "
            + HIKE_LEVEL + " TEXT, "
            + HIKE_DESCRIPTION + " TEXT, "
            + HIKE_USER_ID + " TEXT REFERENCES " + Table_User.TABLE_NAME + "(" + HIKE_ID + ")"//crate references key
            + " )";
}