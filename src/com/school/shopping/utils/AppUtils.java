package com.school.shopping.utils;

import java.io.File;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;

import com.school.shopping.MyApplication;

/**
 * 跟App相关的辅助类
 */
public class AppUtils {

	private AppUtils() {
		/** cannot be instantiated **/
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	public static Context getContext(){
		return UIUtils.getContext();
	}
	
	public static int getBestCacheSize(){
		ActivityManager am=(ActivityManager) UIUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		int availMemInBytes=am.getMemoryClass()*1024*1024;
		return availMemInBytes/8;
	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName() {
		try {
			PackageManager packageManager = getContext().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return getContext().getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName() {
		try {
			PackageManager packageManager = getContext().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 //检测是否有应用商店
    public static boolean isIntentAvailable(Intent intent) {
        final PackageManager packageManager = getContext().getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

    public static void commentApp(){
    	Uri uri = Uri.parse("market://details?id="+getContext().getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if(isIntentAvailable(intent))
		{
			UIUtils.startActivity(intent);
		}
		else
		{
			UIUtils.showMsg("您的手机上还没有安装任何应用商店！");
		}
    }
    
    
    public static void removeCache(){
    	removeImageLoaderCache();
    	removeMyDiskCache();
    	cleanInternalCache();
    }
    
    
    public static void removeImageLoaderCache(){
    	MyApplication.getImageLoader().clearDiskCache();
    	MyApplication.getImageLoader().clearMemoryCache();
    }
    
    public static void removeMyDiskCache(){
    	if(FileUtils.isSDCardAvailable()){
    		File cacheDir=new File(FileUtils.getCacheDir());
    		if(cacheDir.exists()){
    			FileUtils.deleteFile(cacheDir);
    		}
    	}
    }
    
    /** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)  */
    public static void cleanInternalCache() {
    	FileUtils.deleteFile((UIUtils.getContext().getCacheDir()));
    }
    
    /** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context */
    public static void cleanDatabases() {
    	FileUtils.deleteFile(new File("/data/data/"
                + UIUtils.getContext().getPackageName() + "/databases"));
    }
    
    /** * 按名字清除本应用数据库 * * @param context * @param dbName */
    public static void cleanDatabaseByName(String dbName) {
    	UIUtils.getContext().deleteDatabase(dbName);
    }
    
    /** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
    public static void cleanFiles() {
    	FileUtils.deleteFile(UIUtils.getContext().getFilesDir());
    }
    
    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public static void cleanExternalCache() {
        if (FileUtils.isSDCardAvailable()) {
        	FileUtils.deleteFile(UIUtils.getContext().getExternalCacheDir());
        }
    }
    
    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public static void cleanSharedPreference() {
    	FileUtils.deleteFile(new File("/data/data/"
                + UIUtils.getContext().getPackageName() + "/shared_prefs"));
    }
    
    /** * 清除本应用所有的数据 * * @param context * @param filepath */
    public static void cleanApplicationData(Context context) {
        cleanInternalCache();
        cleanExternalCache();
        cleanDatabases();
        cleanSharedPreference();
        cleanFiles();
    }
    
   
}