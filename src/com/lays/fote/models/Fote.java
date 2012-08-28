package com.lays.fote.models;

import java.text.DecimalFormat;

public class Fote {

    private long id;
    private float amount;
    private String comment;
    private long date;
    private String category;
    private long monthId;

    public Fote(long id, float amount, String comment, long date, String category, long monthId) {
	this.id = id;
	setAmount(amount);
	setComment(comment);
	setDate(date);
	setMonthId(monthId);
	setCategory(category);
    }

    public long getId() {
	return id;
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

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }
    
    public long getMonthId() {
	return monthId;
    }

    public void setMonthId(long monthId) {
	this.monthId = monthId;
    }
    
    public String toString() {
	return "Fote ID: " + id + " Amount: " + amount + " Date: " + date + " Comment: " + comment + " Category: " + category + " Month ID: " + monthId;
    }
}
