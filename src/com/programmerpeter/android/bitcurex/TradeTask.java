package com.programmerpeter.android.bitcurex;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TradeTask extends BaseTask {

	private boolean notLogged = false;
	private String type;
	private JSONObject response;

	public TradeTask(BaseActivity activity, String type) {
		super(activity);
		this.type = type;
	}

	@Override
	protected void _doInBackground() {
		String key = activity.prefs.getString("public_key", ""), secret = activity.prefs
				.getString("secret_key", ""), userID = activity.prefs
				.getString("user_id", "");
		if (key.equals("") || secret.equals("") | userID.equals("")) {
			notLogged = true;
			return;
		}
		response = JSONParser.getJSONObject("https://api.bitcurex.com/balance/"
				+ userID + "/" + key + "/" + secret);
		try {
			if (response.getString("status").equals("ok")) {
				 Map<String, String> params = new HashMap<String, String>();
				String spinnerText = ((Spinner) activity
						.findViewById(R.id.marketSpinner)).getSelectedItem()
						.toString();
				String market = spinnerText.replace("BTC/", "").toLowerCase(
						Locale.ENGLISH);
				String amount = ((EditText) activity.findViewById(R.id.value))
						.getText().toString().replace(",", ".");
				String rate = ((EditText) activity.findViewById(R.id.rate2))
						.getText().toString().replace(",", ".");
				;
				params.put("offer_type",
						type.replace("sell", "ask").replace("buy", "bid"));
				params.put("volume", amount);
				params.put("limit", rate);
				params.put("api_secret", secret);
				JSONObject send = new JSONObject((Map<String, String>) params);
				response = JSONParser.postJSONObject(send,
						"https://api.bitcurex.com/offer/" + market + "/"
								+ userID + "/" + key);
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void _onPostExecute() {
		if (notLogged) {
			Toast.makeText(activity, R.string.not_logged, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		try {
			if (response.getString("status").equals("<Response [201]>")) {
				Toast.makeText(activity, R.string.transaction_made,
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}