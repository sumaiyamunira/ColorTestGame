package com.samsum.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utilities {
	
	public static int getDeviceWidth(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels;
		//int screenHeight = displayMetrics.heightPixels;
		return screenWidth;
		
	}
	

}
