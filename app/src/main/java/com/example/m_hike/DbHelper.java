package com.example.m_hike;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Hiker_Management_App";
    public static final int DATABASE_VERSION = 4;
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table_User.CREATE_TABLE_USER);
        db.execSQL(Table_Hike.CREATE_TABLE_HIKE);
        db.execSQL(Table_Observations.CREATE_TABLE_OBSERVATION);
        db.execSQL(Table_Relationship.CREATE_TABLE_RELATIONSHIP);
        db.execSQL(Table_Share.CREATE_TABLE_SHARE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Table_User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_Hike.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_Observations.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_Relationship.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_Share.TABLE_NAME);
        onCreate(db);
    }

    public ModelUser getUserByEmail(String email){
        String selectUser = "SELECT * FROM " + Table_User.TABLE_NAME + " WHERE " + Table_User.USER_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectUser, null);
        ModelUser user = null;
        if(cursor.moveToFirst()){
            do {
                user = new ModelUser(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_User.USER_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_DAY_OF_BIRTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_GENDER)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_PASSWORD)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_IMAGE))
                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        return user;
    }

    public ModelUser getUserById(String id){
        String selectUser = "SELECT * FROM " + Table_User.TABLE_NAME + " WHERE " + Table_User.USER_ID + " = '" + id + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectUser, null);
        ModelUser user = null;
        if(cursor.moveToFirst()){
            do {
                user = new ModelUser(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_User.USER_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_DAY_OF_BIRTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_GENDER)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_PASSWORD)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_IMAGE))
                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        return user;
    }

    public ArrayList<ModelUser> getAllUser(String orderBy){
        ArrayList<ModelUser> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Table_User.TABLE_NAME+" ORDER BY "+orderBy;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                ModelUser user = new ModelUser(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_User.USER_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_DAY_OF_BIRTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_GENDER)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_PASSWORD)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_IMAGE))
                );
                users.add(user);
            }while (cursor.moveToNext());
        }
        return users;
    }

    public long insertUser(String email, String name, String dayOfBirth, String gender, String password, String image){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Table_User.USER_EMAIL,email);
        contentValues.put(Table_User.USER_NAME,name);
        contentValues.put(Table_User.USER_DAY_OF_BIRTH,dayOfBirth);
        contentValues.put(Table_User.USER_GENDER,gender);
        contentValues.put(Table_User.USER_PASSWORD,password);
        contentValues.put(Table_User.USER_IMAGE,image);
        long id = db.insert(Table_User.TABLE_NAME,null,contentValues);

        db.close();
        return id;
    }

    public void updateUser(String id, String name, String email, String dayOfBirth, String gender, String password, String image){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Table_User.USER_EMAIL,email);
        contentValues.put(Table_User.USER_NAME,name);
        contentValues.put(Table_User.USER_DAY_OF_BIRTH,dayOfBirth);
        contentValues.put(Table_User.USER_GENDER,gender);
        contentValues.put(Table_User.USER_PASSWORD,password);
        contentValues.put(Table_User.USER_IMAGE,image);
        db.update(Table_User.TABLE_NAME,contentValues, Table_User.USER_ID+" =? ",new String[]{id} );

        db.close();
    }

    public void deleteUserById(String id){
        SQLiteDatabase db =  getWritableDatabase();
        db.delete(Table_Hike.TABLE_NAME, Table_Hike.HIKE_USER_ID + " =? ", new String[]{id});
        db.delete(Table_User.TABLE_NAME, Table_User.USER_ID + " =? ", new String[]{id} );
        db.close();
    }

    public void deleteAllUser(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Table_Hike.TABLE_NAME);
        db.execSQL("DELETE FROM " + Table_User.TABLE_NAME);
        db.close();
    }

    public String checkPass(String email){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Table_User.TABLE_NAME +
                " WHERE " + Table_User.USER_EMAIL + " = '" +  email  + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        ModelUser user = null;
        if(cursor.moveToFirst()){
            do {
                user = new ModelUser(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_User.USER_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_DAY_OF_BIRTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_GENDER)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_PASSWORD)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_IMAGE))
                );
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user.getUser_password().toString();
    }

    public boolean checkEmail(String email){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Table_User.TABLE_NAME +
                " WHERE " + Table_User.USER_EMAIL + " = '" +  email  + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<ModelUser> findFriend(String id, String query){
        ArrayList<ModelUser> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Table_User.TABLE_NAME +
                " WHERE (" + Table_User.USER_EMAIL + " LIKE '%" + query + "%'" +
                " OR " + Table_User.USER_NAME + " LIKE '%" + query + "%')" +
                " AND " + Table_User.USER_ID + " != " + id ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                ModelUser user = new ModelUser(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_User.USER_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_DAY_OF_BIRTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_GENDER)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_PASSWORD)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_User.USER_IMAGE))
                );
                users.add(user);
            }while (cursor.moveToNext());
        }
        return users;
    }

    public String getRelationshipStatus(String user1Id, String user2Id) {
        SQLiteDatabase db = getReadableDatabase();
        String status = "unknown"; // default status

        String selectQuery = "SELECT " + Table_Relationship.STATUS + " FROM " + Table_Relationship.TABLE_NAME +
                " WHERE (" + Table_Relationship.USER_1 + " = ? AND " + Table_Relationship.USER_2 + " = ?)";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{user1Id, user2Id});

        if (cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndexOrThrow(Table_Relationship.STATUS));
        }

        cursor.close();
        db.close();
        return status;
    }

    public long insertRelationship(String user1, String user2, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Table_Relationship.USER_1, user1);
        contentValues.put(Table_Relationship.USER_2, user2);
        contentValues.put(Table_Relationship.STATUS, status);

        long id = db.insert(Table_Relationship.TABLE_NAME, null, contentValues);

        db.close();
        return id;
    }

    public void updateRelationship(String id, String user1, String user2, String status ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table_Relationship.USER_1, user1);
        contentValues.put(Table_Relationship.USER_2, user2);
        contentValues.put(Table_Relationship.STATUS, status);
        db.update(Table_Relationship.TABLE_NAME, contentValues, Table_Relationship.RELATIONSHIP_ID + " =? ", new String[]{id});
    }

    public void deleteRelationship( String user1Id, String user2Id){
        SQLiteDatabase db =  getWritableDatabase();

        db.delete(Table_Relationship.TABLE_NAME, Table_Relationship.USER_1 + " =? " + " AND " + Table_Relationship.USER_2 + "=? ", new String[]{user1Id, user2Id} );
        db.close();
    }

    public ModelRelationship getRelationship(String userId1, String userId2) {
        ModelRelationship relationship = new ModelRelationship("0", "0", "0", "unknow");
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Table_Relationship.TABLE_NAME +
                " WHERE " + Table_Relationship.USER_1 + " =? " +
                " AND " + Table_Relationship.USER_2 + " =? ";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{userId1, userId2});

        if (cursor.moveToFirst()) {
            do {
                relationship = new ModelRelationship(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Table_Relationship.RELATIONSHIP_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Table_Relationship.USER_1)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Table_Relationship.USER_2)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Table_Relationship.STATUS))
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return relationship;
    }

    public ArrayList<ModelRelationship> getAllRelationships(String userId, String re) {
        ArrayList<ModelRelationship> friendRelationships = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Table_Relationship.TABLE_NAME +
                " WHERE " + Table_Relationship.USER_1 + " = " + userId +
                " OR " + Table_Relationship.USER_2 + " = " + userId;

        if(re.equals("Friend")){
            selectQuery = "SELECT * FROM " + Table_Relationship.TABLE_NAME +
                    " WHERE (" + Table_Relationship.USER_1 + " = ? " + ") AND " + Table_Relationship.STATUS + " = 'Friend'";
        }
        if (re.equals("Waiting")){
            selectQuery = "SELECT * FROM " + Table_Relationship.TABLE_NAME +
                    " WHERE (" + Table_Relationship.USER_1 + " = ? " + ") AND " + Table_Relationship.STATUS + " = 'Waiting'";
        }
        if (re.equals("Request")){
            selectQuery = "SELECT * FROM " + Table_Relationship.TABLE_NAME +
                    " WHERE (" + Table_Relationship.USER_1 + " = ? " + ") AND " + Table_Relationship.STATUS + " = 'Request'";
        }
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userId});

        if (cursor.moveToFirst()) {
            do {
                ModelRelationship relationship = new ModelRelationship(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Table_Relationship.RELATIONSHIP_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Table_Relationship.USER_1)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Table_Relationship.USER_2)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Table_Relationship.STATUS))
                );
                friendRelationships.add(relationship);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return friendRelationships;
    }

    public ArrayList<ModelShare> getAllShare(String user1Id, String user2Id) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ModelShare> arrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Table_Share.TABLE_NAME +
                " WHERE (" + Table_Share.USER_1 + " = ? AND " + Table_Share.USER_2 + " = ?) "
                + " ORDER BY " + Table_Share.SHARE_ID + " DESC";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{user1Id, user2Id});

        try {
            if (cursor.moveToFirst()) {
                do {
                    ModelShare modelShare = new ModelShare(
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.SHARE_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.USER_1)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.USER_2)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.HIKE_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.NOTIFICATION))
                    );
                    arrayList.add(modelShare);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return arrayList;
    }

    public long insertShare(String user1, String user2, String idHike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Table_Share.USER_1, user1);
        contentValues.put(Table_Share.USER_2, user2);
        contentValues.put(Table_Share.HIKE_ID, idHike);
        contentValues.put(Table_Share.NOTIFICATION, 1);

        long id = db.insert(Table_Share.TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public void updateShare(String user1, String user2, String idHike, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table_Share.USER_1, user1);
        contentValues.put(Table_Share.USER_2, user2);
        contentValues.put(Table_Share.HIKE_ID, idHike);
        contentValues.put(Table_Share.NOTIFICATION, 0);
        db.update(Table_Share.TABLE_NAME, contentValues, Table_Share.SHARE_ID + " =? ", new String[]{id});

    }

    public Integer countShare(String user1, String user2) {
        ArrayList<ModelShare> arrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Table_Share.TABLE_NAME +
                " WHERE (" + Table_Share.USER_1 + " = ? AND "
                + Table_Share.USER_2 + " = ? AND "
                + Table_Share.NOTIFICATION + " = " + 1 + ")" ;
        Integer count = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user1, user2});

        try {
            if (cursor.moveToFirst()) {
                do {
                    ModelShare modelShare = new ModelShare(
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.SHARE_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.USER_1)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.USER_2)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.HIKE_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(Table_Share.NOTIFICATION))
                    );
                    arrayList.add(modelShare);
                    count++;
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return count;
    }

    public ArrayList<ModelHike> getAllData(String orderBy, String userId){
        ArrayList<ModelHike> arrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Table_Hike.TABLE_NAME + " WHERE " + Table_Hike.HIKE_USER_ID + " = " + userId + " ORDER BY " + orderBy;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                ModelHike modelHike = new ModelHike(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_USER_ID)),//add user id
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LOCATION)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_DATE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_PARKING)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LENGTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LEVEL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_DESCRIPTION))
                );
                arrayList.add(modelHike);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    public ModelHike getHikeById(String Id){
        ModelHike modelHike = new ModelHike("0","0","0","0","0","0","0","0","0" );
        String selectQuery = "SELECT * FROM " + Table_Hike.TABLE_NAME + " WHERE " + Table_Hike.HIKE_ID + " = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                modelHike = new ModelHike(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_USER_ID)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LOCATION)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_DATE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_PARKING)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LENGTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LEVEL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_DESCRIPTION))
                );
            }while (cursor.moveToNext());
        }
        db.close();
        return modelHike;
    }

    public long insertHike(String hike_user_id, String hike_name, String hike_location,String hike_date,String hike_parking,
                           String hike_length,String hike_level,String hike_description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Table_Hike.HIKE_USER_ID, hike_user_id);
        contentValues.put(Table_Hike.HIKE_NAME,hike_name);
        contentValues.put(Table_Hike.HIKE_LOCATION,hike_location);
        contentValues.put(Table_Hike.HIKE_DATE,hike_date);
        contentValues.put(Table_Hike.HIKE_PARKING,hike_parking);
        contentValues.put(Table_Hike.HIKE_LENGTH,hike_length);
        contentValues.put(Table_Hike.HIKE_LEVEL,hike_level);
        contentValues.put(Table_Hike.HIKE_DESCRIPTION,hike_description);

        long id = db.insert(Table_Hike.TABLE_NAME,null,contentValues);

        db.close();

        return id;
    }

    public void updateHike(String id, String hike_name, String hike_location,String hike_date,String hike_parking,
                           String hike_length,String hike_level,String hike_description){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Table_Hike.HIKE_NAME,hike_name);
        contentValues.put(Table_Hike.HIKE_LOCATION,hike_location);
        contentValues.put(Table_Hike.HIKE_DATE,hike_date);
        contentValues.put(Table_Hike.HIKE_PARKING,hike_parking);
        contentValues.put(Table_Hike.HIKE_LENGTH,hike_length);
        contentValues.put(Table_Hike.HIKE_LEVEL,hike_level);
        contentValues.put(Table_Hike.HIKE_DESCRIPTION,hike_description);

        db.update(Table_Hike.TABLE_NAME,contentValues, Table_Hike.HIKE_ID+" =? ",new String[]{id} );

        db.close();
    }

    public void deleteHikeById(String id){
        SQLiteDatabase db =  getWritableDatabase();

        db.delete(Table_Observations.TABLE_NAME, Table_Observations.OBSERVATION_HIKE_ID + " =? ", new String[]{id});
        db.delete(Table_Share.TABLE_NAME, Table_Share.HIKE_ID + " =? ", new String[]{id});
        db.delete(Table_Hike.TABLE_NAME, Table_Hike.HIKE_ID + " =? ", new String[]{id} );
        db.close();
    }

    public void deleteAllHike(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Table_Observations.TABLE_NAME);
        db.execSQL("DELETE FROM " + Table_Share.TABLE_NAME);
        db.execSQL("DELETE FROM " + Table_Hike.TABLE_NAME);
        db.close();
    }

    public ArrayList<ModelHike> getSearchHike(String query){

        ArrayList<ModelHike> arrayList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String queryToSearch = "SELECT * FROM "+ Table_Hike.TABLE_NAME+" WHERE ("
                + Table_Hike.HIKE_NAME + " LIKE '%" +query+"%'" +" OR "
                + Table_Hike.HIKE_DATE + " LIKE '%" +query+"%'" +" OR "
                + Table_Hike.HIKE_LOCATION + " LIKE '%" +query+"%'" +" OR "
                + Table_Hike.HIKE_LENGTH + " LIKE '%" +query+"%'" +" )";

        Cursor cursor = db.rawQuery(queryToSearch,null);

        if (cursor.moveToFirst()){
            do {
                ModelHike modelHike = new ModelHike(
                        // only id is integer type
                        //
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_USER_ID)),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LOCATION)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_DATE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_PARKING)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LENGTH)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_LEVEL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Hike.HIKE_DESCRIPTION))
                );
                arrayList.add(modelHike);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    public ArrayList<ModelObservation> getAllObserData(String idHike, String orderBy){

        ArrayList<ModelObservation> arrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ Table_Observations.TABLE_NAME + " WHERE " + Table_Observations.OBSERVATION_HIKE_ID + " = " + idHike +" ORDER BY "+ orderBy;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                ModelObservation modelObser = new ModelObservation(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_HIKE_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_COMMENT)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_IMAGE))
                );
                arrayList.add(modelObser);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    public long insertObser(String hike_id, String obser_name,
                            String obser_time, String obser_comment, String obser_image){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Table_Observations.OBSERVATION_HIKE_ID,hike_id);
        contentValues.put(Table_Observations.OBSERVATION_NAME,obser_name);
        contentValues.put(Table_Observations.OBSERVATION_TIME,obser_time);
        contentValues.put(Table_Observations.OBSERVATION_COMMENT, obser_comment);
        contentValues.put(Table_Observations.OBSERVATION_IMAGE,obser_image);

        long id = db.insert(Table_Observations.TABLE_NAME,null,contentValues);

        db.close();

        return id;
    }

    public void updateObser(String obser_id, String obser_name,
                            String obser_time, String obser_comment, String obser_image){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Table_Observations.OBSERVATION_NAME, obser_name);
        contentValues.put(Table_Observations.OBSERVATION_TIME, obser_time);
        contentValues.put(Table_Observations.OBSERVATION_COMMENT, obser_comment);
        contentValues.put(Table_Observations.OBSERVATION_IMAGE, obser_image);

        db.update(Table_Observations.TABLE_NAME,contentValues, Table_Observations.OBSERVATION_ID+" =? ",new String[]{obser_id} );

        db.close();
    }

    public void deleteObserById(String id){
        SQLiteDatabase db =  getWritableDatabase();

        db.delete(Table_Observations.TABLE_NAME, Table_Observations.OBSERVATION_ID + " =? ", new String[]{id} );
        db.close();
    }

    public void deleteAllObser(String id){
        SQLiteDatabase db = getWritableDatabase();

        db.delete(Table_Observations.TABLE_NAME, Table_Observations.OBSERVATION_HIKE_ID + " =? ", new String[]{id} );
        db.close();
    }

    public ArrayList<ModelObservation> getSearchObser(String idHike, String query){

        ArrayList<ModelObservation> arrayList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String queryToSearch = "SELECT * FROM "+ Table_Observations.TABLE_NAME+" WHERE ("
                + Table_Observations.OBSERVATION_NAME + " LIKE '%" +query+"%'" +" AND "
                + Table_Observations.OBSERVATION_HIKE_ID + " = " +idHike +" )";

        Cursor cursor = db.rawQuery(queryToSearch,null);

        if (cursor.moveToFirst()){
            do {
                ModelObservation modelObser = new ModelObservation(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_HIKE_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_COMMENT)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Table_Observations.OBSERVATION_IMAGE))
                );
                arrayList.add(modelObser);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
}
