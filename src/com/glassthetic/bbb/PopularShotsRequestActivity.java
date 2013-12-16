package com.glassthetic.bbb;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.PaginatedListener;
import com.glassthetic.dribbble.api.Shot;

public class PopularShotsRequestActivity extends AbstractShotsRequestActivity {

	@Override
	void requestShots(PaginatedListener<Shot> listener, ErrorListener errorListener) {
		Shot.getPopular(listener, errorListener);		
	}

}
