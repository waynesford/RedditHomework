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
import android.widget.TextView;

import com.delectable.model.Child;
import com.delectable.model.Page;
import com.example.reddithomework.R;

public class HomepageList extends Fragment{


	ListView mListView;
	MyListAdapter mListAdapter; 
	List<Child> mChildren = new ArrayList<Child>();

	//initing our rest endpoint class
	RestAdapter restAdapter = new RestAdapter.Builder()
	.setEndpoint("http://www.reddit.com") // The base API endpoint.
	.build();

	RedditEndpoints redditEndpoints = restAdapter.create(RedditEndpoints.class);

	/**We use this hold on to the after ID that we use to get to the next page*/
	private String mAfter;
	/**The progressBar is shown when the user scrolls to the bottom of the list and the GET request for the next page of data is underway.*/
	private ProgressBar mProgressBar; 
	/**This shows up in our header when we've exhausted the data source.*/
	private TextView mEndTextView; 
	/**
	 * If we are showing the ProgressBar, then we must be attempting a GET request currently. 
	 * Used to make sure we aren't making double calls
	 */
	private boolean isLoading()
	{
		return mProgressBar.getVisibility()==View.VISIBLE; 
	}

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
		mEndTextView = (TextView)footer.findViewById(R.id.end);

		mListView.addFooterView(footer, null, false);
		mListView.setOnItemClickListener(mItemClickListener);
		mListView.setAdapter(mListAdapter = new MyListAdapter(mChildren));
		mListView.setOnScrollListener(mScrollListener);

		//setting up button to invoke call
		rootView.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				redditEndpoints.getHomepage(null, callback);
				mProgressBar.setVisibility(View.VISIBLE);
			}
		});
		return rootView;
	}

	private Callback<Page> callback = new Callback<Page>() {
		@Override
		public void success(Page page, Response arg1) {

			//done retrieving items, so we hide the progressBar
			mProgressBar.setVisibility(View.GONE);

			//grabbing hold of after so we can use it later when user scrolls to bottom of list
			mAfter = page.getData().getAfter(); 

			//means we've reached the last page of the data source, and there is no page afterwards
			if(mAfter==null) {
				mEndTextView.setVisibility(View.VISIBLE);
			}

			//adding the new onto array and refreshing the listview to show additions
			List<Child> children = page.getData().getChildren(); 
			mChildren.addAll(children); 
			mListAdapter.notifyDataSetChanged(); 
		}

		@Override
		public void failure(RetrofitError retrofitError) {

			//TODO need to handle error
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
			if(isLoading()) {
				return;
			}

			//means we've reached the end of the list, so we disallow them from making another rest request
			if(mEndTextView.getVisibility()==View.VISIBLE) {
				return;
			}

			//listen for when the user has reached the bottom of the list
			boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

			//if they've reached the end of the list, then we init the GET call for the next page
			if(loadMore) {
				redditEndpoints.getHomepage(mAfter, callback);
				mProgressBar.setVisibility(View.VISIBLE);
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
