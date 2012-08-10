package com.lays.fote.holders;

import android.view.View;
import android.widget.TextView;

import com.lays.fote.R;

public class FoteViewHolder {

	private TextView date;
	private TextView amount;
	private TextView comment;

	public FoteViewHolder(View base) {
		setDate((TextView) base.findViewById(R.id.row_date));
		setAmount((TextView) base.findViewById(R.id.row_amount));
		setComment((TextView) base.findViewById(R.id.row_comment));
	}

	public TextView getDate() {
		return date;
	}

	public void setDate(TextView date) {
		this.date = date;
	}

	public TextView getAmount() {
		return amount;
	}

	public void setAmount(TextView amount) {
		this.amount = amount;
	}

	public TextView getComment() {
		return comment;
	}

	public void setComment(TextView comment) {
		this.comment = comment;
	}
}
