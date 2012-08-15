package com.lays.fote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

	public static final String TAG = Database.class.toString();

	private static final String DATABASE_NAME = "fote.db";
	private static final int DATABASE_VERSION = 1;

	// FOTE TABLE
	public static final String TABLE_FOTE = "fote";
	public static final String COLUMN_FOTE_ID = "_id";
	public static final String COLUMN_FOTE_AMOUNT = "amount";
	public static final String COLUMN_FOTE_COMMENT = "comment";
	public static final String COLUMN_FOTE_DATE = "date";
	public static final String COLUMN_FOTE_MONTH_ID = "month_id";

	// MONTH TABLE
	public static final String TABLE_MONTH = "month";
	public static final String COLUMN_MONTH_ID = "id";
	public static final String COLUMN_MONTH_MONTH = "month"; // hahahahah because i can!
	public static final String COLUMN_MONTH_YEAR = "year";
	public static final String COLUMN_MONTH_TIMESTAMP = "timestamp";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Creating Fote Database...");
		db.execSQL("CREATE TABLE " + TABLE_FOTE + " (" + COLUMN_FOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FOTE_AMOUNT + " INTEGER NOT NULL, " + COLUMN_FOTE_COMMENT + " TEXT NOT NULL, " + COLUMN_FOTE_DATE + " INTEGER NOT NULL, " + COLUMN_FOTE_MONTH_ID + " INTEGER NOT NULL);");
		db.execSQL("CREATE TABLE " + TABLE_MONTH + " (" + COLUMN_MONTH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MONTH_MONTH + " INTEGER NOT NULL, " + COLUMN_MONTH_YEAR + " INTEGER NOT NULL, " + COLUMN_MONTH_TIMESTAMP + " INTEGER NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DATABASE_NAME, "Upgrading database, which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOTE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTH);
		onCreate(db);
	}
}
