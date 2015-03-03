package com.webwalker.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.webwalker.wblogger.R;

public class MyProgressBar extends ProgressDialog {

	public MyProgressBar(Context context) {
		super(context);
	}

	public MyProgressBar(Context context, int theme) {
		super(context, theme);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.loading);
	}

	@Override
	public void setMessage(CharSequence message) {
		TextView textView = (TextView) this.findViewById(R.id.msg);
		if (textView != null)
			textView.setText(message);
	}

}
