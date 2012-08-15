package com.lays.fote.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
    private Spinner spinner;
    private Month currentMonth;
    private ArrayList<Fote> mFotes;
    private FoteListAdapter mAdapter;

    /** Fote list's listener */
    private OnItemClickListener listener = new OnItemClickListener() {
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    long foteID = mFotes.get(position).getId();
	    Intent intent = new Intent(MainActivity.this, EditingFoteActivity.class);
	    intent.putExtra(FoteApplication.FOTE_KEY, foteID);
	    startActivity(intent);
	    overridePendingTransition(R.anim.slide_right_incoming, R.anim.slide_right_outgoing);
	}
    };

    /** Fote spinner's listener */
    private OnItemSelectedListener spinnerListener = new OnItemSelectedListener() {
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
		long id) {
	    // TODO do something to sort
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	    // TODO do something?
	}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	registerForContextMenu(getListView());
	getListView().setOnItemClickListener(listener);
	initSpinner();
	initCurrentMonth();
	initListViewAndTotalSpending();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
	super.onWindowFocusChanged(hasFocus);
	if (hasFocus) {
	    initCurrentMonth();
	    initListViewAndTotalSpending();
	}
    }

    private void initSpinner() {
	spinner = (Spinner) findViewById(R.id.spinner);
	spinner.setOnItemSelectedListener(spinnerListener);
	final String[] items = { "Most Recent", "Most Expensive" };
	ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
	aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinner.setAdapter(aa);
    }

    private void initCurrentMonth() {
	// check if there is a month object currently
	FoteCalendar now = new FoteCalendar();
	String month = now.getMonth();
	String year = now.getYear();
	currentMonth = (new MonthDataSource(this)).findOrCreateMonthByMonthYear(month, year);
	TextView monthYearView = (TextView) findViewById(R.id.current_month_year);
	monthYearView.setText(currentMonth.getMonthName() + " " + currentMonth.getYear());
    }

    private void initListViewAndTotalSpending() {
	// refresh data here and update total spending too
	mFotes = (ArrayList<Fote>) (new FoteDataSource(this)).getAllFotesByMonthId(currentMonth.getId());
	mAdapter = new FoteListAdapter(this, mFotes);
	setListAdapter(mAdapter);
	getListView().setSelection(mAdapter.getCount());
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
	    startActivity(new Intent(this, FotingActivity.class));
	    overridePendingTransition(R.anim.slide_right_incoming,
		    R.anim.slide_right_outgoing);
	    return true;
	case R.id.menu_see_list:
	    Toast.makeText(this, "Saw big list bro...", Toast.LENGTH_SHORT)
		    .show();
	    return true;
	case R.id.menu_settings:
	    Toast.makeText(this, "Saw everything bro...", Toast.LENGTH_SHORT)
		    .show();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
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
	startActivity(intent);
	overridePendingTransition(R.anim.slide_right_incoming, R.anim.slide_right_outgoing);
    }

    private void shareFote(int position) {
	// TODO: implementation
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
		    }
		}).show();
    }
}
