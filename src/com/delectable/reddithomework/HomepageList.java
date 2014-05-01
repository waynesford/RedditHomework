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
import android.view.Window;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.delectable.model.Child;
import com.delectable.model.Page;
import com.example.reddithomework.R;

public class HomepageList extends Fragment{


	ListView mListView;
	MyListAdapter mListAdapter; 
	List<Child> mChildren = new ArrayList<Child>();

	RestAdapter restAdapter = new RestAdapter.Builder()
	  .setEndpoint("http://www.reddit.com") // The base API endpoint.
	  .build();

	RedditEndpoints redditService = restAdapter.create(RedditEndpoints.class);
	
	/**
	 * We use this hold on to the after ID that we use to get to the next page
	 */
	private String mAfter;
	/**Used to keep track of when a rest request is taking place, so we don't end up making double calls*/
	private boolean isLoading;
	private ProgressBar mProgressBar; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_main, container,	false);
		
		//listview setup
		mListView = (ListView)rootView.findViewById(R.id.listview);
		
		//footer setup
		View footer = inflater.inflate(R.layout.footer, mListView, false);
		mProgressBar = (ProgressBar)footer.findViewById(R.id.progressBar);
		mProgressBar.setVisibility(View.GONE);
		mListView.addFooterView(footer, null, false);
		mListView.setOnItemClickListener(mItemClickListener);
		mListView.setAdapter(mListAdapter = new MyListAdapter(mChildren));
		mListView.setOnScrollListener(mScrollListener);
		
		//setting up button to invoke call
		rootView.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				redditService.getHomepage(null, callback);
				isLoading = true;
				mProgressBar.setVisibility(View.VISIBLE);
			}
		});
		return rootView;
	}
	
	private Callback<Page> callback = new Callback<Page>() {
		@Override
		public void success(Page page, Response arg1) {
			
			//done retrieving items, so we can set this back to false
			isLoading = false;
			mProgressBar.setVisibility(View.GONE);

			//grabbing hold of after so we can use it later when user scrolls to bottom of list
			mAfter = page.getData().getAfter(); 
			
			//adding the new onto array and refreshing the listview to show additions
			List<Child> children = page.getData().getChildren(); 
			mChildren.addAll(children); 
			mListAdapter.notifyDataSetChanged(); 
		}

	    @Override
	    public void failure(RetrofitError retrofitError) {
	    	
	    	//TODO need to handle error
	    	isLoading = false;
			mProgressBar.setVisibility(View.GONE);

	    }
	};
	
	private OnScrollListener mScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			//do nothing
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) 
		{
			//in the middle of a request, disallow a new REST request from happening
			if(isLoading) {
				return;
			}
			//listen for when the user has reached the bottom of the list
			 boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

			 //if they've reached the end of the list, then we init the GET call for the next page
			 if(loadMore) {
					redditService.getHomepage(mAfter, callback);
					isLoading = true;
					mProgressBar.setVisibility(View.VISIBLE);
					getActivity().setProgressBarIndeterminateVisibility(true);
			 }
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
