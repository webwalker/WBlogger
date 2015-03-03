/**
 * 
 */
package com.webwalker.utility.entity;

import com.webwalker.utility.PathUtil;

import android.annotation.SuppressLint;
import android.widget.ProgressBar;

/**
 * @author Administrator
 * 
 */
public class DownEntity {
	private String Url;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return Url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		Url = url;
	}

	private String FileName;

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return FileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getSaveFileName() {
		return DownPath + FileName;
	}

	private int ThreadCount = 5;

	/**
	 * @return the threadCount
	 */
	public int getThreadCount() {
		return ThreadCount;
	}

	/**
	 * @param threadCount
	 *            the threadCount to set
	 */
	public void setThreadCount(int threadCount) {
		ThreadCount = threadCount;
	}

	private String DownPath = "";

	/**
	 * @return the filePath
	 */
	public String getDownPath() {
		if (DownPath.equals(""))
			DownPath = PathUtil.getSDCardPath();

		DownPath += "/";
		return DownPath;
	}

	/**
	 * 以SD卡为根目录
	 */
	public void setDownPath(String filePath) {
		DownPath = filePath;
	}

	private ProgressBar progressBar;

	/**
	 * @return the progressBar
	 */
	public ProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * @param progressBar
	 *            the progressBar to set
	 */
	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

}
