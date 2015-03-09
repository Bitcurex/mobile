package com.programmerpeter.android.bitcurex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
	
	public LoginActivity() {
		super(R.string.login_title, R.layout.login, true);
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
	}
	
	@SuppressLint("NewApi")
	public void click(View v) {
		switch (v.getId()) {
		case R.id.next1:
			setContentView(R.layout.keys);
			init();
			break;
		case R.id.next2:
			String secret = ((EditText) findViewById(R.id.private_key))
					.getText().toString();
			String key = ((EditText) findViewById(R.id.public_key)).getText()
					.toString();
			String userID = ((EditText) findViewById(R.id.user_id)).getText()
					.toString();
			if (key.equals("") || secret.equals("") || userID.equals("")) {
				Toast.makeText(this, R.string.no_key, Toast.LENGTH_SHORT)
						.show();
			} else {
				findViewById(R.id.next2).setEnabled(false);
				new KeyTask(this, key, secret, userID).execute();
			}
			break;
		case R.id.scan_private:
			try {
			    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			    startActivityForResult(intent, 1);
			} catch (Exception e) {
			    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
			    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
			    startActivity(marketIntent);
			}
			break;
		case R.id.scan_public:
			try {
			    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			    startActivityForResult(intent, 0);
			} catch (Exception e) {
			    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
			    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
			    startActivity(marketIntent);
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == 0 && resultCode == RESULT_OK) {
	        ((EditText) findViewById(R.id.public_key)).setText(data.getStringExtra("SCAN_RESULT"));
	    }
	    if (requestCode == 1 && resultCode == RESULT_OK) {
	    	((EditText) findViewById(R.id.private_key)).setText(data.getStringExtra("SCAN_RESULT"));
	    }
	}
	
	@Override
	public void reload() {
		startRateTask();
	}
}
