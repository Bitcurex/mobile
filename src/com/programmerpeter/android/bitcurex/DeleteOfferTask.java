package com.programmerpeter.android.bitcurex;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class DeleteOfferTask extends AsyncTask<Void, Void, Void> {
	
	private String id;
	private AccountActivity activity;
	private boolean notLogged = false;
	private JSONObject response;
	
	public DeleteOfferTask(AccountActivity activity, String id) {
		this.activity = activity;
		this.id = id;
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (isNetworkAvailable()) {
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(activity);
			String key = prefs.getString("public_key", ""), secret = prefs
					.getString("secret_key", ""), userID = prefs.getString("user_id", "");
			if (key.equals("") || secret.equals("") | userID.equals("")) {
				notLogged = true;
				return null;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("offer_id", id);
			map.put("api_secret", secret);
			JSONObject post = new JSONObject(map);
			response = JSONParser.deleteJSONObject(post, "https://api.bitcurex.com/offer/del/"+userID+"/"+key);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (notLogged) {
			Toast.makeText(activity, R.string.not_logged,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (!isNetworkAvailable()) {
			Toast.makeText(activity, R.string.no_internet,
					Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			if(response.getString("status").equals("<Response [200]>")){
				Toast.makeText(activity, R.string.offer_canceled, Toast.LENGTH_SHORT).show();
				OffersTask offers = new OffersTask(activity);
				offers.execute();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}
