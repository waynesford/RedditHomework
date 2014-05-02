package com.delectable.reddithomework;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;

import com.delectable.model.Data2;

public class DetailViewFragment extends Fragment{

	private static final String PARCEL = "dataParcel";
	
	public enum DataType
	{
		TEXT, URL; 
	}
	
	public void setArguments(Data2 data)
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(PARCEL, data);
		setArguments(bundle);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		if(getArguments()==null) {
			throw new IllegalArgumentException(DetailViewFragment.class.getSimpleName() + " needs to have arguments set onto it."); 
		}
		
		View view = null; 
		Data2 data = getArguments().getParcelable(PARCEL); 
		if(data.isSelf()) {
			//self text post
			
			//put data in textview
			TextView tv = new TextView(getActivity());
			tv.setText(data.getSelftext());
			//throw in scrollview
			ScrollView scrollview = new ScrollView(getActivity());
			scrollview.addView(tv); 
			view = scrollview; 
		} else {
			//link post
			WebView webview = new MyWebView(getActivity());
			webview.loadUrl(data.getUrl());
			view = webview;
		}
		return view; 
	}
	
	/**
	 * Custom webview to wrap up general settings.
	 */
	private class MyWebView extends WebView {

		public MyWebView(Context context) {
			super(context);
			
			getSettings().setBuiltInZoomControls(true);
			getSettings().setDisplayZoomControls(false);
            //make sure that the web page shows in the webview and not the web browser
			setWebViewClient(new MyWebViewClient());
			getSettings().setJavaScriptEnabled(true);
			getSettings().setLoadWithOverviewMode(true);
			getSettings().setUseWideViewPort(true);  
		}
	}
	
	/**
	 * Custom WebViewClient to make sure that the web page shows in the webview and not the web browser
	 */
	private class MyWebViewClient extends WebViewClient { 
        public boolean shouldOverrideUrlLoading(WebView view, String url) 
        { 
            //show the web page in webview but not in web browser
            view.loadUrl (url);
            return true;
        }
    }

}
