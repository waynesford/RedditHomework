package com.delectable.reddithomework;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reddithomework.R;

public class DetailViewFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		TextView tv = (TextView)view.findViewById(R.id.button);
		tv.setText("Hey Man");
		
		return view; 
	}

}
