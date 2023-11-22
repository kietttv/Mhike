package com.example.m_hike;

public class Table_User {
    public static final String TABLE_NAME = "USER";
    public static final String USER_ID = "USER_ID";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_DAY_OF_BIRTH = "USER_DAY_OF_BIRTH";
    public static final String USER_GENDER = "USER_GENDER";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_IMAGE = "USER_IMAGE";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME + "( "
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_EMAIL + " TEXT, "
            + USER_NAME + " TEXT, "
            + USER_DAY_OF_BIRTH + " TEXT, "
            + USER_GENDER + " TEXT, "
            + USER_IMAGE + " TEXT, "
            + USER_PASSWORD + " TEXT"
            + " )";
}