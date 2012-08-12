package com.lays.fote.models;

import java.text.DecimalFormat;

public class Fote {

	private long id;
	private float amount;
	private String comment;
	private long date;

	public Fote(long mId, float amt, String cmt, long dte) {
		id = mId;
		amount = amt;
		comment = cmt;
		date = dte;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFormattedAmount() {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		return "$" + df.format(amount);
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amt) {
		amount = amt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String cmt) {
		comment = cmt;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long dte) {
		date = dte;
	}

	public String toString() {
		return "ID: " + id + " Amount: " + amount + " Date: " + date + " Comment: " + comment;
	}
}
