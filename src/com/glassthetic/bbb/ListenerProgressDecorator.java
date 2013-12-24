package com.glassthetic.bbb;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.View;
import android.widget.ProgressBar;

import com.glassthetic.dribbble.api.Listener;

public class ListenerProgressDecorator<T> extends ListenerDecorator<T> {
	
	private ProgressBar mProgressBar;
	
	public ListenerProgressDecorator(Activity activity, Listener<T> listener) {
		super(listener);
		activity.setContentView(R.layout.loading);
		mProgressBar = (ProgressBar) activity.findViewById(R.id.progress_bar);
		mProgressBar.setVisibility(View.VISIBLE);
		mProgressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, Mode.SRC_IN);
	}
	
	@Override
	public void onResponse(T response) {
		listener.onResponse(response);
		mProgressBar.setVisibility(View.GONE);
	}
}

abstract class ListenerDecorator<T> implements Listener<T> {
	protected Listener<T> listener;
	
	public ListenerDecorator(Listener<T> listener) {
		this.listener = listener;
	}
	
	@Override
	public void onResponse(T response) {
		listener.onResponse(response);
	}
}