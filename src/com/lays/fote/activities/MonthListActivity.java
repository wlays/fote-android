package com.lays.fote.activities;

import java.util.ArrayList;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListActivity;
import com.lays.fote.R;
import com.lays.fote.adapters.MonthListAdapter;
import com.lays.fote.database.MonthDataSource;
import com.lays.fote.models.Month;

public class MonthListActivity extends SherlockListActivity {

    private static final String TAG = MonthListActivity.class.getSimpleName();

    private ArrayList<Month> months;
    private MonthListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_month_list);
	getSupportActionBar().hide();
	initMonthList();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
	super.onWindowFocusChanged(hasFocus);
	if (hasFocus) {
	    initMonthList();
	}
    }

    @Override
    public void onBackPressed() {
	super.onBackPressed();
	overridePendingTransition(R.anim.slide_down_incoming, R.anim.slide_down_outgoing);
    }

    private void initMonthList() {
	months = (ArrayList<Month>) (new MonthDataSource(this)).getAllMonth();
	adapter = new MonthListAdapter(this, months);
	setListAdapter(adapter);
    }
}
