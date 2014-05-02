package com.delectable.reddithomework;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.delectable.model.Child;
import com.delectable.model.Data2;

class MyListAdapter extends BaseAdapter {
	
	private List<Child> mChildren; 
	
	public MyListAdapter(List<Child> children)
	{
		mChildren = children; 
	}

	@Override
	public int getCount() {
		return mChildren.size();
	}

	@Override
	public Child getItem(int position) {
		return mChildren.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		RowView view = (RowView)convertView; 
		
		//init row views if they don't exist yet
		if(view==null) {
			view = new RowView(parent.getContext());
		}
		
		//set data upon rows as they get shown on screen
		Data2 data = getItem(position).getData();
		view.setData(data);
		
		return view;
	}
	
	
}
