package com.glassthetic.bbb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.IndexedListener;
import com.glassthetic.dribbble.api.Shot;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class DisplayShotsActivity extends Activity implements AdapterView.OnItemClickListener {

	private ShotCardScrollAdapter mAdapter;
	private List<Card> mCards;
	private CardScrollView mCardScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		final List<Shot> shots = intent.getParcelableArrayListExtra(Constants.SHOTS_PARCEL);
		
		mCards = new ArrayList<Card>();
		
		mCardScrollView = new CardScrollView(this);
		mCardScrollView.setOnItemClickListener(this);
		mAdapter = new ShotCardScrollAdapter();
		mCardScrollView.setAdapter(mAdapter);
		
		int numberOfLikesText = R.string.number_of_likes;
		
		final File cacheDir = new File(DisplayShotsActivity.this.getCacheDir(), "images");
		cacheDir.mkdirs();
		
		for (int i = 0; i < shots.size(); i++) {
			Shot shot = shots.get(i);
			
			Card card = new Card(this);
			card.setText(shot.title);
			card.setFootnote(getResources().getString(numberOfLikesText, shot.likesCount));
			card.setImageLayout(Card.ImageLayout.FULL);
			
			mCards.add(card);
			
			// TODO: try loading next images on focus of current image
			shot.getImage(new IndexedListener<Bitmap>(i) {

				@Override
				public void onResponse(Bitmap bitmap) {
					int index = this.getIndex();
					Card card = (Card) mAdapter.getItem(index);
					Shot shot = shots.get(index);
					
					BitmapWorkerTask task = new BitmapWorkerTask(cacheDir, shot, bitmap, card, mCardScrollView);
					task.execute();
				}
				
			}, new ErrorListener() {
				
				@Override
				public void onErrorResponse(Exception exception) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
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
