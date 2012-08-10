package com.lays.fote.models;

public class Fote {

	private int amount;
	private String comment;
	private long date;

	public Fote(int amt, String cmt, long dte) {
		amount = amt;
		comment = cmt;
		date = dte;
	}

	public int getAmount() {
		return amount;
	}

	public String getComment() {
		return comment;
	}

	public long getDate() {
		return date;
	}
}
