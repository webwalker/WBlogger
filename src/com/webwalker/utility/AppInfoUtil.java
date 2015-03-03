/**
 * 
 */
package com.webwalker.utility;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * <p>注释</p>
 * @author Frank.fan
 * @version $Id: AppInfoUtil.java, v 0.1 2012-3-23 下午1:14:34 fanmanrong Exp $
 */
public class AppInfoUtil {

    public static List<String[]> getVersionCode(Context context) {
        List<String[]> appList = new ArrayList<String[]>(); //用来存储获取的应用信息数据

        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packages.size(); i++) {
            String[] tmpInfo = new String[5];
            PackageInfo packageInfo = packages.get(i);
            tmpInfo[0] = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();//.appName
            tmpInfo[1] = packageInfo.packageName;//.packageName
            tmpInfo[2] = packageInfo.versionName;//.versionName
            tmpInfo[3] = packageInfo.versionCode + "";//.versionCode
            appList.add(tmpInfo);

        }
        return appList;
    }

    public static boolean isInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        for (PackageInfo ai : pm.getInstalledPackages(0)) {
            if (packageName.equals(ai.packageName)) {
                return true;
            }
        }
        return false;
    }
}
