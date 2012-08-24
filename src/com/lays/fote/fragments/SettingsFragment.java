package com.lays.fote.fragments;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.lays.fote.FoteApplication;
import com.lays.fote.R;

@TargetApi(11)
public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    // private static final String TAG = SettingsFragment.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private ListPreference mSorting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.xml.preferences);
	mSharedPreferences = getPreferenceScreen().getSharedPreferences();
	mSorting = (ListPreference) getPreferenceScreen().findPreference(FoteApplication.PREF_SORTING_KEY);
    }

    @Override
    public void onResume() {
	super.onResume();
	mSorting.setSummary(mSharedPreferences.getString(FoteApplication.PREF_SORTING_KEY, FoteApplication.PREF_SORTING_DEFAULT_VALUE));
	mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
	super.onPause();
	mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	if (key.equals(FoteApplication.PREF_SORTING_KEY)) {
	    mSorting.setSummary(sharedPreferences.getString(key, FoteApplication.PREF_SORTING_DEFAULT_VALUE));
	}
    }
}