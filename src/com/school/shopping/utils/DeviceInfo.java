package com.school.shopping.utils;

import android.os.Build;

public class DeviceInfo {
	
	public static String getUniqueID(){
		String m_szDevIDShort = "35" + //we make this look like a valid IMEI 

		Build.BOARD.length()%10 + // 主板  
		Build.BRAND.length()%10 + // android系统定制商  
		Build.CPU_ABI.length()%10 + // cpu指令集  
		Build.DEVICE.length()%10 + // 设备参数  
		Build.DISPLAY.length()%10 + // 显示屏参数  
		Build.HOST.length()%10 +  
		Build.ID.length()%10 + // 修订版本列表 
		Build.MANUFACTURER.length()%10 + // 硬件制造商  
		Build.MODEL.length()%10 +  // 版本  
		Build.PRODUCT.length()%10 + // 手机制造商  
		Build.TAGS.length()%10 + // 描述build的标签  
		Build.TYPE.length()%10 + // builder类型  
		Build.USER.length()%10 ; //13 digits
		
		return m_szDevIDShort;
		
	}

}
