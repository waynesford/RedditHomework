package com.delectable.reddithomework;

import com.delectable.model.Data2;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * This row view is essentially the same as the ImageRow, except without the Image, which we hide.
 * @author Wayne
 *
 */
public class TitleOnlyRow extends ImageRow{

	public TitleOnlyRow(Context context) 
	{
		this(context, null);
	}
	
	public TitleOnlyRow(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		
		getThumbnailView().setVisibility(View.GONE);
	}
	
	@Override
	public void setData(Data2 data) 
	{
		setTextData(data);
	}
	

}
