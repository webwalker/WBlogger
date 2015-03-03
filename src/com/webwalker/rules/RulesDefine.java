/**
 * 
 */
package com.webwalker.rules;

/**
 * @author Administrator
 * 
 */
public class RulesDefine {
	public enum MonitorRule {
		/**
		 * 此ID之后的微博变化
		 */
		MAPSI(1, "此ID之后的微博变化", ">post_since_id"),

		MARSI(2, "此ID之后转发的微博变化", ">repost_since_id");

		/** 代码 */
		private final int code;
		/** 信息 */
		private final String message;
		private final String regex;

		MonitorRule(int code, String message, String regex) {
			this.code = code;
			this.message = message;
			this.regex = regex;
		}

		/**
		 * 根据code获取ENUM
		 * 
		 * @param code
		 * @return
		 */
		public static MonitorRule getByCode(int code) {
			for (MonitorRule resultCode : MonitorRule.values()) {
				if (resultCode.getCode() == code) {
					return resultCode;
				}
			}

			return null;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		/**
		 * @return the regex
		 */
		public String getRegex() {
			return regex;
		}
	}

	public enum TimeRule {
		TR(1, "两小时内随机", "<h2");

		/** 代码 */
		private final int code;
		/** 信息 */
		private final String message;
		private final String regex;

		TimeRule(int code, String message, String regex) {
			this.code = code;
			this.message = message;
			this.regex = regex;
		}

		/**
		 * 根据code获取ENUM
		 * 
		 * @param code
		 * @return
		 */
		public static TimeRule getByCode(int code) {
			for (TimeRule resultCode : TimeRule.values()) {
				if (resultCode.getCode() == code) {
					return resultCode;
				}
			}

			return null;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		/**
		 * @return the regex
		 */
		public String getRegex() {
			return regex;
		}
	}

	public enum TaskRule {
		UPW(1, "获取用户发布的最新微博列表", "top 5"),

		UPWID(2, "获取用户发布的最新微博ID", ""),

		UFW(3, "获取用户转发的最新微博", "=since_id"),

		PW(4, "发布微博", ""), FW(5, "转发微博", ""),

		GUIID(1000, "根据UID获取用户信息", ""),

		GUINICKNAME(1001, "根据用户昵称获取用户信息", "");

		/** 代码 */
		private final int code;
		/** 信息 */
		private final String message;
		private final String regex;

		TaskRule(int code, String message, String regex) {
			this.code = code;
			this.message = message;
			this.regex = regex;
		}

		/**
		 * 根据code获取ENUM
		 * 
		 * @param code
		 * @return
		 */
		public static TaskRule getByCode(int code) {
			for (TaskRule resultCode : TaskRule.values()) {
				if (resultCode.getCode() == code) {
					return resultCode;
				}
			}

			return null;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		/**
		 * @return the regex
		 */
		public String getRegex() {
			return regex;
		}
	}
}
