/**
 * 
 */
package com.webwalker.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;

/**
 * @author Administrator
 * 
 */
public class PathUtil {
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath() + "/";
	}

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	public static String getFileName(String url) {
		Pattern pattern = Pattern.compile("([^\\\\/]+\\.\\w+)$");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return matcher.group();
		}
		return "";
	}
}
