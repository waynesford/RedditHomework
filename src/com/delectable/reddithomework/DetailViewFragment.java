package com.delectable.reddithomework;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.PictureListener;
import android.widget.TextView;

public class DetailViewFragment extends Fragment{
	
	private static final String DATATYPE = "datatype";
	private static final String DATA = "data";
	
	public enum DataType
	{
		TEXT, URL; 
	}
	
	
	public void setArguments(DataType dataType, String data) 
	{
		Bundle bundle = new Bundle();
		bundle.putSerializable(DATATYPE, dataType);
		bundle.putString(DATA, data);
		setArguments(bundle);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
//		View view = inflater.inflate(R.layout.fragment_main, container, false);
//		TextView tv = (TextView)view.findViewById(R.id.button);
//		tv.setText("Hey Man");
		
		View view = null; 
		if(getArguments()==null) {
			throw new IllegalArgumentException(DetailViewFragment.class.getSimpleName() + " needs to have arguments set onto it."); 
		}
		
		DataType dataType = (DataType) getArguments().getSerializable(DATATYPE); 
		
		if(dataType==DataType.URL) {
			//init webview
			WebView webview = new WebView(getActivity());
			webview.loadUrl(getArguments().getString(DATA));
			Log.d("sample", getArguments().getString(DATA));
			webview.getSettings().setBuiltInZoomControls(true);
			webview.getSettings().setDisplayZoomControls(false);
			webview.setWebViewClient(new MyWebViewClient());
			webview.getSettings().setJavaScriptEnabled(true);
			webview.getSettings().setLoadWithOverviewMode(true);
			webview.getSettings().setUseWideViewPort(true);  
			view = webview;
		}
		
		if(dataType==DataType.TEXT) {
			//init textview
			TextView tv = new TextView(getActivity());
			tv.setText(getArguments().getString(DATA));
			view = tv; 
		}
		
		return view; 
	}
	
	private class MyWebViewClient extends WebViewClient { 
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) 
        { 
            //show the web page in webview but not in web browser
            view.loadUrl (url);
            return true;
        }
        
    }

}
