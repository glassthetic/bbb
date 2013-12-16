package com.glassthetic.bbb;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.PaginatedListener;
import com.glassthetic.dribbble.api.Shot;

public class EveryoneShotsRequestActivity extends AbstractShotsRequestActivity {

	@Override
	void requestShots(PaginatedListener<Shot> listener, ErrorListener errorListener) {
		Shot.getEveryone(listener, errorListener);
	}

}
