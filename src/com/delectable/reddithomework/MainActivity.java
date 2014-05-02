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
	public void onItemSelected(Child child) 
	{
		//handle case where it's a self text post, but the text is empty
		if(child.getData().isSelf()) {
			if(child.getData().getSelftext().equals("")) {
				//tell user there's no text, don't let user proceed to detail view
				Toast.makeText(this, R.string.emptyText, Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		DetailViewFragment fragment = new DetailViewFragment();
		fragment.setArguments(child.getData());

		//pass on data to detailview
		getFragmentManager().beginTransaction()
		.replace(R.id.container, fragment)
		.addToBackStack(DetailViewFragment.class.getSimpleName())
		.commit();

	}

}
