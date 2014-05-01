package com.delectable.reddithomework;

import retrofit.Callback;
import retrofit.http.GET;

import com.delectable.model.Page;

public interface RedditEndpoints {
	  @GET("/.json")
	  void getHomepage(Callback<Page> cb);
	}
