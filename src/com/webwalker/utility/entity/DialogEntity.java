/**
 * 
 */
package com.webwalker.utility.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * 对话框模型数据模型
 * 
 * @author Administrator
 * 
 */
public class DialogEntity {

	Context _Context;

	public DialogEntity(Context context) {
		_Context = context;
	}

	public String Title;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}

	public void setTitle(int resId) {
		Title = _Context.getResources().getString(resId);
	}

	public Drawable Icon;

	/**
	 * @return the icon
	 */
	public Drawable getIcon() {
		return Icon;
	}

	/**
	 * @param resId
	 *            the icon to set
	 */
	public void setIcon(int resId) {
		Icon = _Context.getResources().getDrawable(resId);
	}

	/**
	 * @param resId
	 *            the icon to set
	 */
	public void setIcon(Drawable drawable) {
		Icon = drawable;
	}

	public String Text;
	public CharSequence charSequence;

	/**
	 * @return the text
	 */
	public String getStringMessage() {
		return Text;
	}

	public CharSequence getMessage() {
		return charSequence;
	}
	
	/**
	 * @param text
	 *            the text to set
	 */
	public void setMessage(String text) {
		Text = text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setMessage(CharSequence cs) {
		charSequence = cs;
	}

	public void setMessage(int resId) {
		Text = _Context.getResources().getString(resId);
	}

	public String OK;

	/**
	 * @return the oK
	 */
	public String getOK() {
		return OK;
	}

	/**
	 * @param oK
	 *            the oK to set
	 */
	public void setOK(String oK) {
		OK = oK;
	}

	public void setOK(int resId) {
		OK = _Context.getResources().getString(resId);
	}

	public String Cancel;

	/**
	 * @return the cancel
	 */
	public String getCancel() {
		return Cancel;
	}

	/**
	 * @param cancel
	 *            the cancel to set
	 */
	public void setCancel(String cancel) {
		Cancel = cancel;
	}

	public void setCancel(int resId) {
		Cancel = _Context.getResources().getString(resId);
	}
}
