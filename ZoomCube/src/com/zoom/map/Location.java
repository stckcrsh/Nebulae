package com.zoom.map;

public class Location {
	
	public enum COLUMNS_LOCATIONS{
		ITEMID			("itemId"),
		LONGITUDE		("longitude"),
		LATTITUDE		("lattitude"),
		TIMESTAMP		("timestamp");
		
		private String value;
		public String Value(){return value;}
		
		COLUMNS_LOCATIONS(String _value){
			value = _value;
		}
	}	
	
	public static final String TABLE_NAME = "locations";
	
	public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"' " +
			"('"+COLUMNS_LOCATIONS.ITEMID.Value()+"' INTEGER PRIMARY KEY  NOT NULL  UNIQUE , " +
			"'"+COLUMNS_LOCATIONS.LONGITUDE.Value()+"' DOUBLE NOT NULL , " +
			"'"+COLUMNS_LOCATIONS.LATTITUDE.Value()+"' DOUBLE NOT NULL , " +
			"'"+COLUMNS_LOCATIONS.TIMESTAMP.Value()+"' DATETIME NOT NULL );";
	
	private long itemId;
	public long GetItemId(){return itemId;}
	public void SetItemId(long _itemId){itemId = _itemId;}
}