package com.melon.myapp.functions.sensor;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.util.ToastUtil;

//摇一摇
public class ShakeOneShakeActivity extends BaseActivity implements SensorEventListener {

	private SensorManager mSensorManager;
	private Vibrator mVibrator;

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_shake_one_shake);
	}

	@Override
	protected void initData() {
		setTitle("摇一摇");
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

		// 加速度传感器注册
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();

		// values[0]:X轴，values[1]：Y轴，values[2]：Z轴
		float[] values = event.values;

		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			/*
			 * 因为一般正常情况下，任意轴数值最大就在9.8~10之间，只有在你突然摇动手机的时候，瞬时加速度才会突然增大或减少。
			 * 所以，经过实际测试，只需监听任一轴的加速度大于14的时候，改变你需要的设置就OK了~~~
			 */
			if ((Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14 || Math.abs(values[2]) > 14)) {
				ToastUtil.showShortToast(getApplicationContext(), "摇动了");
				// 摇动手机后，再伴随震动提示
				mVibrator.vibrate(500);
			}
		}
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(this);
		super.onPause();
	}
}
