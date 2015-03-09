package com.programmerpeter.android.bitcurex;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

public class LogoutActivity extends BaseActivity {
	
	public LogoutActivity() {
		super(R.string.logout_title, R.layout.logout, true);
	}

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
	}
	
	@SuppressLint("NewApi")
	public void click(View v) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String key = prefs.getString("public_key", ""), secret = prefs
				.getString("secret_key", ""), userID = prefs.getString("user_id", "");
		if (key.equals("") || secret.equals("") | userID.equals("")) {
			Toast.makeText(this, R.string.not_logged, Toast.LENGTH_SHORT).show();
			return;
		}
		Editor editor = prefs.edit();
		editor.remove("public_key");
		editor.remove("secret_key");
		editor.remove("user_id");
		editor.commit();
		
		setContentView(R.layout.logout_finished);
		init();
	}

	@Override
	public void reload() {
		startRateTask();
	}
	
}
