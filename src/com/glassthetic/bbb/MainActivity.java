package com.glassthetic.bbb;

import java.util.ArrayList;
import java.util.List;

import com.glassthetic.dribbble.api.DribbbleAPI;
import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.Listener;
import com.glassthetic.dribbble.api.Shot;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {

	private ShotCardScrollAdapter mAdapter;
	private List<Card> mCards;
	private CardScrollView mCardScrollView;
	private Card mLoadingCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DribbbleAPI.setContext(this);
		
		mLoadingCard = new Card(this);
		mLoadingCard.setText(R.string.loading);
		
		mCards = new ArrayList<Card>();
		
		mCardScrollView = new CardScrollView(this);
		mAdapter = new ShotCardScrollAdapter();
		mCardScrollView.setAdapter(mAdapter);
		mCardScrollView.activate();
		
		
		setContentView(mLoadingCard.toView());
		
		Shot.getPopular(new Listener<List<Shot>>() {

			@Override
			public void onResponse(List<Shot> shots) {
				Card card;
				
				for (Shot shot : shots) {
					card = new Card(MainActivity.this);
					card.setText(shot.title);
					card.setInfo(shot.player.name);
//					card.setFullScreenImages(true);
//					card.addImage();
					mCards.add(card);
				}
				
				mAdapter.notifyDataSetChanged();
				setContentView(mCardScrollView);
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
	
	
	private class ShotCardScrollAdapter extends CardScrollAdapter {

		@Override
		public int findIdPosition(Object id) {
			return -1;
		}

		@Override
		public int findItemPosition(Object item) {
			return mCards.indexOf(item);
		}

		@Override
		public int getCount() {
			return mCards.size();
		}

		@Override
		public Object getItem(int position) {
			return mCards.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return mCards.get(position).toView();
		}
		
	}
}
