package com.programmerpeter.android.bitcurex;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class TickerTask extends BaseTask {
	private JSONObject ticker;
	private String market;
	
	public TickerTask(BaseActivity activity) {
		super(activity);
	}

	@Override
	protected void _doInBackground() {
		market = getLowerMarket();
		String tickerUrl = "https://bitcurex.com/api/" + market
				+ "/ticker.json";
		ticker = JSONParser.getJSONObject(tickerUrl);
		return;
	}

	@Override
	protected void _onPostExecute() {
		market = market.toUpperCase(Locale.ENGLISH);
		try {
			activity.setText(R.id.buyText, formatDouble(ticker.getDouble("best_bid_h")) + " "
					+ market + " ");
			activity.setText(R.id.sellText, formatDouble(ticker.getDouble("best_ask_h")) + " "
					+ market + " ");
			activity.setText(R.id.lastText, formatDouble(ticker.getDouble("last_tx_price_h")) + " "
					+ market + " ");
			activity.setText(R.id.volumeText, (int)ticker.getDouble("last24_volume_h") + " "
					+ "BTC" + " ");
			activity.setText(R.id.minimumText, formatDouble(ticker.getDouble("lowest_tx_price_h")) + " "
					+ market + " ");
			activity.setText(R.id.maximumText, formatDouble(ticker.getDouble("highest_tx_price_h")) + " "
					+ market + " ");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}