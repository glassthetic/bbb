package com.glassthetic.bbb;

import java.util.List;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.PaginatedListener;
import com.glassthetic.dribbble.api.Shot;

public class PopularShotsRequestActivity extends AbstractShotsRequestActivity {

	@Override
	void requestShots(PaginatedListener<List<Shot>> listener, ErrorListener errorListener) {
		Shot.getPopular(listener, errorListener);		
	}

}
