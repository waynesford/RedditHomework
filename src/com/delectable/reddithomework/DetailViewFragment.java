package com.delectable.reddithomework;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.reddithomework.R;

public class DetailViewFragment extends Fragment{
	
	private static final String DATATYPE = "datatype";
	private static final String DATA = "data";
	
	public enum DataType
	{
		TEXT, URL; 
		
		private String mData; 
		
		public void setData(String data)
		{
			mData = data; 
		}
		public String getData() 
		{ 
			return mData; 
		}
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

}
