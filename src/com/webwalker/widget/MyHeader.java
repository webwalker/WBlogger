package com.webwalker.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.webwalker.wblogger.R;

public class MyHeader extends RelativeLayout {

	// control
	public TextView title;

	// values
	private String t;

	public MyHeader(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.com_head, this);

		// bind controls
		title = (TextView) findViewById(R.id.tvText);
		// get values
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MyView);
		t = ta.getString(R.styleable.MyView_title);

		// set values
		title.setText(t);
	}

	public void setTitle(String text) {
		title.setText(text);
	}
}
