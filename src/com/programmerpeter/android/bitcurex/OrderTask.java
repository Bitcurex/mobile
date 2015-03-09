package com.programmerpeter.android.bitcurex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListView;

public class OrderTask extends BaseTask {
	private JSONObject offers;

	public OrderTask(BaseActivity activity) {
		super(activity);
	}

	@Override
	protected void _doInBackground() {
		String offersUrl = "https://bitcurex.com/api/" + getLowerMarket()
				+ "/orderbook.json";
		offers = JSONParser.getJSONObject(offersUrl);
		return;
	}

	@Override
	protected void _onPostExecute() {
		try {
			JSONArray bids = offers.getJSONArray("bids");
			JSONArray asks = offers.getJSONArray("asks");
			List<OrderItem> bidsList = new ArrayList<OrderItem>(), asksList = new ArrayList<OrderItem>();
			asksList.add(new OrderItem(activity.getResources().getString(R.string.price)
					+ getUpperMarket() + ")",activity.getResources().getString(R.string.amount),
					activity.getResources().getString(R.string.value2) + getUpperMarket() + ")"));
			bidsList.add(new OrderItem(activity.getResources().getString(R.string.price)
					+ getUpperMarket() + ")",activity.getResources().getString(R.string.amount),
					activity.getResources().getString(R.string.value2) + getUpperMarket() + ")"));
			for (int i = 0; i < bids.length(); i++) {
				JSONArray bid = bids.getJSONArray(i);
				bidsList.add(new OrderItem(formatDouble(bid.getDouble(0)),
						formatDouble(bid.getDouble(1)), null));
			}
			for (int i = 0; i < asks.length(); i++) {
				JSONArray ask = asks.getJSONArray(i);
				asksList.add(new OrderItem(formatDouble(ask.getDouble(0)),
						formatDouble(ask.getDouble(1)), null));
			}
			ListView bidList = (ListView) activity.findViewById(R.id.bidList);
			ListView askList = (ListView) activity.findViewById(R.id.askList);
			Collections.reverse(asksList);
			bidList.setAdapter(new OrderAdapter(activity, R.id.bidList,
					bidsList));
			askList.setAdapter(new OrderAdapter(activity, R.id.askList,
					asksList));
			askList.setSelection(askList.getAdapter().getCount() - 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}