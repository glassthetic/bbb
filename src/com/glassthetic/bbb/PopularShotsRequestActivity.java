package com.glassthetic.bbb;

import java.util.List;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.Listener;
import com.glassthetic.dribbble.api.Shot;

public class PopularShotsRequestActivity extends AbstractShotsRequestActivity {

	@Override
	void requestShots(Listener<List<Shot>> listener, ErrorListener errorListener) {
		Shot.getPopular(listener, errorListener);		
	}

}
