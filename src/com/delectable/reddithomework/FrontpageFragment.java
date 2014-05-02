package com.delectable.reddithomework;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.delectable.model.Child;
import com.delectable.model.Page;
import com.example.reddithomework.R;

public class FrontpageFragment extends Fragment{

	//setup our rest endpoint class
	RestAdapter restAdapter = new RestAdapter.Builder()
	.setEndpoint("http://www.reddit.com") // The base API endpoint.
	.build();

	RedditEndpoints redditEndpoints = restAdapter.create(RedditEndpoints.class);
	
	

	//our callback to the activity, to let it know which item was selected
	OnItemSelectedListener mListener; 
	
	ListView mListView;
	MyListAdapter mListAdapter; 
	List<Child> mChildren = new ArrayList<Child>();

	
	/**We use this hold on to the after ID that we use to get to the next page*/
	private String mAfter;
	/**The progressBar is shown when the user scrolls to the bottom of the list and the GET request for the next page of data is underway.*/
	private ProgressBar mProgressBar; 
	/**This shows up in our header when we've exhausted the data source.*/
	private TextView mEndTextView; 
	/**This shows when the GET request errors. Pressing the Button will allow them to manually init the request again!*/
	private Button mErrorButton; 
	/**
	 * If we are showing the ProgressBar, then we must be attempting a GET request currently. 
	 * Used to make sure we aren't making double calls
	 */
	private boolean isLoading()
	{
		return mProgressBar.getVisibility()==View.VISIBLE; 
	}
	/**
	 * @param afterValue The after ID needed to retrieve the next page. Pass in null to not pass in an "after" parameter, which will simply return the first page of the list.
	 */
	private void getNextRedditPage(String afterValue)
	{
		Log.d("sample", "getNextRedditPage"); 
		redditEndpoints.getRedditFrontpage(afterValue, callback);
		
		//we'll always show the progressCircle when we we init a GET request. we also use it's visibility value for flow control as well.
		mProgressBar.setVisibility(View.VISIBLE);
		mErrorButton.setVisibility(View.GONE);
		mEndTextView.setVisibility(View.GONE);
	}
	
	
    /** Container Activity must implement this interface and we ensure
     * that it does during the onAttach() callback
     */
    public interface OnItemSelectedListener {
        public void onItemSelected(Child child);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Check that the container activity has implemented the callback interface
        try {
            mListener = (OnItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() 
                    + " must implement OnItemSelectedListener");
        }
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	menu.findItem(R.id.action_settings).setVisible(false);
       inflater.inflate(R.menu.frontpage, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	switch (item.getItemId()) {
		case R.id.action_refresh:
			onRefreshClick();
			break;
		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }
    
    private void onRefreshClick()
    {
    	//clear out values and get the redditpage again
		mAfter = null;
		mChildren.clear();
		mListAdapter.notifyDataSetChanged();
		getNextRedditPage(null);
    }

    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
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
		mErrorButton = (Button)footer.findViewById(R.id.error);
		
		//as this will only be visible on error, this will essentially redo the last call
		//over again, because our mAfter value was never replaced with a value from the succesful response.
		mErrorButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mErrorButton.setVisibility(View.GONE);
				getNextRedditPage(mAfter);
			}
		});

		//listview setup (cont.)
		mListView.addFooterView(footer, null, false);
		mListView.setOnItemClickListener(mItemClickListener);
		mListView.setAdapter(mListAdapter = new MyListAdapter(mChildren));
		mListView.setOnScrollListener(mScrollListener);

		//setting up button to invoke call
		rootView.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				redditEndpoints.getRedditFrontpage(null, callback);
				mProgressBar.setVisibility(View.VISIBLE);
			}
		});
		return rootView;
	}

	private Callback<Page> callback = new Callback<Page>() {
		@Override
		public void success(Page page, Response arg1) {

			mProgressBar.setVisibility(View.GONE);
			mAfter = page.getData().getAfter(); //so we can use later when we user scrolls to the bottom of the list

			//there is no next page, end of list
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
			
			Log.d("sample", "" + retrofitError.getResponse().getReason()); 
			retrofitError.printStackTrace();
			mErrorButton.setVisibility(View.VISIBLE);
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

			//we've reached the end of the list, disallow user from making another request
			if(mEndTextView.getVisibility()==View.VISIBLE) {
				return;
			}
			
			//the error button is visible only when the last GET request errored, 
			//disallow scrolldown to get more posts, user must manually retry request by pressing error button
			if(mErrorButton.getVisibility()==View.VISIBLE) {
				return;
			}

			//listen for when the user has reached the bottom of the list
			boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

			//if they've reached the end of the list, then we start the GET request for the next page
			if(loadMore) {
				getNextRedditPage(mAfter);
			}
		}
	};




	OnItemClickListener mItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			//callback to activity to inform it what item was selected
			mListener.onItemSelected(mListAdapter.getItem(position));
		}
	};




}
