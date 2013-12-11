package com.glassthetic.bbb;

import java.util.ArrayList;
import java.util.List;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.Listener;
import com.glassthetic.dribbble.api.Shot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

abstract class AbstractShotsRequestActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Listener<List<Shot>> listener = new ListenerProgressDecorator<List<Shot>>(this, new Listener<List<Shot>>() {
			
			@Override
			public void onResponse(List<Shot> shots) {			
				Intent intent = new Intent();
				intent.setClass(AbstractShotsRequestActivity.this, DisplayShotsActivity.class);
				intent.putParcelableArrayListExtra(Constants.SHOTS_PARCEL, (ArrayList<? extends Parcelable>) new ArrayList<Shot>(shots));
				//intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
				
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
		
		requestShots(listener, errorListener);
	}
	
	abstract void requestShots(Listener<List<Shot>> listener, ErrorListener errorListener);
}
