package com.lays.fote.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lays.fote.models.Fote;

public class FoteDataSource {

    private static final String TAG = FoteDataSource.class.getSimpleName();

    // Database fields
    private SQLiteDatabase database;
    private Database databaseHelper;

    public FoteDataSource(Context context) {
	databaseHelper = new Database(context);
    }

    private void open() throws SQLException {
	database = databaseHelper.getWritableDatabase();
    }

    private void close() {
	databaseHelper.close();
    }

    /**
     * Check if current month/year combination from Fote object passed in
     * already exists in database. If so, use that. Otherwise, create one for
     * future use.
     * 
     * @param amount
     * @param comment
     * @param date
     * @return long rowID or -1 if failed
     */
    public void createFote(float amount, String comment, long date, long monthId) {
	open();
	ContentValues cv = new ContentValues();
	cv.put(Database.COLUMN_FOTE_AMOUNT, amount);
	cv.put(Database.COLUMN_FOTE_COMMENT, comment);
	cv.put(Database.COLUMN_FOTE_DATE, date);
	cv.put(Database.COLUMN_FOTE_MONTH_ID, monthId);
	long success = database.insert(Database.TABLE_FOTE, null, cv);
	// Log.i(TAG, "Newly inserted row ID (-1 if error occurred): " +
	// success);
	close();
    }

    public void updateFote(Fote fote) {
	open();
	ContentValues values = new ContentValues();
	values.put(Database.COLUMN_FOTE_AMOUNT, fote.getAmount());
	values.put(Database.COLUMN_FOTE_COMMENT, fote.getComment());
	values.put(Database.COLUMN_FOTE_DATE, fote.getDate());
	values.put(Database.COLUMN_FOTE_MONTH_ID, fote.getMonthId());
	int numOfRowAffected = database.update(Database.TABLE_FOTE, values,
		Database.COLUMN_FOTE_ID + "=?",
		new String[] { String.valueOf(fote.getId()) });
	// Log.i(TAG, "Row Updated: " + numOfRowAffected);
	close();
    }

    public void deleteFote(Fote fote) {
	open();
	// Log.i(TAG, "Fote Deleted: " + fote.toString());
	database.delete(Database.TABLE_FOTE, Database.COLUMN_FOTE_ID + " = "
		+ fote.getId(), null);
	close();
    }

    /**
     * Return a list of all Fotes in the database
     * 
     * @return ArrayList<Fote> list of all Fotes
     */
    public List<Fote> getAllFotesByMonthId(long monthId) {
	open();
	List<Fote> fotes = new ArrayList<Fote>();
	Cursor result = database.query(Database.TABLE_FOTE, new String[] {
		Database.COLUMN_FOTE_ID, Database.COLUMN_FOTE_AMOUNT,
		Database.COLUMN_FOTE_COMMENT, Database.COLUMN_FOTE_DATE,
		Database.COLUMN_FOTE_MONTH_ID }, Database.COLUMN_FOTE_MONTH_ID
		+ " = ?", new String[] { Long.toString(monthId) }, null, null,
		Database.COLUMN_FOTE_DATE);
	if (result.moveToFirst()) {
	    while (!result.isAfterLast()) {
		Fote fote = new Fote(result.getLong(0), result.getFloat(1),
			result.getString(2), result.getLong(3),
			result.getLong(4));
		// Log.i(TAG, fote.toString());
		fotes.add(fote);
		result.moveToNext();
	    }
	}
	result.close();
	close();
	return fotes;
    }

    public Fote getFoteById(long id) {
	open();
	Fote fote = null;
	Cursor result = database.query(Database.TABLE_FOTE, new String[] {
		Database.COLUMN_FOTE_ID, Database.COLUMN_FOTE_AMOUNT,
		Database.COLUMN_FOTE_COMMENT, Database.COLUMN_FOTE_DATE,
		Database.COLUMN_FOTE_MONTH_ID },
		Database.COLUMN_FOTE_ID + "=?",
		new String[] { String.valueOf(id) }, null, null, null);
	if (result.moveToFirst()) {
	    fote = new Fote(result.getLong(0), result.getFloat(1),
		    result.getString(2), result.getLong(3), result.getLong(4));
	    // Log.i(TAG, fote.toString());
	}
	result.close();
	close();
	if (fote == null) {
	    Log.e(TAG, "Fote Not Found");
	}
	return fote;
    }
}
