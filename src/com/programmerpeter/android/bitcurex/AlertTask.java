package com.programmerpeter.android.bitcurex;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

public class AlertTask extends AsyncTask<Void, Void, Void> {

	private NotificationManager mNotificationManager;
	private Vibrator vibrator;
	private Context context;
	private SharedPreferences prefs;

	public AlertTask(Context context) {
		this.context = context;
	}

	private void makeNotification(int titleID, int textID, double number,
			String market, int id) {
		Intent notificationIntent = new Intent(context, TradeActivity.class);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(context.getResources().getString(titleID))
				.setContentText(
						context.getResources().getString(textID) + " "
								+ Integer.valueOf((int) number) + market + "!")
				.setContentIntent(intent);
		mNotificationManager.notify(id, mBuilder.build());
		int way = prefs.getInt("notification", 1);
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(context, notification);
		if (way == 0)
			vibrator.vibrate(1000);
		else if (way == 1) {
			r.play();
		} else if (way == 2) {
			vibrator.vibrate(1000);
			r.play();
		}
	}

	@Override
	protected Void doInBackground(Void... arg) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		float maxAlertPLN = prefs.getFloat("maxAlertPLN", 9999);
		float minAlertPLN = prefs.getFloat("minAlertPLN", 0);
		float maxAlertEUR = prefs.getFloat("maxAlertEUR", 9999);
		float minAlertEUR = prefs.getFloat("minAlertEUR", 0);
		float maxAlertUSD = prefs.getFloat("maxAlertUSD", 9999);
		float minAlertUSD = prefs.getFloat("minAlertUSD", 0);
		boolean alert = prefs.getBoolean("alert", false);
		boolean maxAlertEnabledPLN = prefs.getBoolean("maxAlertEnabledPLN",
				false);
		boolean minAlertEnabledPLN = prefs.getBoolean("minAlertEnabledPLN",
				false);
		boolean maxAlertEnabledEUR = prefs.getBoolean("maxAlertEnabledEUR",
				false);
		boolean minAlertEnabledEUR = prefs.getBoolean("minAlertEnabledEUR",
				false);
		boolean maxAlertEnabledUSD = prefs.getBoolean("maxAlertEnabledUSD",
				false);
		boolean minAlertEnabledUSD = prefs.getBoolean("minAlertEnabledUSD",
				false);
		Editor editor = prefs.edit();
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (alert && isNetworkAvailable()) {
			JSONObject tickerPLN = JSONParser
					.getJSONObject("https://bitcurex.com/api/pln/ticker.json");
			JSONObject tickerEUR = JSONParser
					.getJSONObject("https://bitcurex.com/api/eur/ticker.json");
			JSONObject tickerUSD = JSONParser
					.getJSONObject("https://bitcurex.com/api/usd/ticker.json");
			try {
				double pln = tickerPLN.getDouble("last_tx_price_h");
				double eur = tickerEUR.getDouble("last_tx_price_h");
				double usd = tickerUSD.getDouble("last_tx_price_h");
				if (pln >= maxAlertPLN && (!maxAlertEnabledPLN)) {
					makeNotification(R.string.notificationTitle,
							R.string.notificationMax, maxAlertPLN, "PLN", 0);
					editor.putBoolean("maxAlertEnabledPLN", true);
					editor.commit();
				} else if (pln < maxAlertPLN) {
					editor.remove("maxAlertEnabledPLN");
					editor.commit();
				}
				if (pln <= minAlertPLN && (!minAlertEnabledPLN)) {
					makeNotification(R.string.notificationTitle,
							R.string.notificationMin, minAlertPLN, "PLN", 1);
					editor.putBoolean("minAlertEnabledPLN", true);
					editor.commit();
				} else if (pln > minAlertPLN) {
					editor.remove("minAlertEnabledPLN");
					editor.commit();
				}
				if (eur >= maxAlertEUR && (!maxAlertEnabledEUR)) {
					makeNotification(R.string.notificationTitle,
							R.string.notificationMax, maxAlertEUR, "EUR", 2);
					editor.putBoolean("maxAlertEnabledEUR", true);
					editor.commit();
				} else if (eur < maxAlertEUR) {
					editor.remove("maxAlertEnabledEUR");
					editor.commit();
				}
				if (eur <= minAlertEUR && (!minAlertEnabledEUR)) {
					makeNotification(R.string.notificationTitle,
							R.string.notificationMin, minAlertEUR, "EUR", 3);
					editor.putBoolean("minAlertEnabledEUR", true);
					editor.commit();
				} else if (eur > minAlertEUR) {
					editor.remove("minAlertEnabledEUR");
					editor.commit();
				}
				if (usd >= maxAlertUSD && (!maxAlertEnabledUSD)) {
					makeNotification(R.string.notificationTitle,
							R.string.notificationMax, maxAlertUSD, "USD", 4);
					editor.putBoolean("maxAlertEnabledUSD", true);
					editor.commit();
				} else if (usd < maxAlertUSD) {
					editor.remove("maxAlertEnabledUSD");
					editor.commit();
				}
				if (usd <= minAlertUSD && (!minAlertEnabledUSD)) {
					makeNotification(R.string.notificationTitle,
							R.string.notificationMin, minAlertUSD, "USD", 5);
					editor.putBoolean("minAlertEnabledUSD", true);
					editor.commit();
				} else if (usd > minAlertUSD) {
					editor.remove("minAlertEnabledUSD");
					editor.commit();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}