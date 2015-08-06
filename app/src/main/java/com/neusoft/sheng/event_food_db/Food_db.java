package com.neusoft.sheng.event_food_db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neusoft.sheng.event_food_util.Food_info;

import java.util.ArrayList;


public class Food_db
{
	private String databasePath;
	private SQLiteDatabase database;

	DBTool dbTool;

	public Food_db(DBTool dbTool)
	{
		this.dbTool=dbTool;
	}


	public ArrayList<Food_info> getFood()
	{
		database=dbTool.openDatabase();
		ArrayList<Food_info>food_infos=new ArrayList<Food_info>();
		Cursor cursor= database.query("food_info", null, null, null, null, null,null);
		while (cursor.moveToNext())
		{
			Food_info food_info=new Food_info();
			food_info.setFood_id(cursor.getString(0));
			food_info.setFood_name(cursor.getString(1));
			food_info.setFood_mater(cursor.getString(2));
			food_info.setFood_price(cursor.getString(3));
			food_info.setShop_id(cursor.getString(4));
			food_info.setFood_pic(cursor.getString(5));
			food_info.setFood_sell(cursor.getString(6));
			food_infos.add(food_info);
		}
		dbTool.closeDatabase();
		return food_infos;
	}



	public ArrayList<Food_info> getFood(String shop_id)
	{
		database=dbTool.openDatabase();
		ArrayList<Food_info>food_infos=new ArrayList<Food_info>();

		Cursor cursor= database.query("food_info", null, "shop_idtype=?", new String[]{shop_id}, null, null,null);
		while (cursor.moveToNext())
		{
			Food_info food_info=new Food_info();
			food_info.setFood_id(cursor.getString(0));
			food_info.setFood_name(cursor.getString(1));
			food_info.setFood_mater(cursor.getString(2));
			food_info.setFood_price(cursor.getString(3));
			food_info.setShop_id(cursor.getString(4));
			food_info.setFood_pic(cursor.getString(5));
			food_info.setFood_sell(cursor.getString(6));
			food_infos.add(food_info);
		}
		dbTool.closeDatabase();
		return food_infos;
	}



	public Food_info getFoodByfood_id(String food_id)
	{
		database=dbTool.openDatabase();

		Food_info food_info=new Food_info();
		Cursor cursor= database.query("food_info", null, "food_id=?", new String[]{food_id}, null, null,null);
if(cursor.getCount()>0)
{

	cursor.moveToNext();




	food_info.setFood_id(cursor.getString(0));
	food_info.setFood_name(cursor.getString(1));
	food_info.setFood_mater(cursor.getString(2));
	food_info.setFood_price(cursor.getString(3));
	food_info.setShop_id(cursor.getString(4));
	food_info.setFood_pic(cursor.getString(5));
	food_info.setFood_sell(cursor.getString(6));
}

		dbTool.closeDatabase();
		//System.out.println("flag--" + cursor.getCount() + "--" + food_id + "--" + food_info.getFood_name());
		return food_info;
	}


}
