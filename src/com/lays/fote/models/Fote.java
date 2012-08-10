package com.lays.fote.models;

public class Fote {

	private long id;
	private int amount;
	private String comment;
	private long date;

	public Fote(long mId, int amt, String cmt, long dte) {
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
