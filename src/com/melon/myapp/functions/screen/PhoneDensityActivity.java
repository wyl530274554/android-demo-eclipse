package com.melon.myapp.functions.screen;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

/**
 * Created by melon on 2015/6/22.
 */
public class PhoneDensityActivity extends BaseActivity {
    private TextView tvContent;

    @Override
    protected void initData() {
        showDesity();
    }

    private void showDesity() {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);

        tvContent.setText("density: " + metrics.density + "\ndensityDpi: " + metrics.densityDpi
                        + "\nheightPixels: " + metrics.heightPixels
                        + "\nwidthPixels: " + metrics.widthPixels
                        + "\nscaledDensity: " + metrics.scaledDensity
                        + "\nxdpi: " + metrics.xdpi
                        + "\nydpi: " + metrics.ydpi
        );
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_phone_density);

        tvContent = (TextView) findViewById(R.id.tv_phone_density_content);
    }

    @Override
    public void onClick(View v) {

    }
}
