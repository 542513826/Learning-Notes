package com.smartdot.mobile.portal.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MyappUtil {

    // 安装指定应用的apk
    public static void installApp(Context context, String appName) {
        // String str = "/CanavaCancel.apk";
        String fileName = Environment.getExternalStorageDirectory() + "" + appName;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    // 卸载指定应用的apk
    // 可以考虑获取删除的结果并返回
    public static void removeApp(Context context, String appName) {
        String pckgName = "package:" + appName;
        Uri packageURI = Uri.parse(pckgName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    // 打开APK程序代码
    public static void openFile(File file, Context context) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /** 根据包名打开另一个应用 */
    public static void startAnotherApp(Context context, String packageName) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            CustomToast.showToast(context, "无法打开该应用！");
            return;
        }
        if (packageInfo == null) {
            System.out.println("packageInfo==null");
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);
        System.out.println("packageInfo.packageName=" + packageInfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveInfo = resolveInfoList.iterator().next();
        if (resolveInfo != null) {
            // 获取包名 activityPackageName = 参数packname
            String activityPackageName = resolveInfo.activityInfo.packageName;
            // 获取Activity名
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveInfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置ComponentName参数1:packagename参数 2:MainActivity路径
            ComponentName cn = new ComponentName(activityPackageName, className);

            intent.setComponent(cn);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "无法打开该应用！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** 根据包名和Activity名打开另一个应用，不显示图标版，参数启动时传的值*/
    public static void startAnotherGoneApp(Context context, String packageName, String classname,
            Map<String, String> map) {

        // 获取包名 activityPackageName = 参数packname
        String activityPackageName = packageName;
        // 获取Activity名
        // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
        String className = classname;
        // LAUNCHER Intent
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // 设置ComponentName参数1:packagename参数2:MainActivity路径
        ComponentName cn = new ComponentName(activityPackageName, className);

        intent.setComponent(cn);

        /** 要传的值 */
        for (String key : map.keySet()) {
            String value = map.get(key);
            intent.putExtra(key, value);
        }

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "无法打开该应用！", Toast.LENGTH_SHORT).show();
        }

    }

    /** 根据包名检查指定应用是否已安装 参数是应用包名 */
    public static boolean hasAppInstalled(Context context, String packageName) {
        boolean res = false;
        PackageInfo packageInfo = null;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo == null) {
                System.out.println("packageInfo==null");
            } else {
                System.out.println("packageInfo!=null");
                res = true;
            }
        } catch (NameNotFoundException e) {
            // e.printStackTrace();
            // System.out.println("该应用不存在！");
        }

        return res;
    }

    private void doStartApplicationWithPackageName(Context mContext, String packagename) {
        String packagename1 = "com.palmtrends.nfrwzk";
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = mContext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = mContext.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            mContext.startActivity(intent);
        }
    }


}
