/**
 * 
 */
package com.webwalker.version;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;

/**
 * @author Administrator
 * 
 */
public class BaseAppUpdate {

	private static final int PROGRESS_MAX = 100;
	protected ProgressDialog progressDialog;
	private Context context;

	public BaseAppUpdate(Context context) {
		this.context = context;
	}

	

	public void OnClickOK() {
	}
}
