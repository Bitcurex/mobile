package com.programmerpeter.android.bitcurex;

import java.text.DecimalFormat;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OrderAdapter extends ArrayAdapter<OrderItem> {

	private List<OrderItem> OrderItems;
	private Context context;

	public OrderAdapter(Context context, int textViewResourceId,
			List<OrderItem> OrderItems) {
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
			v = vi.inflate(R.layout.order_list, null);
		}

		OrderItem OrderItem = OrderItems.get(position);

		if (OrderItem != null) {
			TextView rate = (TextView) v.findViewById(R.id.rate);
			TextView amount = (TextView) v.findViewById(R.id.amount);
			TextView rate_mul_amount = (TextView) v
					.findViewById(R.id.rate_mul_amount);
			rate.setText(OrderItem.getrate());
			amount.setText(OrderItem.getAmount());
			if(OrderItem.getMul() == null) {
				float result = Float.parseFloat(OrderItem.getrate().replace(',', '.'))
						* Float.parseFloat(OrderItem.getAmount().replace(',', '.'));
				rate_mul_amount.setText(new DecimalFormat("0.0000").format(result));
			} else {
				v.setClickable(false);
				rate_mul_amount.setText(OrderItem.getMul());
			}
		}

		return v;
	}
}