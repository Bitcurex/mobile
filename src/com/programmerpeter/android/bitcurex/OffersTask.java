package com.programmerpeter.android.bitcurex;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListView;
import android.widget.Toast;

public class OffersTask extends BaseTask {
	private JSONArray plnoffers, euroffers, usdoffers;
	private JSONObject responsepln, responseeur, responseusd;

	public OffersTask(BaseActivity activity) {
		super(activity);
	}

	@Override
	protected void _doInBackground() {
		String key = activity.prefs.getString("public_key", ""), secret = activity.prefs
				.getString("secret_key", ""), userID = activity.prefs
				.getString("user_id", "");
		if (key.equals("") || secret.equals("") | userID.equals("")) {
			return;
		}
		responsepln = JSONParser
				.getJSONObject("https://api.bitcurex.com/offers/pln/" + userID
						+ "/" + key + "/" + secret);
		responseeur = JSONParser
				.getJSONObject("https://api.bitcurex.com/offers/eur/" + userID
						+ "/" + key + "/" + secret);
		responseusd = JSONParser
				.getJSONObject("https://api.bitcurex.com/offers/usd/" + userID
						+ "/" + key + "/" + secret);
		try {
			if (responsepln.getString("status").equals("ok")
					&& responseeur.getString("status").equals("ok")
					&& responseusd.getString("status").equals("ok")) {
				euroffers = responseeur.getJSONArray("data");
				plnoffers = responsepln.getJSONArray("data");
				usdoffers = responseusd.getJSONArray("data");
				return;
			}
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
		} catch (JSONException je) {
			je.printStackTrace();
		}
	}

	@Override
	protected void _onPostExecute() {
		if (plnoffers != null && euroffers != null && usdoffers != null) {
			List<OfferItem> list = new ArrayList<OfferItem>();
			list.add(new OfferItem("ID", activity.getResources().getString(
					R.string.rate), activity.getResources().getString(
					R.string.type), activity.getResources().getString(
					R.string.currency), activity.getResources().getString(
					R.string.value)));
			for (int i = 0; i < plnoffers.length(); i++) {
				try {
					JSONObject offer = plnoffers.getJSONObject(i);
					String id = offer.getString("id");
					String limit = formatDouble(offer.getDouble("limit"));
					String type = offer
							.getString("type")
							.replace(
									"bid",
									activity.getResources().getString(
											R.string.bid))
							.replace(
									"ask",
									activity.getResources().getString(
											R.string.ask));
					String currency = offer.getString("currency").toUpperCase(
							Locale.ENGLISH);
					String volume = formatDouble(offer.getDouble("volume"));
					list.add(new OfferItem(id, limit, type, currency, volume));
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			for (int i = 0; i < euroffers.length(); i++) {
				try {
					JSONObject offer = euroffers.getJSONObject(i);
					String id = offer.getString("id");
					String limit = formatDouble(offer.getDouble("limit"));
					String type = offer.getString("type")
							.replace("bid", "Kupno").replace("ask", "Sprzeda¿");
					String currency = offer.getString("currency").toUpperCase(
							Locale.ENGLISH);
					String volume = formatDouble(offer.getDouble("volume"));
					list.add(new OfferItem(id, limit, type, currency, volume));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < usdoffers.length(); i++) {
				try {
					JSONObject offer = usdoffers.getJSONObject(i);
					String id = offer.getString("id");
					String limit = formatDouble(offer.getDouble("limit"));
					String type = offer
							.getString("type")
							.replace(
									"bid",
									activity.getResources().getString(
											R.string.bid))
							.replace(
									"ask",
									activity.getResources().getString(
											R.string.ask));
					String currency = offer.getString("currency").toUpperCase(
							Locale.ENGLISH);
					String volume = formatDouble(offer.getDouble("volume"));
					list.add(new OfferItem(id, limit, type, currency, volume));
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			ListView listView = (ListView) activity.findViewById(R.id.offers);
			listView.setAdapter(new OfferAdapter(activity, R.id.offers, list));
		}
	}
}
