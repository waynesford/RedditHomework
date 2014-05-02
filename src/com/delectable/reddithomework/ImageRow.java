package com.delectable.reddithomework;

import com.delectable.model.Data2;
import com.example.reddithomework.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageRow extends RelativeLayout{
	
	private TextView mTitle;
	private TextView mComments;
	private TextView mScore;
	private ImageView mThumbnail; 
	
	public TextView getTitleView() 		{ return mTitle; }
	public TextView getCommentsView() 	{ return mComments; }
	public TextView getScoreView() 		{ return mScore; }
	public ImageView getThumbnailView()	{ return mThumbnail; }


	public ImageRow(Context context)
	{
		this(context, null);
	}
	
	public ImageRow(Context context, AttributeSet attrs) 
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
		setTextData(data);
		//if(data.getThumbnail()!=null && !data.getThumbnail().equals("")) {
			Picasso.with(getContext()).load(data.getThumbnail()).into(mThumbnail);
		//}
	}
	
	protected void setTextData(Data2 data)
	{
		mTitle.setText(data.getTitle());
		String commentString = getContext().getString(R.string.comments, String.valueOf(data.getNum_comments()));
		mComments.setText(commentString);
		mScore.setText(String.valueOf(data.getScore()));
	}


}
