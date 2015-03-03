/**
 * 
 */
package com.webwalker.utility.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.webwalker.utility.Consts;

/**
 * @author Administrator
 * 
 */
public class SharedPreference implements IStoreProvider {

	private Context _Context = null;

	public SharedPreference(Context context) {
		_Context = context;
	}

	@Override
	public String Get(String key) {
		SharedPreferences sp = _Context.getSharedPreferences(
				Consts.CONTEXT_SHARED_PREFERENCE, Context.MODE_PRIVATE);

		return sp.getString(key, "");
	}

	public List<String> GetList(String key) {
		String jsonStr = (String) Get(key);
		List<String> list = new ArrayList<String>();
		try {
			JSONArray jsonArr = new JSONArray(jsonStr);
			for (int i = jsonArr.length() - 1; i >= 0; i--) {
				String str = jsonArr.getString(i);
				list.add(str);
			}
		} catch (Exception e) {
		}
		return list;
	}

	/**
	 * value可以存放序列化数据
	 */
	@Override
	public void Set(String key, String value) {
		SharedPreferences sp = _Context.getSharedPreferences(
				Consts.CONTEXT_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 填充数组
	 * 
	 * @param key
	 * @param list
	 */
	public void Set(String key, List<String> list) {
		JSONArray array = new JSONArray();
		for (String str : list) {
			array.put(str);
		}

		Set(key, array.toString());
	}

	@Override
	public void Remove(String key) {
		SharedPreferences sp = _Context.getSharedPreferences(
				Consts.CONTEXT_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}

	@Override
	public void Clear() {
		SharedPreferences sp = _Context.getSharedPreferences(
				Consts.CONTEXT_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		Map<String, ?> map = sp.getAll();
		if (map != null) {
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				edit.remove(it.next());
			}
		}
		edit.clear();
		edit.commit();
	}
}
