package com.programmerpeter.android.bitcurex;

import java.text.DecimalFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SmallBalanceTask extends AsyncTask<Void, Void, Void> {
	private JSONObject response, balance;
	private BaseActivity activity;
	private String market;

	private static String formatDouble(double d) {
		return new DecimalFormat("0.0000").format(d);
	}

	public SmallBalanceTask(BaseActivity tradeActivity) {
		this.activity = tradeActivity;
	}

	@Override
	protected Void doInBackground(Void... params) {
		String spinnerText = ((Spinner) activity
				.findViewById(R.id.marketSpinner)).getSelectedItem().toString();
		market = spinnerText.replace("BTC/", "").toLowerCase(Locale.ENGLISH);
		if (isNetworkAvailable()) {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(activity);
			String key = prefs.getString("public_key", ""), secret = prefs
					.getString("secret_key", ""), userID = prefs.getString(
					"user_id", "");
			if (key.equals("") || secret.equals("") | userID.equals("")) {
				return null;
			}
			response = JSONParser
					.getJSONObject("https://api.bitcurex.com/balance/" + userID
							+ "/" + key + "/" + secret);
			try {
				if (response.getString("status").equals("ok")) {
					balance = response.getJSONObject("data");
					return null;
				}
			} catch (NullPointerException npe) {
				npe.printStackTrace();
				Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT)
						.show();
			} catch (JSONException je) {
				je.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (balance != null) {
			try {
				if (market.equals("pln"))
					setText(R.id.fiatAmount,
							formatDouble(balance.getDouble("pln")) + " PLN");
				if (market.equals("eur"))
					setText(R.id.fiatAmount,
							formatDouble(balance.getDouble("eur")) + " EUR");
				if (market.equals("usd"))
					setText(R.id.fiatAmount,
							formatDouble(balance.getDouble("usd")) + " USD");
				setText(R.id.btcAmount, formatDouble(balance.getDouble("btc"))
						+ " BTC");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void setText(int id, String text) {
		((TextView) activity.findViewById(id)).setText(text);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}