package com.lays.fote;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;

import com.lays.fote.helpers.DatabaseHelper;

public class FoteApplication extends Application {

	private static final String TAG = FoteApplication.class.toString();

	public static final boolean DEVELOPER_MODE = true;

	private static DatabaseHelper database;

	public void onCreate() {
		if (DEVELOPER_MODE) {
			StrictMode.enableDefaults();
		}
		super.onCreate();
		// Initialize global variables here
		Log.i(TAG, "Fote Application starting...");
		database = new DatabaseHelper(this);
	}

	public DatabaseHelper getDatabaseHelper() {
		return database;
	}

	/**
	 * A global method to check if Internet connection is available
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
