package com.programmerpeter.android.bitcurex;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {

	private ActionBarDrawerToggle actionBarDrawerToggle;
	private int titleID, layoutID;
	private boolean reloadOnCreate;
	public SharedPreferences prefs;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			int interval = 10;
			switch (prefs.getInt("refreshSelection", 2)) {
			case 0:
				interval = 5;
				break;
			case 1:
				interval = 10;
				break;
			case 2:
				interval = 30;
				break;
			case 3:
				interval = 60;
				break;
			case 4:
				return;
			}
			interval *= 1000;
			reload();
			handler.postDelayed(this, interval);
		}
	};

	public BaseActivity(int titleID, int layoutID, boolean reloadOnCreate) {
		this.titleID = titleID;
		this.layoutID = layoutID;
		this.reloadOnCreate = reloadOnCreate;
	}

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(layoutID);
		setTitle(titleID);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		init();
		if (reloadOnCreate)
			reload();
		runnable.run();
	}

	@SuppressLint("NewApi")
	public void init() {
		DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ListView drawerList = (ListView) findViewById(R.id.menu);
		MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(),
				new ArrayList<String>(Arrays.asList(getResources()
						.getStringArray(R.array.menu))));
		drawerList.setAdapter(menuAdapter);
		drawerList.setOnItemClickListener(new MenuListener(this));
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.open, R.string.close);
		drawerLayout.setDrawerListener(actionBarDrawerToggle);
		if (Build.VERSION.SDK_INT >= 11) {
			ActionBar ab = getActionBar();
			ab.setDisplayShowCustomEnabled(true);
			ab.setCustomView(R.layout.action_bar);
			ab.setDisplayHomeAsUpEnabled(true);
			View v = getActionBar().getCustomView();
			TextView titleTxtView = (TextView) v.findViewById(R.id.title);
			titleTxtView.setText(titleID);
		}
	}

	public void startRateTask() {
		RateTask rate = new RateTask(this);
		rate.execute();
	}

	public void initSpinner(int spinnerID, int spinnerItemsID,
			OnItemSelectedListener listener, int selection) {
		Spinner spinner = (Spinner) findViewById(spinnerID);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, spinnerItemsID, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(listener);
		spinner.setSelection(selection);
	}

	public void setText(int id, String text) {
		((TextView) findViewById(id)).setText(text);
	}

	public abstract void reload();

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onDestroy() {
		handler.removeCallbacks(runnable);
		super.onDestroy();
	}
	
	public static String formatDouble(double d) {
		return new DecimalFormat("0.0000").format(d);
	}
}
