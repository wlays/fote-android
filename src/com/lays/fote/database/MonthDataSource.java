package com.lays.fote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lays.fote.models.Month;

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

    public Month findOrCreateMonthByMonthYear(String month, String year) {
	Month monthObject = null;
	open();
	Cursor result = database.query(Database.TABLE_MONTH, new String[] {
		Database.COLUMN_MONTH_ID, Database.COLUMN_MONTH_MONTH,
		Database.COLUMN_MONTH_YEAR }, Database.COLUMN_MONTH_MONTH
		+ "=? AND " + Database.COLUMN_MONTH_YEAR + "=?",
		new String[] { month, year }, null, null, null);
	if (result.moveToFirst()) {
	    monthObject = new Month(result.getLong(0), result.getInt(1), result.getInt(2));
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

    private Month createMonth(String month, String year) {
	open();
	ContentValues cv = new ContentValues();
	cv.put(Database.COLUMN_MONTH_MONTH, Integer.parseInt(month));
	cv.put(Database.COLUMN_MONTH_YEAR, Integer.parseInt(year));
	long success = database.insert(Database.TABLE_MONTH, null, cv);
	Log.i(TAG, "Newly inserted row ID (-1 if error occurred): " + success);
	close();
	return findOrCreateMonthByMonthYear(month, year);
    }
    
    public void deleteIfOneAssoicatedFoteLeft(long monthId) {
	open();
	// check the fote table for given month id
	Cursor result = database.query(Database.TABLE_FOTE, new String[] { Database.COLUMN_FOTE_ID, Database.COLUMN_FOTE_AMOUNT, Database.COLUMN_FOTE_COMMENT, Database.COLUMN_FOTE_DATE, Database.COLUMN_FOTE_MONTH_ID }, Database.COLUMN_FOTE_MONTH_ID + " = ?", new String[] { Long.toString(monthId) }, null, null, null);
	// if cursor holds only one
	if (result.getCount() == 1) {
	    // delete associated month object from database
	    database.delete(Database.TABLE_MONTH, Database.COLUMN_MONTH_ID + "=" + Long.toString(monthId), null);
	} else {
	    Log.e(TAG, "ERROR: Month has more than one associated Fote");
	}
	result.close();
	close();
    }
}
