package com.programmerpeter.android.bitcurex;

import com.programmerpeter.android.bitcurex.listeners.OnMarketSelectedListener;

import android.os.Bundle;

public class MarketActivity extends BaseActivity {

	public MarketActivity() {
		super(R.string.market_title, R.layout.market, false);
	}

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		initSpinner(R.id.marketSpinner, R.array.market,
				new OnMarketSelectedListener(this),
				prefs.getInt("marketSelection", 0));
	}

	@Override
	public void reload() {
		startRateTask();
		MarketTask market = new MarketTask(this);
		market.execute();
	}

}
