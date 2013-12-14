package com.glassthetic.bbb;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.Listener;
import com.glassthetic.dribbble.api.Shot;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class DisplayShotsActivity extends Activity implements AdapterView.OnItemClickListener {

	private ShotCardScrollAdapter mAdapter;
	private List<Card> mCards;
	private CardScrollView mCardScrollView;
	private SparseIntArray mShotCardMapping = new SparseIntArray();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		List<Shot> shots = intent.getParcelableArrayListExtra(Constants.SHOTS_PARCEL);
		
		mCards = new ArrayList<Card>();
		
		Card card;
		String numberOfLikes;
		int index = 0;
		int resourceId = R.string.number_of_likes;
		
		for (final Shot shot : shots) {
			card = new Card(this);
			card.setText(shot.title);
			numberOfLikes = getResources().getString(resourceId, shot.likesCount);
			card.setInfo(numberOfLikes);
			card.setFullScreenImages(true);
			
			mShotCardMapping.append(shot.id, index);
			mCards.add(card);
			
			
			final File cacheDir = new File(getBaseContext().getCacheDir(), "images");
			cacheDir.mkdirs();
			
			shot.getImage(new Listener<Bitmap>() {
				
				@Override
				public void onResponse(Bitmap bitmap) {
					File cacheFile = new File(cacheDir, shot.id + "");
					
                    try {
						cacheFile.createNewFile();
						FileOutputStream fos = new FileOutputStream(cacheFile);
						bitmap.compress(CompressFormat.PNG, 100, fos);
						fos.flush();
						fos.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                  
                    int position = mShotCardMapping.get(shot.id);
                    Card card = (Card) mAdapter.getItem(position);
        			card.addImage(Uri.fromFile(cacheFile));
        			mCardScrollView.updateViews(true);
				}
			}, new ErrorListener() {
				
				@Override
				public void onErrorResponse(Exception exception) {
					// TODO Auto-generated method stub
					
				}
			});
			
			index++;
			
//			if (index == 2) {
//				break;
//			}
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
