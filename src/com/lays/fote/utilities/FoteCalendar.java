package com.lays.fote.utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FoteCalendar extends GregorianCalendar {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a Gregorian calendar for the current date and time
     */
    public static void main(String[] args) {
	Calendar calendar = new GregorianCalendar();
	System.out.println("Current time is " + new Date());
	System.out.println("YEAR:\t" + calendar.get(Calendar.YEAR));
	System.out.println("MONTH:\t" + calendar.get(Calendar.MONTH));
	System.out.println("DATE:\t" + calendar.get(Calendar.DATE));
	System.out.println("HOUR:\t" + calendar.get(Calendar.HOUR));
	System.out.println("HOUR_OF_DAY:\t"
		+ calendar.get(Calendar.HOUR_OF_DAY));
	System.out.println("MINUTE:\t" + calendar.get(Calendar.MINUTE));
	System.out.println("SECOND:\t" + calendar.get(Calendar.SECOND));
	System.out.println("DAY_OF_WEEK:\t"
		+ calendar.get(Calendar.DAY_OF_WEEK));
	System.out.println("DAY_OF_MONTH:\t"
		+ calendar.get(Calendar.DAY_OF_MONTH));
	System.out.println("DAY_OF_YEAR:\t"
		+ calendar.get(Calendar.DAY_OF_YEAR));
	System.out.println("WEEK_OF_MONTH:\t"
		+ calendar.get(Calendar.WEEK_OF_MONTH));
	System.out.println("WEEK_OF_YEAR:\t"
		+ calendar.get(Calendar.WEEK_OF_YEAR));
	System.out.println("AM_PM:\t" + calendar.get(Calendar.AM_PM));

	// Construct a calendar for September 11, 2001
	FoteCalendar calendar1 = (FoteCalendar) new GregorianCalendar(2001, 8,
		11);
	System.out.println("September 11, 2001 is a "
		+ dayNameOfWeek(calendar1.get(Calendar.DAY_OF_WEEK)));
    }

    public FoteCalendar(long milliseconds) {
	setTimeInMillis(milliseconds);
    }

    public FoteCalendar(int year, int month, int day) {
	super(year, month, day);
    }

    public FoteCalendar() {
	super();
    }

    public String getHourWithNotation() {
	if (get(Calendar.AM_PM) == 1) { // 0 is AM, 1 is PM
	    return get(Calendar.HOUR) + ":" + get(Calendar.MINUTE) + "PM";
	} else {
	    return get(Calendar.HOUR) + ":" + get(Calendar.MINUTE) + "AM";
	}
    }

    public String getDay() {
	return "" + get(Calendar.DAY_OF_MONTH);
    }

    public static String dayNameOfWeek(int dayOfWeek) {
	switch (dayOfWeek) {
	case 1:
	    return "Sunday";
	case 2:
	    return "Monday";
	case 3:
	    return "Tuesday";
	case 4:
	    return "Wednesday";
	case 5:
	    return "Thursday";
	case 6:
	    return "Friday";
	case 7:
	    return "Saturday";
	default:
	    return null;
	}
    }

    public int getYear() {
	return get(Calendar.YEAR);
    }

    public int getMonth() {
	return get(Calendar.MONTH);
    }

    public int getAdjustedMonth() {
	return get(Calendar.MONTH) + 1;
    }

    public String getMonthName() {
	switch (get(Calendar.MONTH)) {
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

    public String getFullDate() {
	return getDay() + " " + getMonthName() + " " + getYear();
    }

    public String getShortenedDateAmerican() {
	return getAdjustedMonth() + "/" + getDay() + "/" + getYear();
    }

    public String getShortenedDateBritish() {
	return getDay() + "/" + getAdjustedMonth() + "/" + getYear();
    }
}
