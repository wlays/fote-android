package com.lays.fote.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lays.fote.FoteApplication;
import com.lays.fote.R;
import com.lays.fote.adapters.FoteListAdapter;
import com.lays.fote.helpers.DatabaseHelper;
import com.lays.fote.models.Fote;

public class MainActivity extends SherlockListActivity {

	private static final String TAG = MainActivity.class.toString();
	private Spinner spinner;
	private DatabaseHelper mDatabase;
	private ArrayList<Fote> mFotes;
	private FoteListAdapter mAdapter;

	/** Fote list's listener */
	private OnItemClickListener listener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Toast.makeText(MainActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
		}
	};

	/** Fote spinner's listener */
	private OnItemSelectedListener spinnerListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
			// TODO do something
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO do something
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initSpinner();
		mDatabase = ((FoteApplication) getApplication()).getDatabaseHelper();
		mFotes = mDatabase.fetchAllNotes();
		mAdapter = new FoteListAdapter(this, mFotes);
		setListAdapter(mAdapter);
		getListView().setOnItemClickListener(listener);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			mFotes = mDatabase.fetchAllNotes();
			mAdapter = new FoteListAdapter(this, mFotes);
			setListAdapter(mAdapter);
		}
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
			return true;
		case R.id.menu_see_list:
			Toast.makeText(this, "Saw big list bro...", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_settings:
			Toast.makeText(this, "Saw everything bro...", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initSpinner() {
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(spinnerListener);
		final String[] items = { "", "Most recent", "Most expensive" };
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(aa);
	}
}
