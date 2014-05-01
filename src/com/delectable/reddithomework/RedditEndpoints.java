package com.delectable.reddithomework;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import com.delectable.model.Page;

public interface RedditEndpoints {
	  @GET("/.json")
	  void getHomepage(@Query("after") String after, Callback<Page> cb);
	}
