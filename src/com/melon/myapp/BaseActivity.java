package com.melon.myapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener {
	public Handler mHandler = new Handler();
	public SharedPreferences mSp ;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSp = getSharedPreferences("config", MODE_PRIVATE);
		initView();
		initData();
	}

	protected abstract void initView();
	protected abstract void initData();
}
