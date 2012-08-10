package com.lays.fote.helpers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lays.fote.models.Fote;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TAG = DatabaseHelper.class.toString();

	private static final String DATABASE_NAME = "fote.db";
	private static final int DATABASE_VERSION = 1;

	// FOTE TABLE
	public static final String TABLE_FOTE = "fote";
	public static final String COLUMN_FOTE_ID = "_id";
	public static final String COLUMN_FOTE_AMOUNT = "amount";
	public static final String COLUMN_FOTE_COMMENT = "comment";
	public static final String COLUMN_FOTE_DATE = "date";

	// MONTH TABLE
	public static final String TABLE_MONTH = "month";
	public static final String COLUMN_MONTH_YEAR_ID = "id";
	public static final String COLUMN_MONTH_TOTAL_SPENDING = "total_spending";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Creating Fote Database...");
		db.execSQL("CREATE TABLE " + TABLE_FOTE + " (" + COLUMN_FOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FOTE_AMOUNT + " INTEGER NOT NULL, " + COLUMN_FOTE_COMMENT + " TEXT NOT NULL, " + COLUMN_FOTE_DATE + " INTEGER NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DATABASE_NAME, "Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOTE);
		onCreate(db);
	}

	/**
	 * Check if current month/year combination from Fote object passed in
	 * already exists in database. If so, use that. Otherwise, create one for
	 * future use.
	 * 
	 * @param fote
	 *            the object to be saved
	 * @return long rowID or -1 if failed
	 */
	public long createFote(Fote fote) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_FOTE_AMOUNT, fote.getAmount());
		cv.put(COLUMN_FOTE_COMMENT, fote.getComment());
		cv.put(COLUMN_FOTE_DATE, fote.getDate());
		long success = db.insert(TABLE_FOTE, null, cv);
		db.close();
		return success;
	}

	/**
	 * Return a list of all Fotes in the database
	 * 
	 * @return ArrayList<Fote> list of all Fotes
	 */
	public ArrayList<Fote> fetchAllNotes() {
		ArrayList<Fote> fotes = new ArrayList<Fote>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor result = db.query(TABLE_FOTE, new String[] { COLUMN_FOTE_ID, COLUMN_FOTE_AMOUNT, COLUMN_FOTE_COMMENT, COLUMN_FOTE_DATE }, null, null, null, null, COLUMN_FOTE_DATE);
		if (result.moveToFirst()) {
			while (!result.isAfterLast()) {
				Fote fote = new Fote(result.getInt(1), result.getString(2), result.getLong(3));
				Log.i(TAG, "Amount: " + fote.getAmount() + " Comment: " + fote.getComment() + " Date: " + fote.getDate());
				fotes.add(fote);
				result.moveToNext();
			}
		}
		result.close();
		db.close();
		return fotes;
	}
}
