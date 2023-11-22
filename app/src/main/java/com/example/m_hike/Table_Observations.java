package com.example.m_hike;

public class Table_Observations {
    public static final String TABLE_NAME = "OBSERVATION";
    public static final String OBSERVATION_HIKE_ID = "OBSERVATION_HIKE_ID";
    public static final String OBSERVATION_ID = "OBSERVATION_ID";
    public static final String OBSERVATION_NAME = "OBSERVATION_NAME";
    public static final String OBSERVATION_TIME = "OBSERVATION_TIME";
    public static final String OBSERVATION_COMMENT = "OBSERVATION_COMMENT";
    public static final String OBSERVATION_IMAGE = "OBSERVATION_IMAGE";

    public static final String CREATE_TABLE_OBSERVATION = "CREATE TABLE " + TABLE_NAME + "( "
            + OBSERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OBSERVATION_NAME + " TEXT, "
            + OBSERVATION_TIME + " TEXT, "
            + OBSERVATION_COMMENT + " TEXT, "
            + OBSERVATION_IMAGE + " TEXT, "
            + OBSERVATION_HIKE_ID + " TEXT REFERENCES " + Table_Hike.TABLE_NAME + "(" + OBSERVATION_ID + ")"
            +")";
}
