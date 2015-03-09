package com.programmerpeter.android.bitcurex;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AccountActivity extends BaseActivity {

	public AccountActivity() {
		super(R.string.account_title, R.layout.account, true);
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		initSpinner(R.id.languageSpinner, R.array.language,
				new OnLanguageSelectedListener(), prefs.getInt("languageSelection", 0));
		initSpinner(R.id.refreshSpinner, R.array.refresh,
				new OnRefreshSelectedListener(), prefs.getInt("refreshSelection", 1));
	}

	private void setLocale(String lang) {
		Locale myLocale = new Locale(lang);
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);
		Intent refresh = new Intent(this, AccountActivity.class);
		startActivity(refresh);
	}

	private class OnLanguageSelectedListener implements
			AdapterView.OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if (position == prefs.getInt("languageSelection", 0))
				return;
			Editor e = prefs.edit();
			e.putInt("languageSelection", position);
			e.commit();
			if (position == 0)
				setLocale("pl");
			else if (position == 1)
				setLocale("en");
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	private class OnRefreshSelectedListener implements
			AdapterView.OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			Editor e = prefs.edit();
			e.putInt("refreshSelection", position);
			e.commit();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	public void click(View v) {
		View parentRow = (View) v.getParent();
		ListView listView = (ListView) parentRow.getParent();
		final int position = listView.getPositionForView(parentRow);
		OfferAdapter adapter = (OfferAdapter) listView.getAdapter();
		String id = adapter.getID(position);
		DeleteOfferTask delete = new DeleteOfferTask(this, id);
		delete.execute();
	}

	@Override
	public void reload() {
		BalanceTask balance = new BalanceTask(this);
		balance.execute();
		OffersTask offers = new OffersTask(this);
		offers.execute();
		RateTask rate = new RateTask(this);
		rate.execute();
	}
}
