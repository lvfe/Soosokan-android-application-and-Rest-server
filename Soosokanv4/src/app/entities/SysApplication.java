package app.entities;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Application;

public class SysApplication extends Application {
	private static Map<String,Activity> mList = new HashMap<String,Activity>();

	// add Activity
	public static void addActivity(Activity activity,String TAG) {
		mList.put(TAG, activity);
	}
	
	public static void close(String TAG){
		Activity a = mList.remove(TAG);
		a.finish();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

}
