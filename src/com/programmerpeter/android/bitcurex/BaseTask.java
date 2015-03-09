package com.programmerpeter.android.bitcurex;

import java.text.DecimalFormat;
import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

public abstract class BaseTask extends AsyncTask<Void, Void, Void> {

	public BaseActivity activity;
	private boolean noConnection;

	public BaseTask(BaseActivity activity) {
		this.activity = activity;
	}

	protected abstract void _doInBackground();

	protected abstract void _onPostExecute();

	public String getUpperMarket() {
		String spinnerText = ((Spinner) activity
				.findViewById(R.id.marketSpinner)).getSelectedItem().toString();
		return spinnerText.replace("BTC/", "");
	}
	
	public String getLowerMarket() {
		String spinnerText = ((Spinner) activity
				.findViewById(R.id.marketSpinner)).getSelectedItem().toString();
		return spinnerText.replace("BTC/", "").toLowerCase(Locale.ENGLISH);
	}
	
	public static String formatDouble(double d) {
		return new DecimalFormat("0.0000").format(d);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		if (isNetworkAvailable()) {
			_doInBackground();
		} else {
			noConnection = true;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (noConnection) {
			Toast.makeText(activity, R.string.no_internet, Toast.LENGTH_SHORT)
					.show();
			return;
		} else {
			_onPostExecute();
		}
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}