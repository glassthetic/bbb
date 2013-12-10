package com.glassthetic.bbb;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;

import com.glassthetic.dribbble.api.DribbbleAPI;
import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.Listener;
import com.glassthetic.dribbble.api.Shot;

public class MainActivity extends Activity {

	private ProgressBar mProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DribbbleAPI.setContext(this);
		
		setContentView(R.layout.loading);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		mProgressBar.setVisibility(View.VISIBLE);
		mProgressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, Mode.SRC_IN);
		
		Shot.getPopular(new Listener<List<Shot>>() {

			@Override
			public void onResponse(List<Shot> shots) {
				mProgressBar.setVisibility(View.GONE);
				
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ShotsActivity.class);
				intent.putParcelableArrayListExtra(Constants.SHOTS_PARCEL, (ArrayList<? extends Parcelable>) new ArrayList<Shot>(shots));
				// Here we can pass additional parameters to prevent the user from going back
				// http://developer.android.com/guide/components/tasks-and-back-stack.html
				startActivity(intent);
			}
		}, new ErrorListener() {
			
			@Override
			public void onErrorResponse(Exception exception) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}
