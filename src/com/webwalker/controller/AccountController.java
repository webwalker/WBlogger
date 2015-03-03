/**
 * 
 */
package com.webwalker.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.webwalker.adpater.ListViewItem;
import com.webwalker.adpater.ViewHolder.Keys;
import com.webwalker.data.MyDataHelper;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.listener.IActionListener;
import com.webwalker.listener.IClickListener;
import com.webwalker.listener.MyClickListener;
import com.webwalker.utility.Encrypter;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.wblogger.R;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public class AccountController extends BaseController {

	MyDataHelper helper = null;

	public AccountController(Context context) {
		super(context);
		helper = new MyDataHelper(context);
	}

	public AccountController(Context context, IActionListener listener) {
		super(context, listener);
		helper = new MyDataHelper(context);
	}

	// 获得单个账户信息
	public UserAccountEntity getSingleAccount(int id) {

		UserAccountEntity user = null;

		try {
			Cursor cursor = helper.getSingleAccount(id);
			if (cursor.moveToNext()) {
				user = new UserAccountEntity();

				user.setId(cursor.getInt(cursor.getColumnIndex("id")));
				user.setAppid(cursor.getInt(cursor.getColumnIndex("app_id")));
				user.setNickname(cursor.getString(cursor
						.getColumnIndex("nickname")));
				user.setUid(cursor.getString(cursor.getColumnIndex("uid")));
				user.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
				user.setExpiresin(cursor.getLong(cursor
						.getColumnIndex("expires_in")));

				String token = cursor.getString(cursor
						.getColumnIndex("user_token"));
				if (!token.equals(""))
					token = Encrypter.decryptDES(token);
				user.setUsertoken(token);

				user.setCreateDate(cursor.getString(cursor
						.getColumnIndex("createdate")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}

		return user;
	}

	// 判断账号是否已存在
	public UserAccountEntity getSingleAccountByName(int appId,
			String nickname) {

		UserAccountEntity user = null;
		try {
			Cursor cursor = helper.getSingleAccountByName(appId, nickname);
			if (cursor.moveToNext()) {
				user = new UserAccountEntity();

				user.setId(cursor.getInt(cursor.getColumnIndex("id")));
				// user.setAppid(cursor.getInt(cursor.getColumnIndex("app_id")));
				// user.setNickname(cursor.getString(cursor
				// .getColumnIndex("nickname")));
				// user.setUid(cursor.getString(cursor.getColumnIndex("uid")));
				// user.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
				// user.setExpiresin(cursor.getLong(cursor
				// .getColumnIndex("expires_in")));
				//
				// String token = cursor.getString(cursor
				// .getColumnIndex("user_token"));
				// if (!token.equals(""))
				// token = Encrypter.decryptDES(token);
				// user.setUsertoken(token);
				//
				// user.setCreateDate(cursor.getString(cursor
				// .getColumnIndex("createdate")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}
		return user;
	}

	// 获取某一个App下的所有子账号
	public ArrayList<UserAccountEntity> getAccount(String isParent, int appId) {
		ArrayList<UserAccountEntity> list = new ArrayList<UserAccountEntity>();

		try {
			Cursor cursor = helper.getAccount(isParent, appId);

			while (cursor.moveToNext()) {
				UserAccountEntity user = new UserAccountEntity();

				user.setId(cursor.getInt(cursor.getColumnIndex("id")));
				user.setNickname(cursor.getString(cursor
						.getColumnIndex("nickname")));
				user.setUid(cursor.getString(cursor.getColumnIndex("uid")));
				user.setCreateDate(cursor.getString(cursor
						.getColumnIndex("createdate")));
				user.setExpiresin(cursor.getLong(cursor
						.getColumnIndex("expires_in")));
				user.setAppid(appId);
				user.setImgId(new AppController().getAppIcon(appId));

				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}
		return list;
	}

	// 获取所有APP下的子账号
	public ArrayList<UserAccountEntity> getAllSubAccount(int isParent) {
		ArrayList<UserAccountEntity> list = new ArrayList<UserAccountEntity>();

		try {
			Cursor cursor = helper.getAllSubAccount(String.valueOf(isParent));

			while (cursor.moveToNext()) {
				UserAccountEntity user = new UserAccountEntity();

				user.setId(cursor.getInt(cursor.getColumnIndex("id")));
				user.setNickname(cursor.getString(cursor
						.getColumnIndex("nickname")));
				user.setUid(cursor.getString(cursor.getColumnIndex("uid")));
				user.setCreateDate(cursor.getString(cursor
						.getColumnIndex("createdate")));
				user.setExpiresin(cursor.getLong(cursor
						.getColumnIndex("expires_in")));

				int appId = cursor.getInt(cursor.getColumnIndex("app_id"));
				user.setAppid(appId);
				user.setImgId(new AppController().getAppIcon(appId));

				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}
		return list;
	}

	// 绑定账号
	public boolean BindAccount(UserAccountEntity user, List<ListViewItem> list) {
		try {
			helper.updateAccountMapping(user, list);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 解绑
	public boolean UnBindAccount(int uaid, List<ListViewItem> list) {
		try {
			for (ListViewItem item : list) {
				helper.deleteAccountMapping(uaid, item.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 删除选中的子账号
	public boolean deleteSubAccount(final List<ListViewItem> list) {

		IClickListener clicklistener = new MyClickListener() {
			@Override
			public void OnClickOK() {
				try {
					helper.batchDeleteSubAccount(list);

					listener.OnAction();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void OnClickCancel() {
			}
		};

		MessagesUtil m = new MessagesUtil(context);
		m.ShowDialog(R.string.dialog_title_confirm, R.string.msg_delete_sub,
				R.string.dialog_ok, R.string.dialog_cancel, clicklistener);
		return true;
	}

	// 根据主账号获取绑定的子账号列表
	public ArrayList<String> getAccountMapping(int uaId) {
		ArrayList<String> list = new ArrayList<String>();

		try {
			Cursor cursor = helper.getAccountMapping(uaId);

			String uid = "";
			while (cursor.moveToNext()) {
				uid = cursor.getString(cursor.getColumnIndex("sub_uid"));
				if (!list.contains(uid))
					list.add(uid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}

		return list;
	}
}
