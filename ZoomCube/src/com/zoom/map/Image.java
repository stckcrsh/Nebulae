package com.zoom.map;

public class Image {
	public enum COLUMNS_IMAGES{
		ID			("id"),
		PATH		("path"),
		NAME		("name"),
		OWNERID		("ownerId"),
		DESCRIPTION	("description"),
		MAINID		("mainId");
		
		private String value;
		public String Value(){return value;}
		
		COLUMNS_IMAGES(String _value){
			value = _value;
		}
	}

	public static final String TABLE_NAME = "images";
	
	public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS '"+TABLE_NAME+"' " +
			"('"+COLUMNS_IMAGES.ID.Value()+"' VARCHAR PRIMARY KEY  NOT NULL  UNIQUE , " +
			"'"+COLUMNS_IMAGES.NAME.Value()+"' VARCHAR NOT NULL , " +
			"'"+COLUMNS_IMAGES.PATH.Value()+"' VARCHAR NOT NULL , " +
			"'"+COLUMNS_IMAGES.DESCRIPTION.Value()+"' VARCHAR NOT NULL , " +
			"'"+COLUMNS_IMAGES.MAINID.Value()+"' VARCHAR ," +
			"'"+COLUMNS_IMAGES.OWNERID.Value()+"' VARCHAR NOT NULL );";
	
	private String name;
	public String GetName(){return name;}
	public void SetName(String _value){name = _value;}
	
	private String ownerId;
	public String GetownerId(){return ownerId;}
	public void SetOwnerId(String _value){ownerId = _value;}
	
	private String path;
	public String GetPath(){return path;}
	public void SetPath(String _value){path = _value;}
	
	private long id;
	public long GetId(){return id;}
	public void SetId(long _value){id = _value;}
	
	private String description;
	public String GetDescription(){return description;}
	public void SetDescription(String _value){description = _value;}
	
	public Image(){
		
	}
	
	public Image(String _name, String _ownerId, String _path, String _description, long _id){
		ownerId = _ownerId;
		path = _path;
		description = _description;
		id = _id;
		name = _name;
	}
}
