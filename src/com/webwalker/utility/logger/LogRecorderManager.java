/**
 * 
 */
package com.webwalker.utility.logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author albertma
 *
 */
class LogRecorderManager
{
	private SQLiteDatabase logDb;
	private ThreadPoolExecutor logThreadPool;
	private static final int BLOCKING_QUEUE_SIZE = 30;

	public LogRecorderManager(SQLiteDatabase logDb)
	{
		this.logDb = logDb;
		this.logThreadPool = new ThreadPoolExecutor(1, 1, 30000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(BLOCKING_QUEUE_SIZE));
	}
	
	public void log(String log)
	{
		RecordTask task = new RecordTask(System.currentTimeMillis() + "", log, logDb);
		logThreadPool.execute(task);
	}
	
	public void clear()
	{
		logDb = null;
		logThreadPool.shutdownNow();
	}
}

class RecordTask implements Runnable
{
	private String timeStample;
	private String content;
	private SQLiteDatabase logDb;

	public RecordTask(String timeStample, String content, SQLiteDatabase logDb)
	{
		this.timeStample = timeStample;
		this.content = content;
		this.logDb = logDb;
	}
	
	@Override
    public void run()
    {
	    Log.i("LogRecorderTask", "LogRecorder start");
	    LoggerDatabaseHelper.insert(logDb, timeStample, content, LoggerDatabaseHelper.UNSEND);
	    Log.i("LogRecorderTask", "LogRecorder finished");
    }
	
}