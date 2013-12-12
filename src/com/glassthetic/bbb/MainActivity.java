package com.glassthetic.bbb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.glassthetic.dribbble.api.DribbbleAPI;
import com.google.android.glass.view.MenuUtils;

public class MainActivity extends Activity {
	
	private static MenuItem mSelectedOptionsMenuItem;
	private Boolean mOptionsMenuItemSelected = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DribbbleAPI.setContext(this);		
	}

	@Override
	protected void onResume() {
		super.onResume();
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
		mSelectedOptionsMenuItem = item;
		mOptionsMenuItemSelected = true;
		
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
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (mSelectedOptionsMenuItem != null) {
			MenuUtils.setInitialMenuItem(menu, mSelectedOptionsMenuItem);
		}
		
		return true;
	}
	
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		super.onOptionsMenuClosed(menu);
		
		if (mOptionsMenuItemSelected) {
			mOptionsMenuItemSelected = false;
		} else {
			finish();
		}
	}
}
