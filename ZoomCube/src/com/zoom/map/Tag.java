package com.zoom.map;


public class Tag {
	
	public enum COLUMNS_TAGS{
		TAG 		("tag"),
		ITEMID		("itemId");
		
		private String value;
		public String Value(){return value;}
		
		COLUMNS_TAGS(String _value){
			value = _value;
		}
	}	
	
	public static final String TABLE_NAME = "tags";
	
	public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "' ('" + 
			COLUMNS_TAGS.ITEMID.Value() + "' INTEGER NOT NULL , " +
			"'" + COLUMNS_TAGS.TAG.Value() + "' VARCHAR NOT NULL , " +
			"PRIMARY KEY ('" + COLUMNS_TAGS.ITEMID.Value() + "', '" + COLUMNS_TAGS.TAG.Value()+"'));";
	
	private String tag;
	public String GetTag(){return tag;}
	public void SetTag(String _value){tag = _value;}
	
	private long itemId;
	public long GetItemId(){return itemId;}
	public void SetItemId(long _value){itemId = _value;}	
	
	public Tag(){
		
	}
	public Tag(String _tag, long _itemId){
		tag = _tag;
		itemId = _itemId;
	}
}
