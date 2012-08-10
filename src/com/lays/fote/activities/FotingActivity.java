package com.lays.fote.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lays.fote.R;
import com.lays.fote.database.FoteDataSource;
import com.lays.fote.fragments.DatePickerFragment;
import com.lays.fote.utilities.FoteCalendar;

/**
 * Activity lets user create a Fote and saves it to the database.
 * 
 * @author wlays
 * 
 */
public class FotingActivity extends SherlockFragmentActivity {

	/** Class tag */
	private static final String TAG = FotingActivity.class.getSimpleName();

	/** Associated views */
	private EditText amount;
	private EditText comment;
	private Button date;

	/** Timestamp variable for listener */
	private FoteCalendar foteDate;

	/** Listener for the DatePickerDialog in DatePickerFragment */
	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			foteDate = new FoteCalendar(year, month, day);
			date.setText(foteDate.getShortenedDateAmerican());
			// Log.i(TAG, "Full date: " + foteDate.getFullDate());
			// Log.i(TAG, "Timestamp saved: " + foteDate.getTimeInMillis());
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foting);
		amount = (EditText) findViewById(R.id.fote_amount);
		comment = (EditText) findViewById(R.id.fote_comment);
		date = (Button) findViewById(R.id.fote_date);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_left_incoming, R.anim.slide_left_outgoing);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_foting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_cancel:
			Log.i(TAG, "Fote creation cancelled");
			finish();
			overridePendingTransition(R.anim.slide_left_incoming, R.anim.slide_left_outgoing);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Handles the onClick of R.id.fote_date Button
	 * 
	 * @param v
	 */
	public void editDate(View v) {
		DialogFragment newFragment = DatePickerFragment.newInstance(datePickerListener);
		newFragment.show(getSupportFragmentManager(), DatePickerFragment.TAG);
	}

	/**
	 * Handles the onClick of R.id.done Button
	 * 
	 * @param v
	 */
	public void createFote(View v) {
		int foteAmount = Integer.parseInt(amount.getText().toString());
		String foteComment = comment.getText().toString();

		// TODO: Fote validations here...

		(new FoteDataSource(this)).createFote(foteAmount, foteComment, foteDate.getTimeInMillis());
		finish();
	}
}
