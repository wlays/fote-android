package com.lays.fote.adapters;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.lays.fote.R;
import com.lays.fote.holders.MonthViewHolder;
import com.lays.fote.models.Month;

public class MonthListAdapter extends ArrayAdapter<Month> {

    /** XML layout inflater */
    private static LayoutInflater inflater;

    /** List of our mArticles objects */
    private ArrayList<Month> mMonths;

    public MonthListAdapter(SherlockListActivity activity, ArrayList<Month> months) {
	super(activity, R.layout.list_row_month, months);
	inflater = activity.getLayoutInflater();
	mMonths = months;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View row = convertView;

	if (row == null) {
	    row = inflater.inflate(R.layout.list_row_month, parent, false);
	}

	MonthViewHolder holder = (MonthViewHolder) row.getTag();

	if (holder == null) {
	    holder = new MonthViewHolder(row);
	    row.setTag(holder);
	}

	Month month = mMonths.get(position);
	holder.getYear().setText(month.getStringYear());
	holder.getMonth().setText(month.getMonthName());

	return row;
    }
}
