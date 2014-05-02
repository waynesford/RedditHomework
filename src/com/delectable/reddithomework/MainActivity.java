package com.delectable.reddithomework;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.delectable.model.Child;
import com.delectable.reddithomework.DetailViewFragment.DataType;
import com.delectable.reddithomework.FrontpageFragment.OnItemSelectedListener;
import com.example.reddithomework.R;

public class MainActivity extends Activity implements OnItemSelectedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			Log.d("samples", "savedInstanceState is null");
			getFragmentManager().beginTransaction()
			.add(R.id.container, new FrontpageFragment()).commit();
		} else {
			Log.d("samples", "savedInstanceState is not null");
		}
	}

	@Override
	public void onItemSelected(Child child) {
		
		DetailViewFragment fragment = new DetailViewFragment();
		
		//determine whether there is URL data or just text data
		
		if(child.getData().isSelf()) {
			//self text post
			
			if(child.getData().getSelftext().equals("")) {
				//empty self text, don't let user proceed
				Toast.makeText(this, R.string.emptyText, Toast.LENGTH_SHORT).show();
				return;
			}
			//there is valid text for us to populate the next screen with
			fragment.setArguments(DataType.TEXT, child.getData().getSelftext());
			
			
		} else {
			//link post
			fragment.setArguments(DataType.URL, child.getData().getUrl());
		}

		//pass on data to detailview
		getFragmentManager().beginTransaction()
		.replace(R.id.container, fragment)
		.addToBackStack(DetailViewFragment.class.getSimpleName())
		.commit();

	}

}
