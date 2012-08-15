package com.lays.fote.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lays.fote.FoteApplication;
import com.lays.fote.R;
import com.lays.fote.database.FoteDataSource;
import com.lays.fote.database.MonthDataSource;
import com.lays.fote.fragments.DatePickerFragment;
import com.lays.fote.models.Fote;
import com.lays.fote.models.Month;
import com.lays.fote.utilities.FoteCalendar;

public class EditingFoteActivity extends SherlockFragmentActivity {

    /** Class tag */
    private static final String TAG = EditingFoteActivity.class.getSimpleName();

    /** Fote object */
    private Fote mFote;

    /** Associated views */
    private EditText amount;
    private EditText comment;
    private Button date;
    private Button save;

    /** Timestamp variable for listener */
    private FoteCalendar foteDate;
    private FoteCalendar previousFoteDate;

    /** Listener for the DatePickerDialog in DatePickerFragment */
    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
	    foteDate = new FoteCalendar(year, month, day);
	    date.setText(foteDate.getFullDate());
	    // Log.i(TAG, "Full date: " + foteDate.getFullDate());
	    // Log.i(TAG, "Timestamp saved: " + foteDate.getTimeInMillis());
	}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_foting);

	// Get Fote by id from database
	long foteID = getIntent().getLongExtra(FoteApplication.FOTE_KEY, 0);
	mFote = (new FoteDataSource(this)).getFoteById(foteID);

	// init amount
	amount = (EditText) findViewById(R.id.fote_amount);
	amount.setFilters(FoteApplication.getFoteInputFilter());
	amount.setText(String.valueOf(mFote.getAmount()));

	// init comment
	comment = (EditText) findViewById(R.id.fote_comment);
	comment.setText(mFote.getComment());

	// init date
	previousFoteDate = new FoteCalendar(mFote.getDate());
	foteDate = new FoteCalendar(mFote.getDate());
	date = (Button) findViewById(R.id.fote_date);
	date.setText(foteDate.getFullDate());

	// init save
	save = (Button) findViewById(R.id.done);
	save.setOnClickListener(saveClickListener);
	save.setText("SAVE");
    }

    @Override
    public void onBackPressed() {
	super.onBackPressed();
	Log.i(TAG, "Fote Edit cancelled");
	overridePendingTransition(R.anim.slide_left_incoming, R.anim.slide_left_outgoing);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getSupportMenuInflater();
	inflater.inflate(R.menu.activity_editing_fote, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.menu_delete_fote:
	    deleteFote();
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
     * OnClickListener of R.id.done Button
     */
    private OnClickListener saveClickListener = new OnClickListener() {

	@Override
	public void onClick(View v) {
	    String total = amount.getText().toString();
	    // check if string is empty
	    if (total.equals("")) {
		Toast.makeText(EditingFoteActivity.this,
			"Amount can't be empty", Toast.LENGTH_SHORT).show();
		return;
	    }
	    float foteAmount = Float.parseFloat(total);
	    // check if amount is invalid like zero
	    if (foteAmount == 0) {
		Toast.makeText(EditingFoteActivity.this,
			"Amount can't be zero", Toast.LENGTH_SHORT).show();
		return;
	    }

	    String foteComment = comment.getText().toString();
	    // check if string is empty
	    if (foteComment.equals("")) {
		Toast.makeText(EditingFoteActivity.this,
			"Description can't be empty", Toast.LENGTH_SHORT)
			.show();
		return;
	    }

	    // check if foteDate is different from oldFoteDate & it is the only fote associated with it
	    // delete it if that is the case
	    if (foteDate.getMonth() != previousFoteDate.getMonth() || foteDate.getYear() != previousFoteDate.getYear()) {
		Log.i(TAG, "New month object is different from old month object");
		(new MonthDataSource(EditingFoteActivity.this)).deleteIfOneAssoicatedFoteLeft(mFote.getMonthId());
	    }

	    mFote.setAmount(foteAmount);
	    mFote.setComment(foteComment);
	    mFote.setDate(foteDate.getTimeInMillis());
	    Month date = (new MonthDataSource(EditingFoteActivity.this)).findOrCreateMonthByMonthYear(foteDate.getMonth(), foteDate.getYear());
	    mFote.setMonthId(date.getId());
	    (new FoteDataSource(EditingFoteActivity.this)).updateFote(mFote);
	    finish();
	    overridePendingTransition(R.anim.slide_left_incoming, R.anim.slide_left_outgoing);
	}
    };
    
    private void deleteFote() {
	new AlertDialog.Builder(this).setTitle("Are you sure?")
	.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		(new MonthDataSource(EditingFoteActivity.this)).deleteIfOneAssoicatedFoteLeft(mFote.getMonthId());
		(new FoteDataSource(EditingFoteActivity.this)).deleteFote(mFote);
		finish();
		overridePendingTransition(R.anim.slide_left_incoming, R.anim.slide_left_outgoing);
	    }
	})
	.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		// do nothing on cancel
	    }
	}).show();
    }
}
