package com.example.exitswitchffstack;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = null;                       //监听后的处理
    private static final int POSTDELAYED_COUNTS = 5000;    //监听时间间隔

    private static final String[] targetActivityArrays = MonitorList.targetActivityArrays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏

        //隐藏标题栏
        androidx.appcompat.app.ActionBar title_actionBar = getSupportActionBar();
        if (title_actionBar != null) {
            title_actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏

        if(canUsageStats(getApplicationContext())) {
            mHandler = new Handler();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //分析进程
                    boolean getTarget = false;
                    String fore = getForegroundClassName(getApplicationContext());
                    for(String targetActivityArray:targetActivityArrays){
                        if(targetActivityArray.equals(fore)){
                            switchWeb();
                            getTarget = true;
                        }
                    }
                    if(getTarget){
                        mHandler.removeCallbacks(this);
                    }else {
                        mHandler.postDelayed(this, POSTDELAYED_COUNTS);
                    }
                }
            });
        }else{
            //申请权限
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),809);
        }
    }

    public void switchWeb(){
        showToast(this);
        finish();                         //主栈开启的关键
        //打开浏览器，跳转至网址
        String xunLei_Website = "https://developer.android.google.cn/";    //Android Developer网址
        jumpUriToBrowser(getApplicationContext(), xunLei_Website);
        Intent intent = new Intent(MainActivity.this, HijackActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

//    //监听前台运行的软件
//    public static String getForegroundPackageName(Context context) {
//        //Get the app record in the last month
//        Calendar calendar = Calendar.getInstance();
//        final long end = calendar.getTimeInMillis();
//        calendar.add(Calendar.MONTH, -1);
//        final long start = calendar.getTimeInMillis();
//        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//        UsageEvents usageEvents = usageStatsManager.queryEvents(start, end);
//        UsageEvents.Event event = new UsageEvents.Event();
//        String packageName = null;
//        while (usageEvents.hasNextEvent()) {
//            usageEvents.getNextEvent(event);
//            if (event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
//                packageName = event.getPackageName();
//            }
//        }
//        return packageName;
//    }

    public static void jumpUriToBrowser(Context context, String url) {
        Intent intent = new Intent();
        // 设置意图动作为打开浏览器
        intent.setAction(Intent.ACTION_VIEW);
        // 声明一个Uri
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //得到前台Activity名
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

    public static void showToast(Context act){
        try {

            Toast toast = new Toast(act);
            View view = LayoutInflater.from(act).inflate(R.layout.view, null);

            //根据自己需要对view设置text或其他样式
            toast.setView(view);
            toast.setGravity(Gravity.CENTER, 0, 800);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}