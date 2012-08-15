package com.lays.fote.models;

public class Month {

    private long id;
    private int month;
    private int year;
    private long timestamp;

    public Month(long id, int month, int year, long timestamp) {
	this.id = id;
	setMonth(month);
	setYear(year);
	setTimestamp(timestamp);
    }

    public long getId() {
	return id;
    }

    public int getMonth() {
	return month;
    }

    public void setMonth(int month) {
	this.month = month;
    }

    public String getStringYear() {
	return "" + year;
    }
    
    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    public long getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(long timestamp) {
	this.timestamp = timestamp;
    }

    public String toString() {
	return "Month ID: " + id + " Month: " + month + " Year: " + year;
    }

    public String getMonthName() {
	switch (getMonth()) {
	case 0:
	    return "January";
	case 1:
	    return "February";
	case 2:
	    return "March";
	case 3:
	    return "April";
	case 4:
	    return "May";
	case 5:
	    return "June";
	case 6:
	    return "July";
	case 7:
	    return "August";
	case 8:
	    return "September";
	case 9:
	    return "October";
	case 10:
	    return "November";
	case 11:
	    return "December";
	default:
	    return null;
	}
    }
}
