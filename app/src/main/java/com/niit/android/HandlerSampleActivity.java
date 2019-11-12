package com.niit.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HandlerSampleActivity extends Activity {
	ProgressBar bar;
	TextView tvNum;

	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			bar.incrementProgressBy(5);
			tvNum.setText("Number=" + msg.what);
		}
	};

	boolean isRunning=false;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		bar=(ProgressBar)findViewById(R.id.progress);
		tvNum = (TextView)findViewById(R.id.num);

	}

	public void onStart() {
		super.onStart();
		bar.setProgress(0);
		Thread background=new Thread(new Runnable() {
			public void run() {
				try {
					for (int i=0;i<20 && isRunning;i++) {
						Thread.sleep(1000);
						handler.sendMessage(handler.obtainMessage(i, null));
					}
				}
				catch (Throwable t) {
					//just end the background thread
				}
			}
		});
		isRunning=true;
		background.start();
	}

	public void onStop() {
		super.onStop();
		isRunning=false;
	}
}