/**
 * 
 */
package com.webwalker.utility.entity;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 */
public class UpdateEntity implements Serializable {
	public String status;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public int versionCode;

	/**
	 * @return the versionCode
	 */
	public int getVersionCode() {
		return versionCode;
	}

	/**
	 * @param versionCode
	 *            the versionCode to set
	 */
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public int minVersionCode;

	/**
	 * @return the minVersionCode
	 */
	public int getMinVersionCode() {
		return minVersionCode;
	}

	/**
	 * @param minVersionCode
	 *            the minVersionCode to set
	 */
	public void setMinVersionCode(int minVersionCode) {
		this.minVersionCode = minVersionCode;
	}

	public String versionName;

	/**
	 * @return the versionName
	 */
	public String getVersionName() {
		return versionName;
	}

	/**
	 * @param versionName
	 *            the versionName to set
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String versionInfo;

	/**
	 * @return the versionInfo
	 */
	public String getVersionInfo() {
		return versionInfo;
	}

	/**
	 * @param versionInfo
	 *            the versionInfo to set
	 */
	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}

	public String updateTime;

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String debugUrl;

	/**
	 * @return the debugUrl
	 */
	public String getDebugUrl() {
		return debugUrl;
	}

	/**
	 * @param debugUrl
	 *            the debugUrl to set
	 */
	public void setDebugUrl(String debugUrl) {
		this.debugUrl = debugUrl;
	}

	public String releaseUrl;

	/**
	 * @return the releaseUrl
	 */
	public String getReleaseUrl() {
		return releaseUrl;
	}

	/**
	 * @param releaseUrl
	 *            the releaseUrl to set
	 */
	public void setReleaseUrl(String releaseUrl) {
		this.releaseUrl = releaseUrl;
	}

	public boolean isNeedUpdate;

	/**
	 * @return the isNeedUpdate
	 */
	public boolean isNeedUpdate() {
		return isNeedUpdate;
	}

	/**
	 * @param isNeedUpdate
	 *            the isNeedUpdate to set
	 */
	public void setNeedUpdate(boolean isNeedUpdate) {
		this.isNeedUpdate = isNeedUpdate;
	}

	public boolean isMustUpdate;

	/**
	 * @return the isMustUpdate
	 */
	public boolean isMustUpdate() {
		return isMustUpdate;
	}

	/**
	 * @param isMustUpdate
	 *            the isMustUpdate to set
	 */
	public void setMustUpdate(boolean isMustUpdate) {
		this.isMustUpdate = isMustUpdate;
	}
}
