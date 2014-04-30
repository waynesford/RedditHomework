package com.delectable.reddithomework;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delectable.model.Child;
import com.delectable.model.Data2;
import com.delectable.model.Page;
import com.delectable.reddithomework.MainActivity.GitHubService;
import com.example.reddithomework.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Listener;

public class HomepageList extends Fragment{


	ListView mListView;
	MyListAdapter mListAdapter; 
	RestAdapter restAdapter;
	List<Child> mChildren = new ArrayList<Child>(); 
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//init restAdapter
		restAdapter = new RestAdapter.Builder()
		  .setEndpoint("http://www.reddit.com") // The base API endpoint.
		  .build();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_main, container,	false);
		
		//setting up listview
		mListView = (ListView)rootView.findViewById(R.id.listview);
		mListView.setOnItemClickListener(mItemClickListener);
		mListView.setAdapter(mListAdapter = new MyListAdapter(mChildren));
		
		//setting up button to invoke call
		rootView.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GitHubService github = restAdapter.create(GitHubService.class);
				github.contributors(callback);
			}
		});
		return rootView;
	}
	
	private Callback<Page> callback = new Callback<Page>() {
		@Override
		public void success(Page page, Response arg1) {
			String message = ""; 
			List<Child> children = page.getData().getChildren(); 
			mChildren.addAll(children); 
//			for (Child child : children) {
//			  message += child.getData().getTitle() + " - " + child.getData().getScore() + "\n";
//			}
			mListAdapter.notifyDataSetChanged(); 
		}

	    @Override
	    public void failure(RetrofitError retrofitError) {
			//mListView.setText(retrofitError.toString());	
	    }
	};
	
	OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	private class MyListAdapter extends BaseAdapter
	{
		List<Child> mChildren; 
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
		public View getView(int position, View convertView, ViewGroup parent) {
			
			RelativeLayout view = (RelativeLayout)convertView; 
			if(view==null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = (RelativeLayout)inflater.inflate(R.layout.homepage_row, parent, false); 
			}
			
			Data2 data = getItem(position).getData();
			
			((TextView)view.findViewById(R.id.title) ).setText(data.getTitle());
			((TextView)view.findViewById(R.id.comments) ).setText(String.valueOf(data.getNum_comments()));
			((TextView)view.findViewById(R.id.score) ).setText(String.valueOf(data.getScore()));
			ImageView thumbnailImageView = (ImageView)view.findViewById(R.id.thumbnail); 
			
			int targetWidth = getPx(parent.getContext(), 80);
			int targetHeight = getPx(parent.getContext(), 80);
			final String title = data.getTitle();
			Picasso.with(parent.getContext()).load(data.getUrl()).fit().into(thumbnailImageView);
			//Picasso.with(parent.getContext()).load("http://f.thumbs.redditmedia.com/FwDuK1DO76atl2rk.jpg").fit().centerInside().into(thumbnailImageView);

			
			Listener listener = new Listener() {
				@Override
				public void onImageLoadFailed(Picasso arg0, Uri arg1, Exception exception) {
					exception.printStackTrace();
					
				}
			};
			
			//new Picasso.Builder(getActivity()).listener(listener).build().load(data.getUrl()).fit().centerInside().into(thumbnailImageView);
			
			Log.d("sample", "title: " + data.getTitle());
			Log.d("sample", "thumbnail: " + data.getThumbnail());

			
			
			return view;
		}
		
		
		
	}
	
	/**Converts dp to px*/
	public static int getPx(Context context, float dp) 
	{
		Resources r = context.getResources();
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
	}

}
