package com.zoom.map;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.zoom.map.R;

public class TagDialog extends Dialog implements TagListener{
	private EditText eText;
	private Button okButton;
	private Button doneButton;

	private ArrayList<String> tags;
	public ArrayList<String> getTags(){return tags;	}
	
	private TagDialogListener listener;
	private Context context;
	private long itemId;
	
	public TagDialog(int _itemId, Context _context, TagDialogListener _listener) {
		super(_context);
		// TODO Auto-generated constructor stub
		listener = _listener;
		context = _context;
		itemId = _itemId;
		
		tags = new ArrayList<String>();	
		
	}

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		this.setTitle("@string/edit_tags");
		this.setContentView(R.layout.tag_dialog);
		
		//first we need to load our tags from the database if there are any
		TagDAO tDAO = new TagDAO(context);
		tDAO.open();
		System.out.println("COLUMN NUMBER :"+itemId);
		List<Tag> currentTags = tDAO.getAllTagsByItemId(itemId);
		
		if(currentTags.size()>0){
			for (Tag tag : currentTags){
				tags.add(tag.GetTag());
				this.AddTag(tag.GetTag(), tag.GetItemId());
			}
		}else {
			System.out.println("EMPTY EMPTY");
		}
		
		tDAO.close();

		eText = (EditText)this.findViewById(R.id.tag_eText);

		okButton = (Button)this.findViewById(R.id.buttonOK);
		doneButton = (Button)this.findViewById(R.id.buttonDone);

			

		eText.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
				   if (actionId == EditorInfo.IME_NULL  
				      && event.getAction() == KeyEvent.ACTION_DOWN) { 
				      Button okButton = (Button)findViewById(R.id.buttonOK);
				      //TODO fix this callOnClick cause this doesnt work in certain API's
				      okButton.callOnClick();
				   }
				   return true;
				}

		});


		okButton.setOnClickListener(new View.OnClickListener(){

			private Context context;
			private TagListener listener;
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String tagsText = eText.getText().toString().replaceAll("\\W", "").trim();
				if(tagsText != null){
					if( tagsText != ""){

						tags.add(tagsText);
						listener.AddTag(tagsText, itemId);
						
						eText.setText("");
					} else {
						//alert that they left the tag blank 
						eText.setText("");
					}
				} else {
					//alert that they had an invalid entry
					eText.setText("");
				}
			}
			
			public View.OnClickListener init(Context _context, TagListener _listener, long _itemId){
				context = _context;
				listener = _listener;
				itemId = _itemId;
				return this;
			}
		}.init(context, this, itemId));

		doneButton.setOnClickListener(new View.OnClickListener(){

			private TagListener listener;
			
			public View.OnClickListener init(TagListener _listener){
				listener = _listener;
				return this;
			}
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				eText.setOnEditorActionListener(new OnEditorActionListener(){

					@Override
					public boolean onEditorAction(TextView arg0,
							int arg1, KeyEvent arg2) {
						// TODO Auto-generated method stub
						return false;
					}

				});
				String tagsText = eText.getText().toString().trim().replaceAll("\\W", "").trim();
				if(tagsText != null && tagsText != ""){
					tags.add(tagsText);
					

					if(tags.size()>0){
						
						listener.ReturnResults();
						dismiss();
					}
				} else {
					
					listener.ReturnResults();
					dismiss();
				}
			}
			
			

		}.init(this));
	}

	@Override
	public void AddTag(String _tag, long _itemId) {
		// TODO Auto-generated method stub
		Button btn = new Button(context);
		LinearLayout ll = (LinearLayout)this.findViewById(R.id.tag_layout);
		btn.setText(_tag);
		btn.setOnClickListener(new View.OnClickListener(){
			
			private Button btn;
			private LinearLayout ll;
			private TagListener listener;
			private long itemId;
			
			public View.OnClickListener init(Button _btn, LinearLayout _layout, TagListener _listener, long _itemId){
				btn = _btn;
				ll = _layout;
				listener = _listener;
				itemId = _itemId;
				return this;
			}
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tags.remove(btn.getText());
				listener.DeleteTag(btn.getText().toString(), itemId);
				ll.removeView(btn);
			}
			
		}.init(btn, ll, this, _itemId));
		ll.addView(btn);
	}

	@Override
	public void DeleteTag(String _tag, long _itemId) {
		// TODO Auto-generated method stub
		TagDAO tDAO = new TagDAO(context);
		tDAO.open();
		tDAO.deleteTag(new Tag(_tag, _itemId));
		tDAO.close();
	}
	
	@Override
	public void ReturnResults(){
		//here we push the data to the database and to the calling activity
		TagDAO tDAO = new TagDAO(context);
		tDAO.open();
		for (String tag : tags){
			tDAO.createTag(tag, itemId);
		}
		tDAO.close();
		listener.resultReturned(tags);
	}

}