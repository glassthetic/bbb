package com.glassthetic.bbb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
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
	private List<File> mFiles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		List<Shot> shots = intent.getParcelableArrayListExtra(Constants.SHOTS_PARCEL);
		
		mCards = new ArrayList<Card>();
		mFiles = new ArrayList<File>();
		
		mCardScrollView = new CardScrollView(this);
		mCardScrollView.setOnItemClickListener(this);
		mAdapter = new ShotCardScrollAdapter();
		mCardScrollView.setAdapter(mAdapter);
		
		Card card;
		String numberOfLikes;
		int resourceId = R.string.number_of_likes;
		
		final File cacheDir = new File(getBaseContext().getCacheDir(), "images");
		cacheDir.mkdirs();
		
		for (int i = 0; i < shots.size(); i++) {
			Shot shot = shots.get(i);
			
			card = new Card(this);
			card.setText(shot.title);
			numberOfLikes = getResources().getString(resourceId, shot.likesCount);
			card.setFootnote(numberOfLikes);
			card.setImageLayout(Card.ImageLayout.FULL);
			
			mCards.add(card);
			
			
			File file = new File(cacheDir, shot.id + "");
			
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mFiles.add(file);
			
			
			shot.getImage(new IndexedListener<Bitmap>(i) {

				@Override
				public void onResponse(Bitmap bitmap) {
					int index = this.getIndex();				
					File file = mFiles.get(index);
					
					try {
						FileOutputStream stream = new FileOutputStream(file);
						// FIXME use different compression formats based on image extension
						bitmap.compress(CompressFormat.PNG, 0, stream);
						stream.flush();
						stream.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                
					Card card = (Card) mAdapter.getItem(index);
      				card.addImage(Uri.fromFile(file));
      				mCardScrollView.updateViews(true);
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
