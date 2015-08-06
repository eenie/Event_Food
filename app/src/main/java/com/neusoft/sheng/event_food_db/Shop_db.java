package com.neusoft.sheng.event_food_db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.neusoft.sheng.event_food_util.Food_info;
import com.neusoft.sheng.event_food_util.Shop_info;

import java.util.ArrayList;


public class Shop_db
{
	private String databasePath;
	private SQLiteDatabase database;

	DBTool dbTool;

	public Shop_db(DBTool dbTool)
	{

		this.dbTool=dbTool;

	}


	public ArrayList<Shop_info> getShop()
	{
		database=dbTool.openDatabase();
		ArrayList<Shop_info>Shop_infos=new ArrayList<Shop_info>();
		Cursor cursor=
		 database.query("shop_info", null, null, null, null, null,
				null);
		while (cursor.moveToNext())
		{
			Shop_info shop_info=new Shop_info();
			shop_info.setShop_id(cursor.getString(0));
			shop_info.setShop_name(cursor.getString(1));
			shop_info.setShop_valueation(cursor.getString(2));
			shop_info.setShop_sell(cursor.getString(3));
			shop_info.setShop_ad(cursor.getString(4));
			shop_info.setShop_head(cursor.getString(5));
			Shop_infos.add(shop_info);
		}
		dbTool.closeDatabase();
		return Shop_infos;
	}


	public String getShop_phoneByShop_id(String shop_id)
	{
		database=dbTool.openDatabase();
	Cursor cursor=	database.query("shop_info", null, "shop_id=?", new String[]{shop_id}, null, null,
			null);

		System.out.println(cursor.getCount());
		cursor.moveToNext();


		return cursor.getString(6);
	}

}
