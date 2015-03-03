/**
 * 
 */
package com.webwalker.utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.RemoteException;
import android.os.StatFs;

import com.webwalker.entity.AppInfo;
import com.webwalker.entity.PackageSizeInfo;
import com.webwalker.utility.logger.AbstractLogger;
import com.webwalker.utility.logger.SysLogger;

/**
 * @author Administrator
 * 
 */
public class PackageUtil {

	static AbstractLogger logger = SysLogger.getInstance(Consts.LOG_PREFIX);
	private static long totalCacheSize = 0l;// 记录手机中缓存的大小
	private static PackageManager mPackageManager = null;// 在处理缓存相关的函数中使用
	private Timer mTimer = new Timer();
	private TimerTask mClearCacheTask;
	private TimerTask mKillProcessTask;

	private static final String LOG_TAG = "PackageUtils";

	Context context;

	public PackageUtil(Context context) {
		this.context = context;
	}

	public static int getVersionCode(Context context) {
		int verCode = -1;
		try {
			String name = getApplicationName(context);
			verCode = context.getPackageManager().getPackageInfo(name, 0).versionCode;
		} catch (NameNotFoundException e) {
			logger.e(LOG_TAG, e.getMessage());
		}
		return verCode;
	}

	public static String getVersionName(Context context) {
		String verName = "";
		try {
			String name = getApplicationName(context);
			verName = context.getPackageManager().getPackageInfo(name, 0).versionName;
		} catch (NameNotFoundException e) {
			logger.e(LOG_TAG, e.getMessage());
		}
		return verName;
	}

	public static String getApplicationName(Context context) {
		return context.getPackageName();
	}

