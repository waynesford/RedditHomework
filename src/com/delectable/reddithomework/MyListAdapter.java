package com.delectable.reddithomework;

import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.delectable.model.Child;
import com.delectable.model.Data2;

class MyListAdapter extends BaseAdapter
{
	
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
	public int getItemViewType(int position) 
	{
		Data2 data = getItem(position).getData();
		if(data.getThumbnail()!=null && !data.getThumbnail().equals("")) {
			return 0;
		}
		return 1; 
	}
	
	@Override
	public int getViewTypeCount() 
	{
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		Data2 data = getItem(position).getData();
		ImageRow view = (ImageRow)convertView; 
		
		//init row views
		if(view==null) {
			if(data.getThumbnail()!=null && !data.getThumbnail().equals("")) {
				view = new ImageRow(parent.getContext());
			} else {
				view = new TitleOnlyRow(parent.getContext()); 
			}
			int height = getPx(parent.getContext(), 80);
			//view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
		}
		
		//set data upon rows as they get shown on screen
		view.setData(data);
		
		return view;
	}
	
	
	
	
	/**Converts dp to px*/
	private int getPx(Context c, float dp) 
	{
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
	}
	
	
}
