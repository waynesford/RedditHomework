package com.delectable.reddithomework;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.delectable.model.Child;
import com.delectable.model.Data2;
import com.delectable.reddithomework.FrontpageFragment.OnItemSelectedListener;
import com.example.reddithomework.R;

public class MainActivity extends Activity implements OnItemSelectedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new FrontpageFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(Child child) {
		
		//pass on data to detailview
		getFragmentManager().beginTransaction()
		.replace(R.id.container, new DetailViewFragment())
		.addToBackStack(DetailViewFragment.class.getSimpleName())
		.commit();
		
	}
	



}
