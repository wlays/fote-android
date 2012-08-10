package com.lays.fote.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * DialogFragment for letting users pick the date of expense.
 * 
 * @author wlays
 * 
 */
public class DatePickerFragment extends DialogFragment {

	/** Class tag */
	public static final String TAG = DatePickerFragment.class.getSimpleName();

	/** The listener for our DatePickerDialog from the Activity that created us */
	private static DatePickerDialog.OnDateSetListener mListener;

	/** Factory method for creating DatePickerFragments */
	public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
		mListener = listener;
		return new DatePickerFragment();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH);
		int day = rightNow.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), mListener, year, month, day);
	}
}
