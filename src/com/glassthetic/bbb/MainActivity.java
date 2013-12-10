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
import com.google.android.glass.app.Card;

public class MainActivity extends Activity {

	private Card mLoadingCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DribbbleAPI.setContext(this);
		
		mLoadingCard = new Card(this);
		mLoadingCard.setText(R.string.loading);
		setContentView(mLoadingCard.toView());
		
		Shot.getPopular(new Listener<List<Shot>>() {

			@Override
			public void onResponse(List<Shot> shots) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ShotsActivity.class);
				intent.putParcelableArrayListExtra(Constants.SHOTS_PARCEL, (ArrayList<? extends Parcelable>) new ArrayList<Shot>(shots));
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
