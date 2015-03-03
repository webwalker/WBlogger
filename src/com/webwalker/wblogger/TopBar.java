package com.webwalker.wblogger;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.webwalker.wblogger.R;

/**
 	xmlns:test="http://schemas.android.com/apk/res/com.android.tutor"
	xmlns:custom="http://schemas.android.com/apk/res/com.android.custom"
	
    <com.webwalker.widget.TopBar 
        android:id="@+id/topBar"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:background="#0000ff"
        custom:leftBackground="@drawable/icon"
        custom:leftText="左侧"
        custom:leftTextColor="#123123"
        custom:rightBackground="@drawable/icon"
        custom:rightText="右侧"
        custom:rightTextColor="#ff0000"
        custom:title="自定义标题"
        custom:titleTextColor="#ff0000"
        custom:titleTextSize="14.0sp" >
    </com.webwalker.widget.TopBar>
    
	private TopBar topBar;	
	topBar = (TopBar)findViewById(R.id.topBar);   
	        topBar.setTopBarClickListener(new TopBarClickListener() {   
	            @Override  
	            public void rightBtnClick() {   
	                //处理右边按钮所对应的逻辑   
	                Toast.makeText(CustomViewActivity.this, "你点击的是右边的按钮", Toast.LENGTH_LONG).show();   
	            }   
	               
	            @Override  
	            public void leftBtnClick() {   
	                //处理左边按钮所对应的逻辑   
	                Toast.makeText(CustomViewActivity.this, "你点击的是左边的按钮", Toast.LENGTH_LONG).show();   
	            }   
	        });   
        
 * @author Administrator
 *
 */
public class TopBar extends RelativeLayout {

	private Button leftBtn, rightBtn;
	private TextView title;
	private TopBarClickListener topBarClickListener;
	private String titleStr;

	private RelativeLayout.LayoutParams leftBtnLayoutParams,
			rightBtnLayoutParams, titleLayoutParams;
	private static int LEFT_BTN_ID = 1;
	private static int TITLE_ID = 2;
	private static int RIGHT_BTN_ID = 3;

	private Drawable leftBackground, rightBackground;
	private String leftText, rightText;
	private int leftTextColor, rightTextColor, titleTextColor;
	private float titleTextSize;

	public interface TopBarClickListener {
		void leftBtnClick();
		void rightBtnClick();
	}

	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.MyView);

		this.titleStr = ta.getString(R.styleable.MyView_title);
		this.leftBackground = ta.getDrawable(R.styleable.MyView_leftBackground);
		this.rightBackground = ta
				.getDrawable(R.styleable.MyView_rightBackground);
		this.leftText = ta.getString(R.styleable.MyView_leftText);
		this.rightText = ta.getString(R.styleable.MyView_rightText);
		this.leftTextColor = ta.getColor(R.styleable.MyView_leftTextColor, 0);
		this.rightTextColor = ta.getColor(R.styleable.MyView_rightTextColor, 0);
		this.titleTextSize = ta.getDimension(R.styleable.MyView_titleTextSize,
				14);
		this.titleTextColor = ta.getColor(R.styleable.MyView_titleTextColor, 0);

		ta.recycle();

		leftBtn = new Button(context);
		rightBtn = new Button(context);
		title = new TextView(context);

		leftBtn.setId(LEFT_BTN_ID);
		rightBtn.setId(RIGHT_BTN_ID);
		title.setId(TITLE_ID);

		leftBtnLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rightBtnLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		titleLayoutParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		leftBtnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
				RelativeLayout.TRUE);
		leftBtnLayoutParams.setMargins(12, 0, 0, 0);// 左上右下
		leftBtnLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);

		rightBtnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		rightBtnLayoutParams.setMargins(12, 0, 0, 0);// 左上右下
		rightBtnLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);

		titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		titleLayoutParams.setMargins(0, 0, 12, 0);// 左上右下
		titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);
		titleLayoutParams.addRule(RelativeLayout.LEFT_OF, RIGHT_BTN_ID);
		titleLayoutParams.addRule(RelativeLayout.RIGHT_OF, LEFT_BTN_ID);
		titleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,
				RelativeLayout.TRUE);

		addView(leftBtn, leftBtnLayoutParams);
		addView(rightBtn, rightBtnLayoutParams);
		addView(title, titleLayoutParams);

		leftBtn.setBackgroundDrawable(leftBackground);
		leftBtn.setText(leftText);
		leftBtn.setTextColor(leftTextColor);
		rightBtn.setBackgroundDrawable(rightBackground);
		rightBtn.setText(rightText);
		rightBtn.setTextColor(rightTextColor);

		title.setTextSize(22.0f);
		title.setTextColor(Color.BLUE);
		title.setEllipsize(TruncateAt.MIDDLE);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setSingleLine(true);
		title.setText(titleStr);
		title.setTextSize(titleTextSize);
		title.setTextColor(titleTextColor);

		leftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (topBarClickListener != null) {
					topBarClickListener.leftBtnClick();
				}
			}
		});

		rightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (topBarClickListener != null) {
					topBarClickListener.rightBtnClick();
				}
			}
		});
	}

	public void setTopBarClickListener(TopBarClickListener topBarClickListener) {
		this.topBarClickListener = topBarClickListener;
	}

}
