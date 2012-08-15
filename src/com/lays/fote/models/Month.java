package com.lays.fote.models;


public class Month {

    private long id;
    private int month;
    private int year;

    public Month(long id, int month, int year) {
	this.id = id;
	setMonth(month);
	setYear(year);
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

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }
    
    public String toString() {
	return "Month ID: " + id + " Month: " + month + " Year: " + year;
    }
    
    public String getMonthName() {
	switch (getMonth()) {
	case 1:
		return "January";
	case 2:
		return "February";
	case 3:
		return "March";
	case 4:
		return "April";
	case 5:
		return "May";
	case 6:
		return "June";
	case 7:
		return "July";
	case 8:
		return "August";
	case 9:
		return "September";
	case 10:
		return "October";
	case 11:
		return "November";
	case 12:
		return "December";
	default:
		return null;
	}
}
}
