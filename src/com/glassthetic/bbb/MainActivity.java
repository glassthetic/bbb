package com.glassthetic.bbb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.glassthetic.dribbble.api.DribbbleAPI;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DribbbleAPI.setContext(this);		
		openOptionsMenu();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.view_debuts_menu_item:
				startActivity(new Intent(this, DebutsShotsRequestActivity.class));
				return true;
			case R.id.view_everyone_menu_item:
				startActivity(new Intent(this, EveryoneShotsRequestActivity.class));
				return true;
			case R.id.view_popular_menu_item:
				startActivity(new Intent(this, PopularShotsRequestActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		openOptionsMenu();
	}
}
