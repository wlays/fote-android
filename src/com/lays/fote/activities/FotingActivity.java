package com.lays.fote.activities;

import java.util.Date;

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
import com.lays.fote.FoteApplication;
import com.lays.fote.R;
import com.lays.fote.fragments.DatePickerFragment;
import com.lays.fote.helpers.DatabaseHelper;
import com.lays.fote.models.Fote;

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
	private long foteTimestamp;

	/** Listener for the DatePickerDialog in DatePickerFragment */
	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			date.setText(month + "/" + day + "/" + year);
			foteTimestamp = new Date().getTime();
			date.setText(Long.toString(foteTimestamp));
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

		// TODO: Move db.createFote to model
		DatabaseHelper db = ((FoteApplication) getApplication()).getDatabaseHelper();
		db.createFote(new Fote(foteAmount, foteComment, foteTimestamp));
		finish();
	}
}
