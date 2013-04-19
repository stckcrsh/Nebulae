package com.zoom.map;

public interface TagListener {
	public void AddTag(String _tag, long _itemId);
	public void DeleteTag(String _tag, long _itemId);
	public void ReturnResults();
}
