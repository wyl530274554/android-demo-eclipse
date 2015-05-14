package com.melon.myapp.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


/**
 * 网络相关
 * 
 * @author Melon
 */
public class NetUtil {

	private static final String TAG = "NetUtil";

	/** 没有网络 */
	public static final int NETWORKTYPE_INVALID = 0;
	/** wap网络 */
	public static final int NETWORKTYPE_WAP = 1;
	/** 2G网络 */
	public static final int NETWORKTYPE_2G = 2;
	/** 3G和3G以上网络，或统称为快速网络 */
	public static final int NETWORKTYPE_3G = 3;
	public static final int NETWORKTYPE_4G = 4;
	/** wifi网络 */
	public static final int NETWORKTYPE_WIFI = 5;

	public enum WifiCipherType {
		WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
	}


	/**
	 * 启动对应的功能模块
	 * 
	 * @param cls
	 */
	public static void startAct(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
	}

	/**
	 * wifi按信号，排序
	 */
	public static void sort(List<ScanResult> mWifis) {
		// 大->小
		Collections.sort(mWifis, new Comparator<ScanResult>() {
			@Override
			public int compare(ScanResult lhs, ScanResult rhs) {
				return rhs.level - lhs.level;
			}
		});
	}

	/**
	 * wifi是否有密码
	 * 
	 * @param sr
	 *            扫描的结果
	 * @return true 有密码
	 */
	public static boolean isWifiHasPwd(ScanResult sr) {
		boolean result = false;
		if (sr.capabilities.toLowerCase().contains("wep") || sr.capabilities.toLowerCase().contains("wpa")) {
			result = true;
		}
		return result;
	}

	/**
	 * 断开wifi连接
	 * 
	 * @param ctx
	 */
	public static void disWifiConn(Context ctx) {
		WifiManager wm = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		WifiInfo connectionInfo = wm.getConnectionInfo();
		String bssid = connectionInfo.getBSSID();
		int networkId = connectionInfo.getNetworkId();
		// wm.disableNetwork(networkId);
		wm.disconnect();
		wm.removeNetwork(networkId);

		// 记录aijee断开的路由
		ctx.getSharedPreferences("disConnWifi", Context.MODE_PRIVATE).edit().putString(bssid, System.currentTimeMillis() + "").commit();
	}

	/**
	 * 判断wifi是否曾经因连上无法上网而断开过
	 * 
	 * @param bssid
	 * @return true 断开过
	 */
	public static boolean isWifiDisconned(Context ctx, String bssid) {
		boolean contains = ctx.getSharedPreferences("disConnWifi", Context.MODE_PRIVATE).contains(bssid);
		return contains;
	}

	/**
	 * 移除wifi配置
	 */
	public static void removeWifiConfigure(Context ctx, int networkId) {
		WifiManager wm = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		wm.removeNetwork(networkId);

	}
}
