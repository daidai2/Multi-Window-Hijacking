package com.example.adobfuscation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AdObfuscation";
    private Handler mHandler = null;                       //监听后的处理
    private static final int POSTDELAYED_COUNTS = 5000;    //监听时间间隔

    //悬浮窗相关的变量
    private WindowManager wm;
    private WindowManager.LayoutParams layoutParams;
    private View displayView;

    Activity thi = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestOverlayPermission();//申请悬浮窗权限
        if(canUsageStats(getApplicationContext())) {
            mHandler = new Handler();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //分析进程
                    String fore = getForegroundPackageName(getApplicationContext());
                    if("com.sina.weibo".equals(fore)){   //监听微博打开
                        showFloatingView();
                    }
                    mHandler.postDelayed(this, POSTDELAYED_COUNTS);
                }
            });

        }else{
            //申请权限
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),809);
        }


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

    //申请SYSTEM_ALRET_WINDOW权限
    public void requestOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        }
    }

    //制作悬浮窗广告
    private void showFloatingView() {
        if (Settings.canDrawOverlays(this)) {
            // 获取WindowManager服务
            wm = (WindowManager) getSystemService(WINDOW_SERVICE);

            // 设置LayoutParam
            layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.width = 800;
            layoutParams.height = 1300;
            layoutParams.x = 10;
            layoutParams.y = 5;

            //加入注释面板
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            displayView = layoutInflater.inflate(R.layout.advertisement_view, null);

            //给注释面板加上按钮触发器
            displayView.setOnTouchListener(new FloatingOnTouchListener());
            // 将注释面板添加到WindowManager
            wm.addView(displayView, layoutParams);

        }
    }

    //监听前台运行的软件
    public static String getForegroundPackageName(Context context) {
        //Get the app record in the last month
        Calendar calendar = Calendar.getInstance();
        final long end = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        final long start = calendar.getTimeInMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = usageStatsManager.queryEvents(start, end);
        UsageEvents.Event event = new UsageEvents.Event();
        String packageName = null;
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            if (event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
                packageName = event.getPackageName();
            }
        }
        return packageName;
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();
                    if(x >= 800 && x <= 900 && y >= 680 && y <= 780){
                        //关闭视图
                        wm.removeView(displayView);
                    }else if(x >= 200 && x <= 920 && y >= 500 && y <= 1900){
//                        finish();                         //主栈开启的关键
                        wm.removeView(displayView);         //关闭视图
                        //打开浏览器，跳转至网址
                        String xunLei_Website = "https://pan.xunlei.com/";    //迅雷云盘网址
                        jumpUriToBrowser(thi, xunLei_Website);
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    //通过URL跳到浏览器
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

}