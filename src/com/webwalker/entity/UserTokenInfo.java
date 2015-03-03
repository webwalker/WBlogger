/**
 * 
 */
package com.webwalker.entity;

import com.weibo.sdk.android.Oauth2AccessToken;

/**
 * @author Administrator
 * 
 */
public class UserTokenInfo {
	public String userAccount;
	public String accountType;
	public Oauth2AccessToken userToken;
	public String authorizeTime;
	public long expiresTime;
	
	/**
	 * @return the userAccount
	 */
	public String getUserAccount() {
		return userAccount;
	}

	/**
	 * @param userAccount
	 *            the userAccount to set
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the userToken
	 */
	public Oauth2AccessToken getUserToken() {
		return userToken;
	}

	/**
	 * @param userToken
	 *            the userToken to set
	 */
	public void setUserToken(Oauth2AccessToken userToken) {
		this.userToken = userToken;
	}

	/**
	 * @return the authorizeTime
	 */
	public String getAuthorizeTime() {
		return authorizeTime;
	}

	/**
	 * @param authorizeTime
	 *            the authorizeTime to set
	 */
	public void setAuthorizeTime(String authorizeTime) {
		this.authorizeTime = authorizeTime;
	}

	/**
	 * @return the expiresTime
	 */
	public long getExpiresTime() {
		return expiresTime;
	}

	/**
	 * @param expiresTime
	 *            the expiresTime to set
	 */
	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}

	/**
	 * 是否已过期
	 * 
	 * @return
	 */
	public boolean isExpires() {
		return getUserToken().isSessionValid();
	}
}
