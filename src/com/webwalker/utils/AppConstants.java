package com.webwalker.utils;

/**
 * 常量定义
 * 
 * @author Administrator
 * 
 */
public final class AppConstants {
	public static final String LOG_PREFIX = "wblogger_";
	/**
	 * This progress style is just for sub-Activity to show the different UI
	 * progress.
	 */
	public static final int TYPE_PROGRESS_STYLE_START = 0;
	public static final int TYPE_PROGRESS_STYLE_UNCANCELABLE = TYPE_PROGRESS_STYLE_START + 1;
	public static final int TYPE_PROGRESS_STYLE_CANCELABLE = TYPE_PROGRESS_STYLE_START + 2;
	public static final int TYPE_PROGRESS_STYLE_END = TYPE_PROGRESS_STYLE_START + 3;
	public static final int TYPE_PROGRESS_DEFAULT = TYPE_PROGRESS_STYLE_START + 3;

	/**
	 * This is dialog style.
	 */
	public static final int TYPE_DIALOG_STYLE_START = TYPE_PROGRESS_DEFAULT + 1;
	public static final int TYPE_DIALOG_STYLE_ALERT = TYPE_DIALOG_STYLE_START + 1;
	public static final int TYPE_DIALOG_STYLE_CONFIRM = TYPE_DIALOG_STYLE_START + 2;
	public static final int TYPE_DIALOG_STYLE_END = TYPE_DIALOG_STYLE_START + 3;
	/**
	 * This is menu dilog.
	 */
	public static final int TYPE_DIALOG_MENU_START = TYPE_DIALOG_STYLE_END + 1;
	public static final int TYPE_DIALOG_MENU_EXIT = TYPE_DIALOG_MENU_START + 1;
	public static final int TYPE_DIALOG_MENU_END = TYPE_DIALOG_MENU_START + 2;

	public final String CONTEXT_SHARED_PREFERENCE = "context_shared_preference";

	public class OAuth {
		public static final String authtype = "authtype";
		public static final String uid = "uid";
		public static final String token = "token";
		public static final String access_token = "access_token";
		public static final String expires_in = "expires_in";
		public static final String expiresTime = "expiresTime";
		public static final String tokens = "tokens";

		public static final String appid = "appid";
		public static final String isupdate = "isupdate";

		public static final String CONSUMER_KEY = "191220928";
		public static final String REDIRECT_URL = "http://www.mcan.cn/weibo/callback.htm";
		// sina
		public static final int sina = 1;
		// tencent
		public static final int tencent = 2;
	}

	public class RequestCode {
		public static final int WeiboActive = 1;
		public static final int NormalAccount = 100;
	}

	public static final int validStatus = 1;
	public static final String EncryptKEY = "web123!#";
	public static final String ReferIntent = "ReferIntent";
	public static final String MapKey = "prompt";
	public static final String MapValue = "value";
	public static final String TaskList = "TaskList";

	public static final String ReceiverFilter = "com.webwalker.receiver.reminder";

	public class Keys {
		public static final String MonitorKeys = "MonitorKeys";
		public static final String NotifyTitle = "NotifyTitle";
		public static final String NotifyBody = "NotifyBody";
		public static final String AppList = "AppList";
		public static final String HasGuide = "HasGuide";
		public static final String ExitApp = "EXIT_APP";
		public static final String CurrentApp = "CurrentApp";
		public static final String MSG = "msg";
	}

	public class TaskStatus {
		/**
		 * 新建任务
		 */
		public static final int STATE_NEW = 1;
		/**
		 * 正在运行
		 */
		public static final int STATE_RUNNING = 2;
		/**
		 * 任务完成
		 */
		public static final int STATE_COMPLETED = 3;
		/**
		 * 任务暂停
		 */
		public static final int STATE_PAUSED = 4;
		/**
		 * 任务失败
		 */
		public static final int STATE_FAILED = 5;
	}

	public class MessageWhat {
		/**
		 * 显示toast消息
		 */
		public static final int Toast = 1;
	}
}
