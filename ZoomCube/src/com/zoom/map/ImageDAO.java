package com.zoom.map;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ImageDAO {
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	private String[] allColumns = { Image.COLUMNS_IMAGES.ID.Value(),
		Image.COLUMNS_IMAGES.DESCRIPTION.Value(),  Image.COLUMNS_IMAGES.NAME.Value(),
		Image.COLUMNS_IMAGES.OWNERID.Value(), Image.COLUMNS_IMAGES.PATH.Value()};

	public ImageDAO(Context _context) {
		dbHelper = new SQLiteHelper(_context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	
	public Image insertImage(String _description, String _name, String _ownerId, String _path){
		ContentValues values = new ContentValues();
		values.put(Image.COLUMNS_IMAGES.DESCRIPTION.Value(), _description);
		values.put(Image.COLUMNS_IMAGES.NAME.Value(), _name);
		values.put(Image.COLUMNS_IMAGES.OWNERID.Value(), _ownerId);
		values.put(Image.COLUMNS_IMAGES.PATH.Value(), _path);
		long insertId = database.insert(Image.TABLE_NAME, null, values);
		Cursor cursor = database.query(Image.TABLE_NAME, allColumns, 
				Image.COLUMNS_IMAGES.ID.Value() + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Image newImage = cursorToImage(cursor);
		cursor.close();
		return newImage;
	}
	
	public List<Image> getAllimages() {
	    List<Image> images = new ArrayList<Image>();

	    Cursor cursor = database.query(Image.TABLE_NAME,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Image image = cursorToImage(cursor);
	    	images.add(image);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return images;
	}
	
	public void deleteImage(Image _image) {
	 }

	private Image cursorToImage(Cursor _cursor) {
		// TODO Auto-generated method stub
		return null;
	}

}
