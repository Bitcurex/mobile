package com.programmerpeter.android.bitcurex;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> menuItems;

	public MenuAdapter(Context context, ArrayList<String> menuItems) {
		this.context = context;
		this.menuItems = menuItems;
	}

	@Override
	public int getCount() {
		return menuItems.size();
	}

	@Override
	public Object getItem(int position) {
		return menuItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.menu_item, null);
		}

		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		txtTitle.setText(menuItems.get(position));

		return convertView;
	}

}
