package com.neusoft.sheng.event_food_util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neusoft.sheng.event_food_db.DBTool;


public class Util {


    public static String isLogin() {
        SQLiteDatabase database;
        DBTool dbTool = new DBTool(BaseApplicationInterface.databasePath);
        database = dbTool.openDatabase();

        Cursor cursor = database.query("user_info", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            return cursor.getString(1);
        }
        return null;
    }


    public static Long Login(String user_name, String user_pwd) {
        SQLiteDatabase database;
        DBTool dbTool = new DBTool(BaseApplicationInterface.databasePath);
        database = dbTool.openDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", user_name);
        values.put("user_pwd", user_pwd);
        return  database.insert("user_info", null, values);
    }


    public static long exitLogin(String user_name) {
        SQLiteDatabase database;
        DBTool dbTool = new DBTool(BaseApplicationInterface.databasePath);
        database = dbTool.openDatabase();
      return   database.delete("user_info", "user_name=?", new String[]{user_name});

    }


}
