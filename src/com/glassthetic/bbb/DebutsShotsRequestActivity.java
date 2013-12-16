package com.glassthetic.bbb;

import java.util.List;

import com.glassthetic.dribbble.api.ErrorListener;
import com.glassthetic.dribbble.api.PaginatedListener;
import com.glassthetic.dribbble.api.Shot;

public class DebutsShotsRequestActivity extends AbstractShotsRequestActivity {

	@Override
	void requestShots(PaginatedListener<List<Shot>> listener, ErrorListener errorListener) {
		Shot.getDebuts(listener, errorListener);
	}

}
