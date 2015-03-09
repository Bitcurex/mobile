package com.programmerpeter.android.bitcurex;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuListener implements OnItemClickListener {

	private Activity context;

	public MenuListener(Activity ctx) {
		context = ctx;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch ((int) id) {
		case 0:
			context.startActivity(new Intent(context, TradeActivity.class));
			context.finish();
			break;
		case 1:
			context.startActivity(new Intent(context, MarketActivity.class));
			context.finish();
			break;
		case 2:
			context.startActivity(new Intent(context, AccountActivity.class));
			context.finish();
			break;
		case 3:
			context.startActivity(new Intent(context, AlertActivity.class));
			context.finish();
			break;
		case 4:
			context.startActivity(new Intent(context, LoginActivity.class));
			context.finish();
			break;
		case 5:
			context.startActivity(new Intent(context, LogoutActivity.class));
			context.finish();
			break;
		}
	}

}
