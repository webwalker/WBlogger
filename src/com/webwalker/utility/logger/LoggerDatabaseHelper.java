/**
 * 
 */
package com.webwalker.utility.logger;

import java.util.Vector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author administrator
 *
 */
class LoggerDatabaseHelper
{
	public static final String  SEND = "send";
	public static final String  UNSEND = "unsend";
	public static final String ID = "id";
	public static final String CONTENT = "content";
	public static final String STATUS = "status";
	
	private static final String TABLE_NAME = "logtable";
	
	private static final String selectSQL = "SELECT " + ID + ", " + CONTENT + " FROM " + TABLE_NAME  
	        + " WHERE " + STATUS + "=" + "'"+UNSEND + "'" + " ORDER BY " + ID + " LIMIT 10";

	private static final String sqlClause = " (" + ID + " TEXT PRIMARY KEY, " + CONTENT +" TEXT, " + STATUS + " TEXT)"; 
	public static Cursor queryLatestTenUnsendRecords(SQLiteDatabase database)
    {
        if (database.isOpen())
        {
            Log.i("SQL", "SQL query record");
            database.beginTransaction();
            Cursor cursor = database.rawQuery(selectSQL, null);
            database.setTransactionSuccessful();
            database.endTransaction();
            database.yieldIfContendedSafely();
            Log.i("SQL", "SQL query record finished");
            return cursor;
        }
        return null;
    }

    public static void insert(SQLiteDatabase database, String keyString, String contentString, String status)
    {
        if (database.isOpen())
        {
            Log.i("SQL", "SQL insert new record");

            database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, keyString);
            contentValues.put(CONTENT, contentString);
            contentValues.put(STATUS, status);
            database.insert(TABLE_NAME, null, contentValues);
            database.setTransactionSuccessful();
            database.endTransaction();
            database.yieldIfContendedSafely();
            Log.i("SQL", "SQL insert new record finised");
        }
    }

	public static void update(SQLiteDatabase database, Vector<String> keyString, String status)
	{
        if (database.isOpen())
        {
            Log.i("SQL", "SQL update status");
            database.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(STATUS, status);
            int size = keyString.size();
            String sqlClause = "";
            for (int i = 0; i < size; i++)
            {
                sqlClause += ID + "=" + keyString.get(i);
                if (i < size - 1)
                {
                    sqlClause += " OR ";
                }
            }
            database.update(TABLE_NAME, contentValues, sqlClause, null);
            database.setTransactionSuccessful();
            database.endTransaction();
            database.yieldIfContendedSafely();
            Log.i("SQL", "SQL update status finished");
        }
	}
	
	public static void startTransaction(SQLiteDatabase database)
	{
        if (database.isOpen())
        {
            Log.i("SQL", "SQL startTransaction");
            database.beginTransaction();
            database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + sqlClause);
            database.setTransactionSuccessful();
            database.endTransaction();
            database.yieldIfContendedSafely();
            Log.i("SQL", "SQL startTransaction finished");
        }
	}

	public static void commitChanges(SQLiteDatabase database)
	{
        if (database.isOpen())
        {
            Log.i("SQL", "SQL commitChanges");
            database.beginTransaction();
            database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + STATUS + "=" + SEND);
            database.setTransactionSuccessful();
            database.endTransaction();
            Log.i("SQL", "SQL commitChanges finished");
        }
    }

	public static void clearUp(SQLiteDatabase database)
    {
        if (database.isOpen())
        {
            Log.i("SQL", "SQL clearUp");
            commitChanges(database);
            database.close();
            Log.i("SQL", "SQL close");
        }
    }
}
