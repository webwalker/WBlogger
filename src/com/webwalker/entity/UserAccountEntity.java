/**
 * 
 */
package com.webwalker.entity;

import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class UserAccountEntity {
	public int id;
	public int appid;
	public String uaid;
	public String nickname;
	public String uid;
	public String subuid;
	public String usertoken;
	public long expiresin;
	public int isparent;
	public int status;
	public int imgId;

	public String createDate;
	public Date updatedate;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the appid
	 */
	public int getAppid() {
		return appid;
	}

	/**
	 * @param appid
	 *            the appid to set
	 */
	public void setAppid(int appid) {
		this.appid = appid;
	}

	/**
	 * @return the uaid
	 */
	public String getUaid() {
		return uaid;
	}

	/**
	 * @param uaid
	 *            the uaid to set
	 */
	public void setUaid(String uaid) {
		this.uaid = uaid;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the subuid
	 */
	public String getSubuid() {
		return subuid;
	}

	/**
	 * @param subuid
	 *            the subuid to set
	 */
	public void setSubuid(String subuid) {
		this.subuid = subuid;
	}

	/**
	 * @return the usertoken
	 */
	public String getUsertoken() {
		return usertoken;
	}

	/**
	 * @param usertoken
	 *            the usertoken to set
	 */
	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}

	/**
	 * @return the expiresin
	 */
	public long getExpiresin() {
		return expiresin;
	}

	/**
	 * @param expiresin
	 *            the expiresin to set
	 */
	public void setExpiresin(long expiresin) {
		this.expiresin = expiresin;
	}

	/**
	 * @return the isparent
	 */
	public int getIsparent() {
		return isparent;
	}

	/**
	 * @param isparent
	 *            the isparent to set
	 */
	public void setIsparent(int isparent) {
		this.isparent = isparent;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updatedate
	 */
	public Date getUpdatedate() {
		return updatedate;
	}

	/**
	 * @param updatedate
	 *            the updatedate to set
	 */
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	/**
	 * @return the imgId
	 */
	public int getImgId() {
		return imgId;
	}

	/**
	 * @param imgId
	 *            the imgId to set
	 */
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
}
