package com.zoom.map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "ZoomCubeDB";
	public static final int DATABASE_VERSION = 1;
			
	public SQLiteHelper(Context _context) {
		super(_context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase _database) {
		// TODO Auto-generated method stub
		//_database.execSQL("DELETE * FROM '"+TABLE_TAGS+"'");
		_database.execSQL(Tag.CREATE_TABLE);
		_database.execSQL(Image.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase _database, int _old, int _new) {
		// TODO Auto-generated method stub
		Log.w(SQLiteHelper.class.getName(),
		        "Upgrading database from version " + _old + " to "
		            + _new + ", which will destroy all old data");
		_database.execSQL("DROP TABLE IF EXISTS " + Tag.TABLE_NAME);
		_database.execSQL("DROP TABLE IF EXISTS " + Image.TABLE_NAME);
		onCreate(_database);
	}
}
