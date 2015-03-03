/**
 * 
 */
package com.webwalker.utility.network;

/**
 * <p>
 * 注释
 * </p>
 * 
 * @author Frank.fan
 * @version $Id: IntentName.java, v 0.1 2011-12-12 下午12:48:22 fanmanrong Exp $
 */
public enum ResultStatusEnum {

	OK("OK", "成功"),

	ERROR("ERROR", "失败"),

	STATUS("status", "状态"),

	RESULT("result", "结果"),

	RESULT_CODE("resultCode", "结果代码"),

	RESULT_MESSAGE("resultMessage", "结果信息"),

	RESULT_MESSAGE_NEW("message", "结果信息");

	/** 代码 */
	private final String code;
	/** 信息 */
	private final String message;

	ResultStatusEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 根据code获取ENUM
	 * 
	 * @param code
	 * @return
	 */
	public static ResultStatusEnum getByName(String code) {
		for (ResultStatusEnum resultCode : ResultStatusEnum.values()) {
			if (resultCode.getCode().equals(code)) {
				return resultCode;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
