package com.xogrp.tkgz.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hliao on 6/7/2016.
 */
public class AddrSearchDatabaseUtil {
    private static SQLiteDatabase sDb;
    private static Cursor sCursor;
    private static boolean sIsDbInit;


    public  static void  initDatabase(Context context){
        if(sIsDbInit){
            return ;
        }
        else {
            sDb = context.openOrCreateDatabase("searchRecord.db", Context.MODE_PRIVATE, null);
            String sql = "create table if not exists searchedRecord (addrName varchar(64) primary key,addrDetail varchar(128),latitude double,longitude double)";
            sDb.execSQL(sql);
            sIsDbInit=true;
        }
    }

    public static SQLiteDatabase getDatabase(Context context){
        initDatabase(context);
        return sDb;
    }

    public static Cursor getSearchRecord(Context context){
        initDatabase(context);
        sCursor = sDb.query("searchedRecord", new String[]{"addrName", "addrDetail", "latitude", "longitude"}, null, null, null, null, null);
        return sCursor;
    }

    public static void insertIntoTable(Context context,String addrName,String addrDetail,double latitude,double longitude){
        initDatabase(context);
        ContentValues values=new ContentValues();
        values.put("addrName", addrName);
        values.put("addrDetail", addrDetail);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        sDb.insert("searchedRecord", null, values);
    }


    public static void DeleteTable(Context context){
        initDatabase(context);
        sDb.delete("searchedRecord", null, null);
    }

    public static void closeDatabase(){
        sIsDbInit=false;
        sDb.close();
        sCursor.close();
    }

}
