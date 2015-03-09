package com.programmerpeter.android.bitcurex;

import com.programmerpeter.android.bitcurex.listeners.OnMarketSelectedListener;

import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

public class TradeActivity extends BaseActivity {

	public TradeActivity() {
		super(R.string.trade_title, R.layout.trade, false);
	}

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setText(R.id.buyText, "...");
		setText(R.id.sellText, "...");
		setText(R.id.lastText, "...");
		setText(R.id.volumeText, "...");
		setText(R.id.minimumText, "...");
		setText(R.id.maximumText, "...");
		initSpinner(R.id.marketSpinner, R.array.market,
				new OnMarketSelectedListener(this),
				prefs.getInt("marketSelection", 0));
	}

	public void click(View v) {
		switch (v.getId()) {
		case R.id.buyButton:
			new TradeTask(this, "buy").execute();
			break;
		case R.id.sellButton:
			new TradeTask(this, "sell").execute();
			break;
		case R.id.order:
			String amount = ((TextView) v.findViewById(R.id.amount)).getText()
					.toString();
			String rate = ((TextView) v.findViewById(R.id.rate)).getText()
					.toString();
			((EditText) findViewById(R.id.value)).setText(amount);
			((EditText) findViewById(R.id.rate2)).setText(rate);
			break;
		case R.id.btcAmount:
			String btc = ((TextView) v).getText().toString();
			if (btc.equals(""))
				return;
			btc = btc.substring(0, btc.length() - 4);
			((EditText) findViewById(R.id.value)).setText(btc);
			break;
		case R.id.fiatAmount:
			String fiat = ((TextView) findViewById(R.id.fiatAmount)).getText()
					.toString().replace(",", ".");
			if (fiat.equals(""))
				return;
			fiat = fiat.substring(0, fiat.length() - 4);
			String rate2 = ((TextView) findViewById(R.id.buyText)).getText()
					.toString().replace(",", ".");;
			if (rate2.equals("") || rate2.equals("..."))
				return;
			rate2 = rate2.substring(0, rate2.length() - 4);
			((EditText) findViewById(R.id.value)).setText(formatDouble(Double
					.valueOf(fiat) / Double.valueOf(rate2)));
			break;
		}
	}

	@Override
	public void reload() {
		startRateTask();
		SmallBalanceTask smallBalance = new SmallBalanceTask(this);
		smallBalance.execute();
		TickerTask ticker = new TickerTask(this);
		ticker.execute();
		OrderTask order = new OrderTask(this);
		order.execute();
	}
}
