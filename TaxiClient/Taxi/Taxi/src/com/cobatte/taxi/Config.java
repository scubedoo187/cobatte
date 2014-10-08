package com.cobatte.taxi;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Config {
	Activity thisActivity;

	public Config(Activity isActivity) {
		thisActivity = isActivity;
	}

	boolean isNetworkAvailable() {
		boolean available = false;

		ConnectivityManager conn = (ConnectivityManager) thisActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conn.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isAvailable())
			available = true;

		return available;
	}
}
