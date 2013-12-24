package com.glassthetic.bbb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;

import com.glassthetic.dribbble.api.Shot;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollView;

public class BitmapWorkerTask extends AsyncTask<Void, Void, Void> {

	private Bitmap mBitmap;
	private Card mCard;
	private CardScrollView mCardScrollView;
	private File mDir;
	private File mFile;
	private Shot mShot;
	private FileOutputStream mStream;
	
	public BitmapWorkerTask(File dir, Shot shot, Bitmap bitmap, Card card, CardScrollView cardScrollView) {
		mDir = dir;
		mShot = shot;
		mBitmap = bitmap;
		mCard = card;
		mCardScrollView = cardScrollView;
	}

	@Override
	protected Void doInBackground(Void... params) {
		mFile = new File(mDir, mShot.id + "");
		
		try {
			mFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mStream = new FileOutputStream(mFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// FIXME use different compression formats based on image extension
		mBitmap.compress(CompressFormat.PNG, 100, mStream);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		try {
			mStream.flush();
			mStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mCard.addImage(Uri.fromFile(mFile));
		mCardScrollView.updateViews(true);
	}
}
