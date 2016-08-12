package com.ym.retrofitdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * @className : SystemUtil.java
 * @classDescription : 系统工具类
 * @author : AIDAN SU
 * @createTime : 2014-4-1
 *
 */
public class SystemUtil {
	
	protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected static UUID uuid;
    private static final String SCHEME = "package";
    /** 
     * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本) 
     */  
    private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
    /** 
     * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2) 
     */  
    private static final String APP_PKG_NAME_22 = "pkg";
    
    public static final String MOBILE_NAME = Build.MANUFACTURER;
	
    /**
     * 启动新Activity
     * @param classic 目标Activity类
     * @param bundle 参数
     */
    public static void launch(Context context, Class<? extends Activity> classic, Bundle bundle){
        Intent intent = new Intent();
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        intent.setClass(context, classic);
        context.startActivity(intent);
    }

	/**
	 * 获取IMEI码
	 * @param context 上下文
	 * @return imei IMEI码
	 */
	public static String getIMEI(Context context) {
		String imei = null;
		try{
			TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
			if (imei != null && imei.length() > 15) {
				imei = StringUtil.substring(imei, 0, 15);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
        return imei;
    }
	
	/**
	 * 获取UUID码
	 * @param context 上下文
	 * @return uuid UUID码
	 */
	public static String getDeviceUuid(Context context) {
        if( uuid ==null ) {  
            synchronized (SystemUtil.class) {
                    final SharedPreferences prefs = context.getSharedPreferences( PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null );
                    if (id != null) {  
                        uuid = UUID.fromString(id);
                    } else {  
                        try {  
                        	final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
                            if (!"9774d56d682e549c".equals(androidId)) {  
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {  
                                final String deviceId = ((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
                                uuid = deviceId!=null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }  
                            prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString() ).commit();  
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }  catch (Exception e) {
                        }   
                    }  
                 
            }  
        }  
        return uuid != null ? uuid.toString() : "";
    }  
	
	/**
	 * 从manifest.xml文件里面获得程序版本号
	 * @return
	 */
	public static String getVersion(Context context){
		PackageManager packageManager = context.getPackageManager();
		String version ;
		try{
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			version  = packInfo.versionName;
		}catch(Exception e){
			version = "1" ;
		}
			return version;
	}
	
	/**
	 * 根据类名获取对象实例
	 * @param className 类名
	 */
	public static Object getObject(String className){
		Object object = null;
		if(StringUtil.isNotEmpty(className)){
			try {   
				object = Class.forName(className).newInstance();
			}catch(ClassNotFoundException cnf) {
			}   
			catch(InstantiationException ie) {
			}   
			catch(IllegalAccessException ia) {
			}  
		}
		return object;
	}
	
	/**
	 * 判断存储卡是否存在
	 * @return
	 */
	public static boolean hasSdcard() {
	     String status = Environment.getExternalStorageState();
	     if (status.equals(Environment.MEDIA_MOUNTED)) {
	         return true;
	     } else {
	         return false;
	     }
	 }

	/**
     * 打开软键盘
     */
    public static void openKeyboard(final Context context) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
        	@Override
            public void run() {
        		InputMethodManager inputMethodManager=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 500);
    }

	/**
     * 关闭软键盘
     */
    public static void closeKeyboard(Context context) {
    	InputMethodManager inputMethodManager=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    
    public static void showInstalledAppDetails(Context context, String packageName) {
        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        // 2.3（ApiLevel 9）以上，使用SDK提供的接口  
        if (apiLevel >= 9) { 
            intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
            Uri uri = Uri.fromParts(SCHEME, packageName, null);
            intent.setData(uri);  
            
        // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）  
        } else { 
            // 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。  
            final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22 : APP_PKG_NAME_21);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");  
            intent.putExtra(appPkgName, packageName);  
        }  
        context.startActivity(intent);  
    } 
    
    /**
	 * 获取APP安装包信息
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context) {
		PackageInfo info = null;
		try { 
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		} 
		if(info == null) info = new PackageInfo();
		return info;
	}
	
	/**
	 * 获取系统的版本信息
	 * @return
	 */
	public static String[] getSystemInfo(){
		String[] version={"null","null","null","null"};
		String str1 = "/proc/version";
		String str2;
		String[] arrayOfString;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			version[0]=arrayOfString[2];//KernelVersion
			localBufferedReader.close();
		} catch (IOException e) {
		}
		version[1] = Build.VERSION.RELEASE;// firmware version
		version[2]= Build.MODEL;//model
		version[3]= Build.DISPLAY;//system version
		return version;
	}

	/**
	 * 去拔号页面
	 * @author YUHANG JO
	 * @createTime 15/9/18
	 * @lastModify 15/9/18
	 * @param context
	 * @param telNum
	 * @return
	 */
	public static void goToDial(Context context, String telNum) {
		Intent intent=new Intent();
		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + telNum));
		context.startActivity(intent);
	}

	/**
	 * 获取资源id
	 * @author swallow
	 * @createTime 2015/7/28
	 * @lastModify 2015/7/28
	 * @param context
	 * @param name
	 * @param type
	 * @param packageName
	 * @return
	 */
	public static int getResourceId(Context context, String name, String type, String packageName){
		Resources themeResources=null;
		PackageManager pm=context.getPackageManager();
		try {
			themeResources=pm.getResourcesForApplication(packageName);
			return themeResources.getIdentifier(name, type, packageName);
		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Apk是否处于调试模式
	 * @author swallow
	 * @createTime 2015/10/29
	 * @lastModify 2015/10/29
	 * @param
	 * @return
	 */
	public static boolean isApkDebugable(Context context) {
		try {
			ApplicationInfo info= context.getApplicationInfo();
			return (info.flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
		} catch (Exception e) {

		}
		return false;
	}
}
