package com.programmerpeter.android.bitcurex;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListView;

public class MarketTask extends BaseTask {
	private JSONArray offers;

	public MarketTask(BaseActivity activity) {
		super(activity);
	}

	private String s(int id) {
		return activity.getResources().getString(id);
	}

	protected void _onPostExecute() {
		List<MarketItem> list = new ArrayList<MarketItem>();
		list.add(new MarketItem(null, s(R.string.price) + getUpperMarket() + ")",
				s(R.string.amount), s(R.string.type), s(R.string.date),
				s(R.string.value2) + getUpperMarket() + ")"));
		for (int i = 0; i < offers.length(); i++) {
			try {
				JSONObject offer = offers.getJSONObject(i);
				String id = offer.getString("tid");
				String price = formatDouble(offer.getDouble("price"));
				String type = offer
						.getString("type")
						.replace("1",
								activity.getResources().getString(R.string.bid))
						.replace("2",
								activity.getResources().getString(R.string.ask));
				String amount = formatDouble(offer.getDouble("amount"));
				long dv = offer.getLong("date") * 1000;
				Date df = new Date(dv);
				String date = new SimpleDateFormat("HH:mm", Locale.ENGLISH)
						.format(df);
				String volume = formatDouble(offer.getDouble("amount")
						* offer.getDouble("price"));
				list.add(new MarketItem(id, price, amount, type, date, volume));
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		ListView lv = (ListView) activity.findViewById(R.id.marketList);
		lv.setAdapter(new MarketAdapter(activity, R.id.marketList, list));
	}

	@Override
	protected void _doInBackground() {
		String offersUrl = "https://bitcurex.com/api/" + getLowerMarket()
				+ "/trades.json";
		offers = JSONParser.getJSONArray(offersUrl);

	}
}