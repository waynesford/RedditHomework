package com.delectable.reddithomework;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.delectable.reddithomework.MainActivity.Contributor;
import com.delectable.reddithomework.MainActivity.GitHubService;
import com.example.reddithomework.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomepageList extends Fragment{


	TextView textview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		textview = (TextView)rootView.findViewById(R.id.textview); 
		rootView.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				RestAdapter restAdapter = new RestAdapter.Builder()
				  .setServer("https://api.github.com") // The base API endpoint.
				  .build();
				
				GitHubService github = restAdapter.create(GitHubService.class);
				github.contributors("square", "retrofit", callback);
				//List<Contributor> contributors = github.contributors("square", "okhttp");
//				String message = null; 
//				for (Contributor contributor : contributors) {
//				  message = contributor.login + " - " + contributor.contributions + "\n";
//				}
//				textview.setText(message);
				
			}
		});
		return rootView;
	}
	
	private Callback<ArrayList<Contributor>> callback = new Callback<ArrayList<Contributor>>() {
		@Override
		public void success(ArrayList<Contributor> contributors, Response arg1) {
			String message = ""; 
			for (Contributor contributor : contributors) {
			  message += contributor.login + " - " + contributor.contributions + "\n";
			}
			textview.setText(message);	
		}

	    @Override
	    public void failure(RetrofitError retrofitError) {
			textview.setText(retrofitError.toString());	

	    }


	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);



		
	}

}
