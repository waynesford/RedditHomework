package com.delectable.reddithomework;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.delectable.model.Child;
import com.delectable.model.Data2;
import com.delectable.model.Page;
import com.example.reddithomework.R;

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
				RedditEndpoints redditService = restAdapter.create(RedditEndpoints.class);
				redditService.getHomepage(callback);
			}
		});
		return rootView;
	}
	
	private Callback<Page> callback = new Callback<Page>() {
		@Override
		public void success(Page page, Response arg1) {
			List<Child> children = page.getData().getChildren(); 
			mChildren.clear(); 
			mChildren.addAll(children); 
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
			//inverse selection
			boolean selected = view.isSelected();
			view.setSelected(!selected);
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
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ImageRow view = (ImageRow)convertView; 
			//init rows
			if(view==null) {
				int height = getPx(parent.getContext(), 80);
				view = new ImageRow(getActivity());
				//view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
			}
			//set data upon rows as they get shown on screen
			Data2 data = getItem(position).getData();
			view.setData(data);
			return view;
		}
		
		
	}
	
	/**Converts dp to px*/
	private int getPx(Context c, float dp) 
	{
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
	}

}
