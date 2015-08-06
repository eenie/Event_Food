package com.neusoft.sheng.event_food_db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBTool
{
	private String databasePath;
	private SQLiteDatabase database;


	public DBTool(String databasePath)
	{

		this.databasePath = databasePath;
	}

	public SQLiteDatabase openDatabase()
	{

		if (database == null || !database.isOpen())
		{
			database = SQLiteDatabase.openDatabase(databasePath, null,
					SQLiteDatabase.OPEN_READWRITE);

		}

		return database;
	}


	public void closeDatabase()
	{
		if (database != null || database.isOpen())
		{
			database.close();
		}
	}

}
