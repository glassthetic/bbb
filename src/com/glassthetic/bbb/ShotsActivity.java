package com.glassthetic.bbb;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.glassthetic.dribbble.api.Shot;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class ShotsActivity extends Activity implements AdapterView.OnItemClickListener {

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
		String numberOfLikes;
		int resourceId = R.string.number_of_likes;
		
		for (Shot shot : shots) {
			card = new Card(ShotsActivity.this);
			card.setText(shot.title);
			numberOfLikes = getResources().getString(resourceId, shot.likesCount);
			card.setInfo(numberOfLikes);
//			card.setFullScreenImages(true);
//			card.addImage();
			mCards.add(card);
		}
		
		mCardScrollView = new CardScrollView(this);
		mCardScrollView.setOnItemClickListener(this);
		mAdapter = new ShotCardScrollAdapter();
		mCardScrollView.setAdapter(mAdapter);
		mCardScrollView.activate();
		
		setContentView(mCardScrollView);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.shot, menu);
		return true;
	}
	
	@Override
	public void onItemClick(android.widget.AdapterView<?> adapterView, View view, int position, long id) {
		openOptionsMenu();
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
