package com.lays.fote.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lays.fote.models.Month;
import com.lays.fote.utilities.FoteCalendar;

public class MonthDataSource {

    private static final String TAG = MonthDataSource.class.getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private Database databaseHelper;

    public MonthDataSource(Context context) {
	databaseHelper = new Database(context);
    }

    private void open() throws SQLException {
	database = databaseHelper.getWritableDatabase();
    }

    private void close() {
	databaseHelper.close();
    }

    public Month findOrCreateMonthByMonthYear(int month, int year) {
	Month monthObject = null;
	open();
	Cursor result = database.query(Database.TABLE_MONTH, new String[] {
		Database.COLUMN_MONTH_ID, Database.COLUMN_MONTH_MONTH,
		Database.COLUMN_MONTH_YEAR, Database.COLUMN_MONTH_TIMESTAMP },
		Database.COLUMN_MONTH_MONTH + "=? AND "
			+ Database.COLUMN_MONTH_YEAR + "=?", new String[] {
			Integer.toString(month), Integer.toString(year) },
		null, null, null);
	if (result.moveToFirst()) {
	    monthObject = new Month(result.getLong(0), result.getInt(1),
		    result.getInt(2), result.getLong(3));
	    Log.i(TAG, monthObject.toString());
	}
	result.close();
	close();
	if (monthObject == null) {
	    Log.i(TAG,
		    "Month Not Found, proceeding to create new Month in database");
	    return createMonth(month, year);
	}
	return monthObject;
    }

    private Month createMonth(int month, int year) {
	FoteCalendar date = new FoteCalendar(year, month, 1);
	open();
	ContentValues cv = new ContentValues();
	cv.put(Database.COLUMN_MONTH_MONTH, month);
	cv.put(Database.COLUMN_MONTH_YEAR, year);
	cv.put(Database.COLUMN_MONTH_TIMESTAMP, date.getTimeInMillis());
	long success = database.insert(Database.TABLE_MONTH, null, cv);
	Log.i(TAG, "Newly inserted row ID (-1 if error occurred): " + success);
	close();
	return findOrCreateMonthByMonthYear(month, year);
    }

    public void deleteIfOneAssoicatedFoteLeft(long monthId) {
	open();
	// check the fote table for given month id
	Cursor result = database.query(Database.TABLE_FOTE, new String[] {
		Database.COLUMN_FOTE_ID, Database.COLUMN_FOTE_AMOUNT,
		Database.COLUMN_FOTE_COMMENT, Database.COLUMN_FOTE_DATE,
		Database.COLUMN_FOTE_MONTH_ID }, Database.COLUMN_FOTE_MONTH_ID
		+ " = ?", new String[] { Long.toString(monthId) }, null, null,
		null);
	// if cursor holds only one
	if (result.getCount() == 1) {
	    // delete associated month object from database
	    database.delete(Database.TABLE_MONTH, Database.COLUMN_MONTH_ID
		    + "=" + Long.toString(monthId), null);
	} else {
	    Log.e(TAG, "ERROR: Month has more than one associated Fote");
	}
	result.close();
	close();
    }

    /**
     * Return a list of all Months in the database
     * 
     * @return ArrayList<Month> list of all Month
     */
    public List<Month> getAllMonth() {
	open();
	List<Month> monthList = new ArrayList<Month>();
	Cursor result = database.query(Database.TABLE_MONTH, new String[] {
		Database.COLUMN_MONTH_ID, Database.COLUMN_MONTH_MONTH,
		Database.COLUMN_MONTH_YEAR, Database.COLUMN_MONTH_TIMESTAMP },
		null, null, null, null, Database.COLUMN_MONTH_TIMESTAMP);
	if (result.moveToFirst()) {
	    while (!result.isAfterLast()) {
		Month month = new Month(result.getLong(0), result.getInt(1),
			result.getInt(2), result.getLong(3));
		// Log.i(TAG, month.toString());
		monthList.add(month);
		result.moveToNext();
	    }
	}
	result.close();
	close();
	return monthList;
    }

    public Month getMonthById(long id) {
	open();
	Month month = null;
	Cursor result = database.query(Database.TABLE_MONTH, new String[] {
		Database.COLUMN_MONTH_ID, Database.COLUMN_MONTH_MONTH,
		Database.COLUMN_MONTH_YEAR, Database.COLUMN_MONTH_TIMESTAMP },
		Database.COLUMN_MONTH_ID + "=?",
		new String[] { String.valueOf(id) }, null, null, null);
	if (result.moveToFirst()) {
	    month = new Month(result.getLong(0), result.getInt(1),
		    result.getInt(2), result.getLong(3));
	    // Log.i(TAG, fote.toString());
	}
	result.close();
	close();
	if (month == null) {
	    Log.e(TAG, "Month Not Found");
	}
	return month;
    }
}
