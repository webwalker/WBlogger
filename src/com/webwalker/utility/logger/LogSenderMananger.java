/**
 * 
 */
package com.webwalker.utility.logger;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

/**
 * @author albertma
 *
 */
class LogSenderManager implements com.webwalker.utility.logger.UploadJob.UploadJobCallBack
{
	private final static int NETWORK_ACTION_TIMEDURATION = 1000;
	private Timer logTimer;
	private SQLiteDatabase logDb;

	public LogSenderManager(SQLiteDatabase logDb)
	{
		this.logDb = logDb;

		logTimer = new Timer(true);

		UploadJob job = new UploadJob(this.logDb, this);
		logTimer.schedule(job, NETWORK_ACTION_TIMEDURATION);
	}

	public void clear()
	{
		logTimer.cancel();
		logTimer = null;
		this.logDb = null;
		commitLogChange();
	}

	public void commitLogChange()
	{
		LoggerDatabaseHelper.commitChanges(this.logDb);
	}

	@Override
	public void onUploadResult(int resultType)
	{
		if (resultType == UploadJob.UploadJobCallBack.TYPE_NETWORK_UNAVAILABLE)
		{

		} else if (resultType == UploadJob.UploadJobCallBack.TYPE_NO_LOG)
		{

		}

	}

}

class UploadJob extends TimerTask 
{
	private SQLiteDatabase logDb;
	private UploadJobCallBack callback;
	public UploadJob(SQLiteDatabase logDatabase, UploadJobCallBack callback)
	{
		super();
		this.callback = callback;
		this.logDb = logDatabase;
		
	}
	
	@Override
    public void run()
    { if(true)
	    return ;
	    Log.i("LogSendTasker", "LogSender  start send data");
		Cursor  cursor= LoggerDatabaseHelper.queryLatestTenUnsendRecords(logDb);
		if (cursor.getCount() > 0 && cursor.moveToFirst())
		{
			JSONArray jsonArray = new JSONArray();
			Vector<String> keys = new Vector<String>();
			for (int i = 0; i < 10; i++)
			{
			    int idIndex = cursor.getColumnIndex(LoggerDatabaseHelper.ID);
			    int contentIndex = cursor.getColumnIndex(LoggerDatabaseHelper.CONTENT);
				String log = cursor.getString(contentIndex);
				String key = cursor.getString(idIndex);
				Log.i("LogSendTasker", "get log:" + log + " key:" + key);
				keys.add(key);
				JSONObject jsonObject = null;
                try
                {
	                jsonObject = new JSONObject(log);
	                jsonArray.put(jsonObject);
                } catch (JSONException e)
                {
	                e.printStackTrace();
                }
				
				if (!cursor.moveToPrevious())
				{
					break;
				}
			}
			JSONObject result = new JSONObject();
			Log.i("LogSendTasker", "Send Data:" + jsonArray.toString());
			if(result != null)
			{
				LoggerDatabaseHelper.update(logDb, keys, LoggerDatabaseHelper.SEND);
				this.callback.onUploadResult(UploadJobCallBack.TYPE_RESULT_SUCCESS);
			}
//			else
//			{
//				this.callback.onUploadResult(UploadJobCallBack.TYPE_NETWORK_UNAVAILABLE);
//			}
			
		}
		else
		{
			this.callback.onUploadResult(UploadJobCallBack.TYPE_NO_LOG);
		}
    }
	
	public interface UploadJobCallBack
	{
		public final static int TYPE_RESULT_SUCCESS = 0;
		
		public final static int TYPE_NO_LOG = 1;
		
		public final static int TYPE_NETWORK_UNAVAILABLE = 2;
		
		public final static int TYPE_FAIL = 3;
		
		public void onUploadResult(int resultType);
	}
}
