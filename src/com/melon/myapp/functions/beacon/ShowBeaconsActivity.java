package com.melon.myapp.functions.beacon;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.functions.beacon.IBeaconClass.IBeacon;

public class ShowBeaconsActivity extends BaseActivity {

	private BluetoothAdapter mBluetoothAdapter;
	private Handler mHandler = new Handler();
	private static final long SCAN_PERIOD = 5000;
	private boolean mScanning;
	// Device scan callback.
	public BluetoothAdapter.LeScanCallback mLeScanCallback = null;

	private List<IBeacon> beacons = new ArrayList<IBeacon>();
	private TextView tv_show_beacons;

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_show_beacons);

		tv_show_beacons = (TextView) findViewById(R.id.tv_show_beacons);
	}

	@Override
	protected void initData() {
		setTitle("Beacon列表");
		checkBle();
	}

	private void checkBle() {
		if (Build.VERSION.SDK_INT < 18) {
			Toast.makeText(this, "该手机系统版本太低", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, "该手机不支持BLE", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "该手机支持BLE", Toast.LENGTH_SHORT).show();

			mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
				@SuppressLint("NewApi")
				public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
					IBeacon ibeacon = IBeaconClass.fromScanData(device, rssi, scanRecord);
					beacons.add(ibeacon);
				}
			};
		}
	}

	public void click1(View v) {
		// 开启蓝牙
		// if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
		// Intent enableBtIntent = new
		// Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		// startActivityForResult(enableBtIntent, 0);
		// }

		mBluetoothAdapter.enable();

	}

	public void click2(View v) {
		// 检测BLE
		scanLeDevice(true);
	}

	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					// 显示
					StringBuilder sb = new StringBuilder();
					for (IBeacon beacon : beacons) {
						if(beacon == null || TextUtils.isEmpty(beacon.name)) continue;
						sb.append(beacon.name + "\nmajor:" + beacon.major + "    minor：" + beacon.minor + "\nuuid：" + beacon.proximityUuid+ "\nmac：" + beacon.bluetoothAddress+ "\ntxPower：" + beacon.txPower+"    rssi: "+beacon.rssi + "\n\n");
					}
					tv_show_beacons.setText(sb.toString());
				}
			}, SCAN_PERIOD);

			mScanning = true;
			// UUID uuid1 =
			// UUID.fromString("1D2C98C1-FC24-0F25-E926-0D9944D7897E");
			// UUID uuid2 =
			// UUID.fromString("F000FFC0-0451-4000-B000-000000000000");
			// UUID[] uuids = new UUID[] { uuid1, uuid2 };
			// mBluetoothAdapter.startLeScan(uuids ,mLeScanCallback);
			beacons.clear();
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}
}
