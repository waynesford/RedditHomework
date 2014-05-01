package com.delectable.reddithomework;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.delectable.model.Child;
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
	



}
