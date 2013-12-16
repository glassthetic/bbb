package com.glassthetic.bbb;

import java.util.ArrayList;
import java.util.List;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.Paginator;
import com.glassthetic.dribbble.api.PaginatedListener;
import com.glassthetic.dribbble.api.Shot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

abstract class AbstractShotsRequestActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PaginatedListener<Shot> listener = new PaginatedListener<Shot>() {
			
			@Override
			public void onPaginatedResponse(List<Shot> shots, Paginator paginator) {			
				Intent intent = new Intent();
				intent.setClass(AbstractShotsRequestActivity.this, DisplayShotsActivity.class);
				intent.putParcelableArrayListExtra(Constants.SHOTS_PARCEL, (ArrayList<? extends Parcelable>) new ArrayList<Shot>(shots));
				intent.putExtra(Constants.PAGINATOR_PARCEL, paginator);
				//intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
				
				// Consider http://stackoverflow.com/q/12358485/339925
				// instead of finish()
				finish();
			}
		};
		
		//listener = new ListenerProgressDecorator<List<Shot>>(this, listener);
		
		
		ErrorListener errorListener = new ErrorListener() {
			
			@Override
			public void onErrorResponse(Exception exception) {
				// TODO Auto-generated method stub
				
			}
		};
		
		requestShots(listener, errorListener);
	}
	
	abstract void requestShots(PaginatedListener<Shot> listener, ErrorListener errorListener);
}
