package com.programmerpeter.android.bitcurex;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AlertActivity extends BaseActivity {

	public AlertActivity() {
		super(R.string.alert_title, R.layout.alert, true);
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		initSpinner(R.id.notificationSpinner, R.array.notification, new OnNotificationSelectedListener(), 0);
		setFloat("maxAlertPLN", R.id.maxAlertPLN);
		setFloat("maxAlertEUR", R.id.maxAlertEUR);
		setFloat("maxAlertUSD", R.id.maxAlertUSD);
		setFloat("minAlertPLN", R.id.minAlertPLN);
		setFloat("minAlertEUR", R.id.minAlertEUR);
		setFloat("minAlertUSD", R.id.minAlertUSD);
		((CheckBox) findViewById(R.id.enableAlert))
				.setChecked(PreferenceManager.getDefaultSharedPreferences(this)
						.getBoolean("alert", false));
	}

	private AlertActivity getThis() {
		return this;
	}
	
	private class OnNotificationSelectedListener implements
			AdapterView.OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			prefs = PreferenceManager
					.getDefaultSharedPreferences(getThis());
			Editor editor = prefs.edit();
			editor.putInt("notification", position);
			editor.commit();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	}

	private float getFloat(int id) {
		return Float
				.valueOf(((EditText) findViewById(id)).getText().toString());
	}

	private void setFloat(String prefName, int id) {
		String pref = String.valueOf(prefs.getFloat(prefName, 0.0f));
		if (pref.equals("0.0"))
			return;
		((EditText) findViewById(id)).setText(pref);
	}

	public void click(View v) {
		prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		try {
			editor.putFloat("maxAlertPLN", getFloat(R.id.maxAlertPLN));
			editor.putFloat("maxAlertEUR", getFloat(R.id.maxAlertEUR));
			editor.putFloat("maxAlertUSD", getFloat(R.id.maxAlertUSD));
			editor.putFloat("minAlertPLN", getFloat(R.id.minAlertPLN));
			editor.putFloat("minAlertEUR", getFloat(R.id.minAlertEUR));
			editor.putFloat("minAlertUSD", getFloat(R.id.minAlertUSD));
			editor.remove("maxAlertEnabledPLN");
			editor.remove("minAlertEnabledPLN");
			editor.remove("maxAlertEnabledEUR");
			editor.remove("minAlertEnabledEUR");
			editor.remove("maxAlertEnabledUSD");
			editor.remove("minAlertEnabledUSD");
			editor.commit();
		} catch (Exception e) {
			Toast.makeText(this, R.string.invalid_data, Toast.LENGTH_SHORT)
					.show();
		}
		editor.putBoolean("alert",
				((CheckBox) findViewById(R.id.enableAlert)).isChecked());
		editor.commit();
		Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(this, AlertReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		am.cancel(pi);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				10 * 1000, pi);
	}

	@Override
	public void reload() {
		RateTask rate = new RateTask(this);
		rate.execute();
	}

}
