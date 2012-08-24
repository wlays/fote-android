package com.lays.fote.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lays.fote.FoteApplication;
import com.lays.fote.R;
import com.lays.fote.adapters.FoteListAdapter;
import com.lays.fote.database.FoteDataSource;
import com.lays.fote.database.MonthDataSource;
import com.lays.fote.models.Fote;
import com.lays.fote.models.Month;
import com.lays.fote.utilities.FoteCalendar;

public class MainActivity extends SherlockListActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    
    private static final int NEW_FOTE_REQUEST_CODE = 0;
    private static final int EDIT_FOTE_REQUEST_CODE = 1;
    private static final int MONTH_LIST_REQUEST_CODE = 2;

    private static final String MOST_RECENT = "Most Recent";
    private static final String MOST_EXPENSIVE = "Most Expensive";
    private static final String LEAST_RECENT = "Least Recent";
    private static final String LEAST_EXPENSIVE = "Least Expensive";

    private Month mCurrentMonth;
    
    private SharedPreferences mPreferences;
    private Spinner mSpinner;
    
    private String[] mSortingOrders;
    private HashMap<String, Integer> sortOrder = new HashMap<String, Integer>();
    private String mCurrentSortOrder;
    
    private ArrayList<Fote> mFotes;
    private FoteListAdapter mAdapter;

    /** Fote list's listener */
    private OnItemClickListener mFotesListener = new OnItemClickListener() {
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    editFote(position);
	}
    };

    /** Fote spinner's listener */
    private OnItemSelectedListener mSpinnerListener = new OnItemSelectedListener() {
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
	    mCurrentSortOrder = mSortingOrders[position];
	    updateListViewAndTotalSpending();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	if (isScreenSizeLarge()) {
	    // yes, we are large
	    // TODO: put on the backburner...
	} else {
	    // no, we are not
	    setContentView(R.layout.activity_main);
	}

	ListView listview = getListView();
	listview.setOnItemClickListener(mFotesListener);
	registerForContextMenu(listview);
	
	updateCurrentMonth();
	initSpinner();
	updateListViewAndTotalSpending();
    }
    
    public void onResume() {
	super.onResume();
	updateSortingOrder();
    }
    
    private boolean isScreenSizeLarge() {
	return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_LARGE) == Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    
    private void updateCurrentMonth() {
	// check if there is a month object currently
	FoteCalendar now = new FoteCalendar();
	mCurrentMonth = (new MonthDataSource(this)).findOrCreateMonthByMonthYear(now.getMonth(), now.getYear());
	TextView monthYearView = (TextView) findViewById(R.id.current_month_year);
	monthYearView.setText("Month: " + mCurrentMonth.getMonthName() + " " + mCurrentMonth.getYear());
    }
    
    private void initSpinner() {
	mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	mSpinner = (Spinner) findViewById(R.id.spinner);
	mSpinner.setOnItemSelectedListener(mSpinnerListener);
	
	mSortingOrders = getResources().getStringArray(R.array.sorting);
	ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSortingOrders);
	aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	mSpinner.setAdapter(aa);
	
	int length = mSortingOrders.length;
	for (int i = 0; i < length; i++) {
	    sortOrder.put(mSortingOrders[i], i);
	}
	
	updateSortingOrder();
    }
    
    private void updateSortingOrder() {
	// get updated values from settings pref here
	mCurrentSortOrder = mPreferences.getString(FoteApplication.PREF_SORTING_KEY, FoteApplication.PREF_SORTING_DEFAULT_VALUE);
	Log.i(TAG, "Sorting: " + mCurrentSortOrder);
	mSpinner.setSelection(sortOrder.get(mCurrentSortOrder));
    }
    
    private void updateListViewAndTotalSpending() {	
	// refresh data here and update total spending too
	if (mCurrentSortOrder.equals(MOST_RECENT)) {
	    mFotes = (ArrayList<Fote>) (new FoteDataSource(this)).getAllFotesByMonthId(mCurrentMonth.getId());
	    Collections.reverse(mFotes);
	} else if (mCurrentSortOrder.equals(MOST_EXPENSIVE)) {
	    mFotes = (ArrayList<Fote>) (new FoteDataSource(this)).getAllFotesOrderedByAmount(mCurrentMonth.getId());
	    Collections.reverse(mFotes);
	} else if (mCurrentSortOrder.equals(LEAST_RECENT)) {
	    mFotes = (ArrayList<Fote>) (new FoteDataSource(this)).getAllFotesByMonthId(mCurrentMonth.getId());
	} else if (mCurrentSortOrder.equals(LEAST_EXPENSIVE)) {
	    mFotes = (ArrayList<Fote>) (new FoteDataSource(this)).getAllFotesOrderedByAmount(mCurrentMonth.getId());
	}
	mAdapter = new FoteListAdapter(this, mFotes);
	setListAdapter(mAdapter);
	TextView totalSpendingView = (TextView) findViewById(R.id.total_spending_header);
	totalSpendingView.setText(getString(R.string.total_spending_label) + " " + getTotalSpending());
    }

    private String getTotalSpending() {
	float total = 0;
	for (Fote fote : mFotes) {
	    total += fote.getAmount();
	}
	DecimalFormat df = new DecimalFormat();
	df.setMinimumFractionDigits(2);
	df.setMaximumFractionDigits(2);
	return "$" + df.format(total);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getSupportMenuInflater();
	inflater.inflate(R.menu.activity_main, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.menu_add_fote:
	    startActivityForResult(new Intent(this, FotingActivity.class), NEW_FOTE_REQUEST_CODE);
	    overridePendingTransition(R.anim.slide_right_incoming, R.anim.slide_right_outgoing);
	    return true;
	case R.id.menu_see_list:
	    startActivityForResult(new Intent(MainActivity.this, MonthListActivity.class), MONTH_LIST_REQUEST_CODE);
	    overridePendingTransition(R.anim.slide_up_incoming, R.anim.slide_up_outgoing);
	    return true;
	case R.id.menu_settings:
	    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
		startActivity(new Intent(MainActivity.this, SettingsActivity.class));		
	    } else {
		startActivity(new Intent(MainActivity.this, SettingsNewActivity.class));
	    }
	    overridePendingTransition(R.anim.slide_left_incoming, R.anim.slide_left_outgoing);
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	switch (requestCode) {
	case NEW_FOTE_REQUEST_CODE:
	    if (resultCode == RESULT_OK) {
		// A new fote was created, refresh list
		updateCurrentMonth();
		updateListViewAndTotalSpending();
	    }
	    break;
	case EDIT_FOTE_REQUEST_CODE:
	    if (resultCode == RESULT_OK) {
                // A fote was edited, refresh list
		updateCurrentMonth();
		updateListViewAndTotalSpending();
            }
	    break;
	case MONTH_LIST_REQUEST_CODE:
	    if (resultCode == RESULT_OK) {
                // A new month was picked,
		// set it to current month and load it from database..
		long monthId = data.getLongExtra(MonthListActivity.MONTH_ID_KEY, 0);
		mCurrentMonth = (new MonthDataSource(this)).getMonthById(monthId);
		TextView monthYearView = (TextView) findViewById(R.id.current_month_year);
		monthYearView.setText("Month: " + mCurrentMonth.getMonthName() + " " + mCurrentMonth.getYear());
		// refresh the list
		updateListViewAndTotalSpending();
            }
	    break;
	default:
	    break;
	}
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
	android.view.MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(android.view.MenuItem item) {
	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	switch (item.getItemId()) {
	case R.id.context_edit:
	    editFote(info.position);
	    return true;
	case R.id.context_share:
	    shareFote(info.position);
	    return true;
	case R.id.context_delete:
	    deleteFote(info.position);
	    return true;
	default:
	    return super.onContextItemSelected(item);
	}
    }

    private void editFote(int position) {
	long foteID = mFotes.get(position).getId();
	Intent intent = new Intent(MainActivity.this, EditingFoteActivity.class);
	intent.putExtra(FoteApplication.FOTE_KEY, foteID);
	startActivityForResult(intent, EDIT_FOTE_REQUEST_CODE);
	overridePendingTransition(R.anim.slide_right_incoming, R.anim.slide_right_outgoing);
    }

    private void shareFote(int position) {
	// TODO: implementation to be completed
	Toast.makeText(MainActivity.this, "Shared Fote: " + position, Toast.LENGTH_SHORT).show();
    }

    private void deleteFote(final int position) {
	new AlertDialog.Builder(this).setTitle("Are you sure?")
		.setPositiveButton("Cancel", new OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			// do nothing on cancel
		    }
		}).setNegativeButton("Delete", new OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			Fote fote = mFotes.remove(position);
			(new MonthDataSource(MainActivity.this)).deleteIfOneAssoicatedFoteLeft(fote.getMonthId());
			(new FoteDataSource(MainActivity.this)).deleteFote(fote);
			mAdapter.notifyDataSetChanged();
			TextView totalSpendingView = (TextView) findViewById(R.id.total_spending_header);
			totalSpendingView.setText(getString(R.string.total_spending_label) + " " + getTotalSpending());
		    }
		}).show();
    }
}
