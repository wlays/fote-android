package com.lays.fote.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockListActivity;
import com.lays.fote.FoteApplication;
import com.lays.fote.R;
import com.lays.fote.adapters.MonthListAdapter;
import com.lays.fote.database.MonthDataSource;
import com.lays.fote.models.Month;

public class MonthListActivity extends SherlockListActivity {

    private static final String TAG = MonthListActivity.class.getSimpleName();
    public static final String MONTH_ID_KEY = TAG;
    
    private ArrayList<Month> months;
    private MonthListAdapter adapter;

    /** Month list's listener */
    private OnItemClickListener listener = new OnItemClickListener() {
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    Intent intent = new Intent();
	    intent.putExtra(MONTH_ID_KEY, months.get(position).getId());
	    MonthListActivity.this.setResult(Activity.RESULT_OK, intent);
	    finish();
	    overridePendingTransition(R.anim.slide_down_incoming, R.anim.slide_down_outgoing);
	}
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	FoteApplication.tracker.trackPageView("/Months");
	setContentView(R.layout.activity_month_list);
	getListView().setOnItemClickListener(listener);
	initMonthList();
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
