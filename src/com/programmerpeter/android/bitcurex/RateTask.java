package com.programmerpeter.android.bitcurex;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

public class RateTask extends BaseTask {

	private String market;
	private int rate;

	public RateTask(BaseActivity activity) {
		super(activity);
	}

	@Override
	protected void _doInBackground() {
		market = activity.prefs.getString("market", "pln");
		String tickerUrl = "https://bitcurex.com/api/" + market
				+ "/ticker.json";
		JSONObject ticker = JSONParser.getJSONObject(tickerUrl);
		try {
			rate = (int) ticker.getDouble("last_tx_price_h");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void _onPostExecute() {
		if (Build.VERSION.SDK_INT >= 11) {
			market = market.toUpperCase(Locale.ENGLISH);
			String title = rate + " " + market;
			View v = activity.getActionBar().getCustomView()
					.findViewById(R.id.rate);
			TextView tv = (TextView) v;
			tv.setText(title);
		}
	}
}