package com.programmerpeter.android.bitcurex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlertReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		new AlertTask(context).execute();
	}

}
