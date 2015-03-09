package com.programmerpeter.android.bitcurex;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class BalanceTask extends BaseTask {
	private JSONObject balance;
	private JSONObject response;

	public BalanceTask(BaseActivity activity) {
		super(activity);
	}

	@Override
	protected void _doInBackground() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(activity);
		String key = prefs.getString("public_key", ""), secret = prefs
				.getString("secret_key", ""), userID = prefs.getString(
				"user_id", "");
		if (key.equals("") || secret.equals("") | userID.equals("")) {
			return;
		}
		response = JSONParser.getJSONObject("https://api.bitcurex.com/balance/"
				+ userID + "/" + key + "/" + secret);
		try {
			if (response.getString("status").equals("ok")) {
				balance = response.getJSONObject("data");
				return;
			}
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return;
	}

	@Override
	protected void _onPostExecute() {
		if (balance != null) {
			try {
				activity.setText(R.id.pln_active, formatDouble(balance.getDouble("pln"))
						+ " PLN");
				activity.setText(R.id.eur_active, formatDouble(balance.getDouble("eur"))
						+ " EUR");
				activity.setText(R.id.usd_active, formatDouble(balance.getDouble("usd"))
						+ " USD");
				activity.setText(R.id.btc_active, formatDouble(balance.getDouble("btc"))
						+ " BTC");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}