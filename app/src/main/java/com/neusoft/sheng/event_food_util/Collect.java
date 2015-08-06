package com.neusoft.sheng.event_food_util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.neusoft.sheng.event_food.Login_Activity;
import com.neusoft.sheng.event_food_db.DBTool;

import java.util.ArrayList;

public class Collect {
    String food_id;
    private SQLiteDatabase database;
    Context context;
    public CoordinatorLayout rootLayout;


    public Collect() {
        DBTool dbTool = new DBTool(BaseApplicationInterface.databasePath);
        database = dbTool.openDatabase();
    }


    public Collect(String food_id, Context context) {
        this.food_id = food_id;
        this.context = context;
        DBTool dbTool = new DBTool(BaseApplicationInterface.databasePath);
        database = dbTool.openDatabase();
    }

    public Collect(String food_id, Context context, CoordinatorLayout rootLayout) {
        this.food_id = food_id;
        this.context = context;
        this.rootLayout = rootLayout;
        DBTool dbTool = new DBTool(BaseApplicationInterface.databasePath);
        database = dbTool.openDatabase();
    }


    public void delCollect() {

        Cursor cursor = database.query("user_info", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            System.out.println(cursor.getString(0));
            if (database.delete("collect_info","food_id=?",new String[]{food_id}) > 0)
            {
                Toast.makeText(context, "删除成功!", Toast.LENGTH_LONG).show();
            }
            }
        database.close();
    }



    public long addCollect() {

       String user_name=Util.isLogin();
//System.out.println(user_name);

        if (user_name!=null) {


            if (database.query("collect_info", null, "food_id=? and user_name=?", new String[]{food_id,user_name}, null, null, null).getCount() > 0)

            {

                if (rootLayout != null) {

                    Snackbar.make(rootLayout, "已经收藏过了哟", Snackbar.LENGTH_LONG)
                            .setAction("清除", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                } else {

                    Toast.makeText(context, "已经收藏过了哟！", Toast.LENGTH_LONG).show();
                }
            } else {


                ContentValues values = new ContentValues();
                values.put("user_name", user_name);
                values.put("food_id", food_id);

                if (rootLayout != null) {

                    Snackbar.make(rootLayout, "收藏成功!", Snackbar.LENGTH_LONG)
                            .setAction("清除", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                } else {

                    Toast.makeText(context, "收藏成功!", Toast.LENGTH_LONG).show();
                }

                return database.insert("collect_info", null, values);


            }


        } else {


            Intent intent = new Intent(context,
                    Login_Activity.class);
            context.startActivity(intent);
        }

        database.close();
        return 0;
    }



    public  ArrayList<String> getCollect_food_idByuserid(String user_name){

        ArrayList<String>food_id=new ArrayList<String>();
        Cursor cursor=database.query("collect_info", null, "user_name=?", new String[]{user_name}, null, null, null);
        while (cursor.moveToNext())
        {

            food_id.add(cursor.getString(2));
        }
        database.close();
        return food_id;
    }





}
