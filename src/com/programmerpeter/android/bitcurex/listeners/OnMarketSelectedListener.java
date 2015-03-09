package com.programmerpeter.android.bitcurex.listeners;

import java.util.Locale;

import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.programmerpeter.android.bitcurex.BaseActivity;
import com.programmerpeter.android.bitcurex.R;

public class OnMarketSelectedListener implements
		AdapterView.OnItemSelectedListener {

	BaseActivity activity;

	public OnMarketSelectedListener(BaseActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Editor editor = activity.prefs.edit();
		String spinnerText = ((Spinner) activity.findViewById(
				R.id.marketSpinner)).getSelectedItem().toString();
		editor.putString("market",
				spinnerText.replace("BTC/", "").toLowerCase(Locale.ENGLISH));
		editor.putInt("marketSelection", position);
		editor.commit();
		activity.reload();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		activity.reload();
	}
}
