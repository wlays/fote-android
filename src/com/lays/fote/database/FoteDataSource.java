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

	public void open() throws SQLException {
		database = databaseHelper.getWritableDatabase();
	}

	public void close() {
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
	public long createFote(float amount, String comment, long date) {
		open();
		ContentValues cv = new ContentValues();
		cv.put(Database.COLUMN_FOTE_AMOUNT, amount);
		cv.put(Database.COLUMN_FOTE_COMMENT, comment);
		cv.put(Database.COLUMN_FOTE_DATE, date);
		long success = database.insert(Database.TABLE_FOTE, null, cv);
		close();
		return success;
	}

	public void deleteFote(Fote fote) {
		open();
		long id = fote.getId();
		Log.i(TAG, "Fote deleted with id: " + id);
		database.delete(Database.TABLE_FOTE, Database.COLUMN_FOTE_ID + " = " + id, null);
		close();
	}

	/**
	 * Return a list of all Fotes in the database
	 * 
	 * @return ArrayList<Fote> list of all Fotes
	 */
	public List<Fote> fetchAllNotes() {
		open();
		List<Fote> fotes = new ArrayList<Fote>();
		Cursor result = database.query(Database.TABLE_FOTE, new String[] { Database.COLUMN_FOTE_ID, Database.COLUMN_FOTE_AMOUNT, Database.COLUMN_FOTE_COMMENT, Database.COLUMN_FOTE_DATE }, null, null, null, null, Database.COLUMN_FOTE_DATE);
		if (result.moveToFirst()) {
			while (!result.isAfterLast()) {
				Fote fote = new Fote(result.getInt(0), result.getFloat(1), result.getString(2), result.getLong(3));
				// Log.i(TAG, "Amount: " + fote.getAmount() + " Comment: " +
				// fote.getComment() + " Date: " + fote.getDate());
				fotes.add(fote);
				result.moveToNext();
			}
		}
		result.close();
		close();
		return fotes;
	}
}
