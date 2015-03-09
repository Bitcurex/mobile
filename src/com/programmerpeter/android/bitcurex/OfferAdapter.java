package com.programmerpeter.android.bitcurex;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OfferAdapter extends ArrayAdapter<OfferItem> {

	private List<OfferItem> OfferItems;
	private Context context;

	public OfferAdapter(Context context, int textViewResourceId,
			List<OfferItem> OfferItems) {
		super(context, textViewResourceId, OfferItems);
		this.OfferItems = OfferItems;
		this.context = context;
	}

	public String getID(int position){
		return OfferItems.get(position).getId();
	}
	
	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (position == 0) 
				v = vi.inflate(R.layout.offer_list_header, null);
			else
				v = vi.inflate(R.layout.offer_list, null);
		}
		
		OfferItem offerItem = OfferItems.get(position);

		if (offerItem != null) {
			TextView type = (TextView) v.findViewById(R.id.type);
			TextView currency = (TextView) v.findViewById(R.id.currency);
			TextView volume = (TextView) v.findViewById(R.id.volume);
			TextView limit = (TextView) v.findViewById(R.id.limit);
			if (type != null) {
				type.setText(offerItem.getType());
			}
			if (currency != null) {
				currency.setText(offerItem.getCurrency());
			}
			if (volume != null) {
				volume.setText(offerItem.getVolume());
			}
			if (limit != null) {
				limit.setText(offerItem.getLimit());
			}
		}

		return v;
	}
}