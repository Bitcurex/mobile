package com.programmerpeter.android.bitcurex;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences.Editor;
import android.content.res.Resources.NotFoundException;
import android.widget.Toast;

public class KeyTask extends BaseTask {
	private String key, secret, userID;
	private JSONObject response;

	public KeyTask(BaseActivity activity, String key, String secret,
			String userID) {
		super(activity);
		this.key = key;
		this.secret = secret;
		this.userID = userID;
	}

	@Override
	protected void _doInBackground() {
		response = JSONParser.getJSONObject("https://api.bitcurex.com/balance/"
				+ userID + "/" + key + "/" + secret);
	}

	@Override
	protected void _onPostExecute() {
		try {
			if (response.getString("status").equals("ok")) {
				activity.setContentView(R.layout.logged);
				activity.init();
				Editor editor = activity.prefs.edit();
				editor.putString("secret_key", secret);
				editor.putString("public_key", key);
				editor.putString("user_id", userID);
				editor.commit();
				return;
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Toast.makeText(activity, R.string.invalid_keys, Toast.LENGTH_SHORT)
				.show();
		activity.findViewById(R.id.next2).setEnabled(true);
	}
}