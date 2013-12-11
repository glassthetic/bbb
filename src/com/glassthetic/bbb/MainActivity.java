package com.glassthetic.bbb;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.glassthetic.dribbble.api.DribbbleAPI;
import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.Listener;
import com.glassthetic.dribbble.api.Shot;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DribbbleAPI.setContext(this);
		
		Listener<List<Shot>> listener = new ListenerProgressDecorator<List<Shot>>(this, new Listener<List<Shot>>() {

			@Override
			public void onResponse(List<Shot> shots) {			
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ShotsActivity.class);
				intent.putParcelableArrayListExtra(Constants.SHOTS_PARCEL, (ArrayList<? extends Parcelable>) new ArrayList<Shot>(shots));
				startActivity(intent);
				
				// Instead, the user could return to this activity
				// and select from Popular, Everyone, or Debuts.
				//
				// Consider http://stackoverflow.com/q/12358485/339925
				// instead of finish()
				finish();
			}
		});
		
		ErrorListener errorListener = new ErrorListener() {
			
			@Override
			public void onErrorResponse(Exception exception) {
				// TODO Auto-generated method stub
				
			}
		};
		
		Shot.getPopular(listener, errorListener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}
