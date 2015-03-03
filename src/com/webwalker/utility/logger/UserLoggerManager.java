/**
 * 
 */
package com.webwalker.utility.logger;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * @author albertma
 *
 */
public class UserLoggerManager
{
	public 	static String URL = "";
	private static final String DB_NAME = "log.db";
	private  static UserLoggerManager instance = new UserLoggerManager();
	private  SQLiteDatabase  logDB;
	private  LogSenderManager logSenderManager;
	private  LogRecorderManager logRecordManager;
	
	
	private UserLoggerManager()
	{
	}
	
	public static UserLoggerManager getInstance()
	{
		return instance;
	}

	public void log(String log)
	{
		if(logRecordManager == null)
		{
			new RuntimeException("Please call init(Context context) before calling log(String log)");
		}
		Log.i("UserLoggerManager", "log:" + log);
		logRecordManager.log(log);
	}
	
	
	
	public void clear()
	{
		logSenderManager.clear();
		logRecordManager.clear();
		LoggerDatabaseHelper.clearUp(logDB);
		
	}
	
	public void init(Context context)
    {
		logDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
		LoggerDatabaseHelper.startTransaction(logDB);
		logSenderManager = new LogSenderManager(logDB);
		logRecordManager = new LogRecorderManager(logDB);
    }

}