	public static NotificationManager getNotifyManager(Context context) {
		return (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	// 获取已安装的应用列表
	public List<AppInfo> getInstallPackage() {

		List<AppInfo> list = new ArrayList<AppInfo>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> appPackage = pm.getInstalledPackages(0);
		for (int i = 0; i < appPackage.size(); i++) {
			try {
				PackageInfo packageInfo = appPackage.get(i);
				if ((packageInfo.applicationInfo.flags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) == 0) {

					AppInfo tmpAppInfo = new AppInfo();
					tmpAppInfo.appName = packageInfo.applicationInfo.loadLabel(
							context.getPackageManager()).toString();
					tmpAppInfo.packageName = packageInfo.packageName;
					tmpAppInfo.versionName = packageInfo.versionName;
					tmpAppInfo.versionCode = packageInfo.versionCode;
					tmpAppInfo.appIcon = packageInfo.applicationInfo
							.loadIcon(context.getPackageManager());

					tmpAppInfo.dataDir = packageInfo.applicationInfo.dataDir;

					// 通过反射机制获得该隐藏函数
					Method getPackageSizeInfo = pm.getClass()
							.getDeclaredMethod("getPackageSizeInfo",
									String.class, IPackageStatsObserver.class);
					// 调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
					getPackageSizeInfo.invoke(pm, packageInfo.packageName,
							new PkgSizeObserver(tmpAppInfo));

					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String date = format.format(new Date(new File(
							packageInfo.applicationInfo.publicSourceDir)
							.lastModified()));

					tmpAppInfo.sizeInfo = this.sizeInfo;
					tmpAppInfo.updateDate = date;

					list.add(tmpAppInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 全局变量，保存当前查询包得信息
	private PackageSizeInfo sizeInfo;

	// aidl文件形成的Bindler机制服务类
	public class PkgSizeObserver extends IPackageStatsObserver.Stub {

		AppInfo appInfo = null;

		public PkgSizeObserver(AppInfo appInfo) {
			this.appInfo = appInfo;
		}

		/***
		 * 回调函数，
		 * 
		 * @param pStatus
		 *            ,返回数据封装在PackageStats对象中
		 * @param succeeded
		 *            代表回调成功
		 */
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			sizeInfo = new PackageSizeInfo();
			sizeInfo.cachesize = pStats.cacheSize; // 缓存大小
			sizeInfo.datasize = pStats.dataSize; // 数据大小
			sizeInfo.codesize = pStats.codeSize; // 应用程序大小
			sizeInfo.totalsize = sizeInfo.cachesize + sizeInfo.datasize
					+ sizeInfo.codesize;
			appInfo.sizeInfo = sizeInfo;
		}
	}

	public static String getFormatSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	public static void startAppByPackageName(Context context, String packageName) {
		startAppByPackageName(context, packageName, null);
	}

	// 启动APP
	public static void startAppByPackageName(Context context,
			String packageName, Intent intent) {
		PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(pi.packageName);

		List<ResolveInfo> apps = context.getPackageManager()
				.queryIntentActivities(resolveIntent, 0);

		ResolveInfo ri = apps.iterator().next();
		if (ri != null) {
			String packageName1 = ri.activityInfo.packageName;
			String className = ri.activityInfo.name;

			if (intent == null) {
				intent = new Intent(Intent.ACTION_MAIN);
			}
			intent.addCategory(Intent.CATEGORY_LAUNCHER);

			ComponentName cn = new ComponentName(packageName1, className);

			intent.setComponent(cn);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}

	}

	/**
	 * 杀进程
	 */
	public static boolean killBackgroundProcess(Context context, String pkgName) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		boolean hasRunning = false;
		// 获得正在运行的所有进程
		List<ActivityManager.RunningAppProcessInfo> processes = am
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo info : processes) {
			if (info != null && info.processName != null
					&& info.processName.length() > 0
					&& info.processName.equals(pkgName)) {
				hasRunning = true;
			}
		}

		if (hasRunning && !(pkgName.startsWith("com.webwalker"))) {
			am.killBackgroundProcesses(pkgName);
			return hasRunning;
		}

		return hasRunning;
	}

	/**
	 * 杀掉所有后台进程
	 * 
	 * @param context
	 */
	void killAllProcess() {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> processes = am
				.getRunningAppProcesses();

		for (ActivityManager.RunningAppProcessInfo info : processes) {
			if (info != null && info.processName != null
					&& info.processName.length() > 0) {
				String pkgName = info.processName;
				if (!("system".equals(pkgName)
						|| "android.process.media".equals(pkgName)
						|| "android.process.acore".equals(pkgName)
						|| "com.android.phone".equals(pkgName) || pkgName
							.startsWith("com.lefter"))) {
					try {
						am.killBackgroundProcesses(pkgName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void clearCacheSchedule() {
		if (mClearCacheTask != null) {
			mClearCacheTask.cancel();
		}
		mClearCacheTask = new ClearCacheTask();
		mTimer.schedule(mClearCacheTask, 0);
	}

	public void killProcessSchedule() {
		if (mKillProcessTask != null) {
			mKillProcessTask.cancel();
		}
		mKillProcessTask = new KillProcessTask();
		mTimer.schedule(mKillProcessTask, 0);
	}

	class ClearCacheTask extends TimerTask {

		@Override
		public void run() {
			clearCache();
		}
	}

	class KillProcessTask extends TimerTask {
		@Override
		public void run() {
			killAllProcess();
		}
	}

	/**
	 * 执行清除缓存操作
	 */
	void clearCache() {
		totalCacheSize = 0l;// 初始化
		queryToatalCache(context);// 给cacheSize赋值
		try {
			if (mPackageManager == null) {
				mPackageManager = context.getPackageManager();// 得到被反射调用函数所在的类对象
			}
			String methodName = "freeStorageAndNotify";// 想通过反射机制调用的方法名
			Class<?> parameterType1 = Long.TYPE;// 被反射的方法的第一个参数的类型
			Class<?> parameterType2 = IPackageDataObserver.class;// 被反射的方法的第二个参数的类型
			Method freeStorageAndNotify = mPackageManager.getClass().getMethod(
					methodName, parameterType1, parameterType2);
			/*
			 * freeStorageSize ： The number of bytes of storage to be freed by
			 * the system. Say if freeStorageSize is XX, and the current free
			 * storage is YY, if XX is less than YY, just return. if not free
			 * XX-YY number of bytes if possible.
			 */
			Long freeStorageSize = Long.valueOf(getDataDirectorySize());

			freeStorageAndNotify.invoke(mPackageManager, freeStorageSize,
					new IPackageDataObserver.Stub() {
						@Override
						public void onRemoveCompleted(String packageName,
								boolean succeeded) throws RemoteException {
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用Android系统中的AIDL文件，获得指定程序的大小。AIDL文件形成的Binder机制
	 */
	static IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			totalCacheSize += pStats.cacheSize;
		}
	};

	/**
	 * 获得手机中所有程序的缓存
	 */
	static void queryToatalCache(Context context) {
		if (mPackageManager == null) {
			mPackageManager = context.getPackageManager();
		}
		List<ApplicationInfo> apps = mPackageManager
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
						| PackageManager.GET_ACTIVITIES);
		String pkgName = "";
		for (ApplicationInfo info : apps) {
			pkgName = info.packageName;
			try {
				queryPkgCacheSize(context, pkgName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 返回/data目录的大小。
	 */
	static long getDataDirectorySize() {
		File tmpFile = Environment.getDataDirectory();
		if (tmpFile == null) {
			return 0l;
		}
		String strDataDirectoryPath = tmpFile.getPath();
		StatFs localStatFs = new StatFs(strDataDirectoryPath);
		long size = localStatFs.getBlockSize() * localStatFs.getBlockCount();
		return size;
	}

	/**
	 * 取得指定包名的程序的缓存大小
	 */
	static void queryPkgCacheSize(Context context, String pkgName)
			throws Exception {
		// 使用放射机制得到PackageManager类的隐藏函数getPackageSizeInfo
		if (mPackageManager == null) {
			mPackageManager = context.getPackageManager();// 得到被反射调用函数所在的类对象
		}
		try {
			// the requested method's name.
			String strGetPackageSizeInfo = "getPackageSizeInfo";
			// 通过反射机制获得该隐藏函数
			Method getPackageSizeInfo = mPackageManager.getClass()
					.getDeclaredMethod(strGetPackageSizeInfo, String.class,// getPackageSizeInfo方法的参数类型
							IPackageStatsObserver.class);// getPackageSizeInfo方法的参数类型
			// 调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
			getPackageSizeInfo.invoke(mPackageManager,// 方法所在的类
					pkgName, mStatsObserver);// 方法使用的参数

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex; // 抛出异常
		}
	}

	static DataInputStream Terminal(String command) throws Exception {
		Process process = Runtime.getRuntime().exec("su");
		// 执行到这，Superuser会跳出来，选择是否允许获取最高权限
		OutputStream outstream = process.getOutputStream();
		DataOutputStream DOPS = new DataOutputStream(outstream);
		InputStream instream = process.getInputStream();
		DataInputStream DIPS = new DataInputStream(instream);
		String temp = command + "\n";
		// 加回车
		DOPS.writeBytes(temp);
		// 执行
		DOPS.flush();
		// 刷新，确保都发送到outputstream
		DOPS.writeBytes("exit\n");
		// 退出
		DOPS.flush();
		process.waitFor();
		return DIPS;
	}

	public static boolean isRooted() {
		// 检测是否ROOT过
		DataInputStream stream;
		boolean flag = false;
		try {
			stream = Terminal("ls /data/");
			// 目录哪都行，不一定要需要ROOT权限的
			if (stream.readLine() != null)
				flag = true;
			// 根据是否有返回来判断是否有root权限
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

		return flag;
	}

	/**
	 * 安装apk
	 */
	public static void installApk(Context context, String fileName) {
		try {
			logger.i(LOG_TAG, "installApk:" + fileName);
			File apkfile = new File(fileName);
			if (!apkfile.exists()) {
				return;
			}
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setDataAndType(Uri.fromFile(apkfile),
					"application/vnd.android.package-archive");
			context.startActivity(i);
		} catch (Exception e) {
			logger.e(LOG_TAG, e);
		}
	}
}