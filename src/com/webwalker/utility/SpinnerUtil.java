package com.webwalker.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpinnerUtil {
	/**
	 *
	 *
	 * @return 获取时间类型ArrayList，可用于绑定SimpleAdapter,map中有"value"和"text"键值
	 */
	public static ArrayList<Map<String, String>> getDateTypeList(){
		ArrayList<Map<String, String>> rtn=new ArrayList<Map<String, String>>();
		Map<String, String> mType = new HashMap<String, String>();
		mType.put("value", "today");
		mType.put("text", "今天");
		rtn.add(mType);
		mType = new HashMap<String, String>();
		mType.put("value", "week");
		mType.put("text", "一周");
		rtn.add(mType);
		mType = new HashMap<String, String>();
		mType.put("value", "half_month");
		mType.put("text", "半个月");
		rtn.add(mType);
		mType = new HashMap<String, String>();
		mType.put("value", "month");
		mType.put("text", "一个月");
		rtn.add(mType);
		return rtn;
	}
	
}
