package com.delectable.reddithomework;

import com.delectable.model.Data2;
import com.example.reddithomework.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RowView extends RelativeLayout{
	
	private TextView mTitle;
	private TextView mComments;
	private TextView mScore;
	private ImageView mThumbnail; 
	
	public TextView getTitleView() 		{ return mTitle; }
	public TextView getCommentsView() 	{ return mComments; }
	public TextView getScoreView() 		{ return mScore; }
	public ImageView getThumbnailView()	{ return mThumbnail; }


	public RowView(Context context)
	{
		this(context, null);
	}
	
	public RowView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		init();
	}
	
	private void init()
	{
		inflate(getContext(), R.layout.homepage_row, this);
		
		//grabbing views, setting as member vars
		mTitle 		= (TextView)findViewById(R.id.title);
		mComments 	= (TextView)findViewById(R.id.comments);
		mScore 		= (TextView)findViewById(R.id.score);
		mThumbnail 	= (ImageView)findViewById(R.id.thumbnail); 
	}
	
	public void setData(Data2 data)
	{
		//setting text to view
		mTitle.setText(data.getTitle());
		String commentString = getContext().getString(R.string.comments, String.valueOf(data.getNum_comments()));
		mComments.setText(commentString);
		mScore.setText(String.valueOf(data.getScore()));
		
		//setting thumbnail image to view
		if(thumbnailExists(data)) {
			Picasso.with(getContext()).load(data.getThumbnail()).into(mThumbnail);
			return;
		}
		//thumbnail doesn't exist, set default image as image
		Picasso.with(getContext()).load(android.R.drawable.ic_menu_gallery).into(mThumbnail);
	}
	
	private boolean thumbnailExists(Data2 data) 
	{ 
		return data.getThumbnail()!=null && 
				!data.getThumbnail().equals("") &&
				!data.getThumbnail().equalsIgnoreCase("self"); //sometimes the thumbnail link shows as self
	} 


}
