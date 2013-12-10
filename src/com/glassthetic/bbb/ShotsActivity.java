package com.glassthetic.bbb;

import java.util.ArrayList;
import java.util.List;

import com.glassthetic.dribbble.api.Shot;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class ShotsActivity extends Activity {

	private ShotCardScrollAdapter mAdapter;
	private List<Card> mCards;
	private CardScrollView mCardScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		List<Shot> shots = intent.getParcelableArrayListExtra(Constants.SHOTS_PARCEL);
		
		mCards = new ArrayList<Card>();
		
		Card card;
		
		for (Shot shot : shots) {
			card = new Card(ShotsActivity.this);
			card.setText(shot.title);
			card.setInfo(shot.player.name);
//			card.setFullScreenImages(true);
//			card.addImage();
			mCards.add(card);
		}
		
		mCardScrollView = new CardScrollView(this);
		mAdapter = new ShotCardScrollAdapter();
		mCardScrollView.setAdapter(mAdapter);
		mCardScrollView.activate();
		
		setContentView(mCardScrollView);
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
