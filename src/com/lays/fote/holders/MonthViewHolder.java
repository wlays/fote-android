package com.lays.fote.holders;

import android.view.View;
import android.widget.TextView;

import com.lays.fote.R;

public class MonthViewHolder {

    private TextView year;
    private TextView month;

    public MonthViewHolder(View base) {
	setYear((TextView) base.findViewById(R.id.row_month_year));
	setMonth((TextView) base.findViewById(R.id.row_month_month));
    }

    public TextView getYear() {
	return year;
    }

    public void setYear(TextView year) {
	this.year = year;
    }

    public TextView getMonth() {
	return month;
    }

    public void setMonth(TextView month) {
	this.month = month;
    }
}