package com.lays.fote.wrappers;

import android.view.View;
import android.widget.TextView;

import com.lays.fote.R;

public class FoteWrapper {

	View base;
	TextView date = null;
	TextView amount = null;
	TextView comment = null;

	public FoteWrapper(View base) {
		this.base = base;
	}

	public TextView getDate() {
		if (date == null) {
			date = (TextView) base.findViewById(R.id.row_date);
		}
		return date;
	}

	public TextView getAmount() {
		if (amount == null) {
			amount = (TextView) base.findViewById(R.id.row_amount);
		}
		return amount;
	}

	public TextView getComment() {
		if (comment == null) {
			comment = (TextView) base.findViewById(R.id.row_comment);
		}
		return comment;
	}
}
