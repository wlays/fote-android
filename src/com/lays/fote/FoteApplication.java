package com.lays.fote;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.text.InputFilter;
import android.util.Log;

import com.lays.fote.database.Database;
import com.lays.fote.utilities.DecimalDigitsInputFilter;

public class FoteApplication extends Application {

	private static final String TAG = FoteApplication.class.getSimpleName();

	public static final String FOTE_KEY = "fote_id";

	public static final boolean DEVELOPER_MODE = true;

	private static Database database;

	public void onCreate() {
		if (DEVELOPER_MODE) {
			StrictMode.enableDefaults();
		}
		super.onCreate();
		// Initialize global variables here
		Log.i(TAG, "Fote Application starting...");
		database = new Database(this);
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

	public static InputFilter[] getFoteInputFilter() {
		return new InputFilter[] { new DecimalDigitsInputFilter(6, 2) };
	}
}
