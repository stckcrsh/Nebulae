package com.zoom.map;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TagDAO {
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	private String[] allColumns = { Tag.COLUMNS_TAGS.ITEMID.Value(),
		Tag.COLUMNS_TAGS.TAG.Value() };

	public TagDAO(Context _context) {
		dbHelper = new SQLiteHelper(_context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}

	public Tag createTag(String _tag, long _itemId) {
	    ContentValues values = new ContentValues();
	    values.put(Tag.COLUMNS_TAGS.TAG.Value(), _tag);
	    values.put(Tag.COLUMNS_TAGS.ITEMID.Value(), _itemId);
	    long insertId = database.insert(Tag.TABLE_NAME, null,
	        values);
	    Cursor cursor = database.query(Tag.TABLE_NAME,
	        allColumns, Tag.COLUMNS_TAGS.ITEMID.Value() + " = " + _itemId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Tag newTag = cursorToTag(cursor);
	    cursor.close();
	    return newTag;
	}
	
	public List<Tag> getAllTags() {
	    List<Tag> tags = new ArrayList<Tag>();

	    Cursor cursor = database.query(Tag.TABLE_NAME,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Tag tag = cursorToTag(cursor);
	      tags.add(tag);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return tags;
	  }
	
	public List<Tag> getAllTagsByItemId(long _itemId){
		List<Tag> tags = new ArrayList<Tag>();
		Cursor cursor = database.rawQuery("SELECT * FROM "+Tag.TABLE_NAME+" WHERE "+Tag.COLUMNS_TAGS.ITEMID.Value()+" = " +
				_itemId, null);
//		Cursor cursor = database.query(Tag.TABLE_TAGS, allColumns, Tag.COLUMNS_TAGS.ITEMID.Value()+" = "+_itemId, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Tag tag = cursorToTag(cursor);
			tags.add(tag);
			cursor.moveToNext();
		}
		cursor.close();
		return tags;
	}
	
	 public void deleteTag(Tag _tag) {
		long id = _tag.GetItemId();
		String tag = _tag.GetTag();
		System.out.println("Tag deleted with id: " + id);
		
		database.delete(Tag.TABLE_NAME, Tag.COLUMNS_TAGS.ITEMID.Value() + " = "+id+"  AND " +
				Tag.COLUMNS_TAGS.TAG.Value() + " = '"+tag+"'", new String[0]);
	 }

	
	 private Tag cursorToTag(Cursor _cursor) {
		 Tag tag = new Tag();
		 tag.SetItemId(_cursor.getInt(0));
		 tag.SetTag(_cursor.getString(1));
		 return tag;
	 }

	 public void emptyTags(){
		 database.delete(Tag.TABLE_NAME, null, null);
	 }
}
