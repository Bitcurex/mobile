package com.programmerpeter.android.bitcurex;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MarketAdapter extends ArrayAdapter<MarketItem> {

	private List<MarketItem> OrderItems;
	private Context context;

	public MarketAdapter(Context context, int textViewResourceId,
			List<MarketItem> OrderItems) {
		super(context, textViewResourceId, OrderItems);
		this.OrderItems = OrderItems;
		this.context = context;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.market_list, null);
		}

		MarketItem OrderItem = OrderItems.get(position);

		if (OrderItem != null) {
			TextView rate = (TextView) v.findViewById(R.id.price);
			TextView amount = (TextView) v.findViewById(R.id.amount);
			TextView value = (TextView) v.findViewById(R.id.value);
			TextView date = (TextView) v.findViewById(R.id.date);
			TextView type = (TextView) v.findViewById(R.id.type);
			rate.setText(OrderItem.getPrice());
			amount.setText(OrderItem.getAmount());
			date.setText(OrderItem.getDate());
			type.setText(OrderItem.getType());
			value.setText(OrderItem.getVolume());
		}

		return v;
	}
}