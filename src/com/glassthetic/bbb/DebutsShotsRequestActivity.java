package com.glassthetic.bbb;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.PaginatedListener;
import com.glassthetic.dribbble.api.Shot;

public class DebutsShotsRequestActivity extends AbstractShotsRequestActivity {

	@Override
	void requestShots(PaginatedListener<Shot> listener, ErrorListener errorListener) {
		Shot.getDebuts(listener, errorListener);
	}

}
