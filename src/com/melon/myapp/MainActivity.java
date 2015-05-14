package com.melon.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.melon.myapp.functions.wifi.ShowWifiInfoActivity;
import com.melon.myapp.util.ToastUtil;
import com.melon.myapp.util.ViewHolder;

public class MainActivity extends Activity {
	private String[] items = new String[] { "查看Wifi列表" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GridView gv_main = (GridView) findViewById(R.id.gv_main);

		MyAdapter mAdapter = new MyAdapter();
		gv_main.setAdapter(mAdapter);
		gv_main.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					enterActivity(ShowWifiInfoActivity.class);
					break;
				}
			}

		});
	}

	private void enterActivity(Class<?> clazz) {
		Intent intent = new Intent(getApplication(), clazz);
		startActivity(intent);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_main, parent, false);
			}

			TextView tvName = ViewHolder.get(convertView, R.id.tv_item_main_name);
			tvName.setText(items[position]);
			return convertView;
		}
	}
}
