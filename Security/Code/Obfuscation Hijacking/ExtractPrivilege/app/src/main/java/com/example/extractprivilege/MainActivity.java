package com.example.extractprivilege;

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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private VideoView video;          //视频播放器
    private Button test;
    private Handler mHandler = null;                       //监听后的处理
    private static final int POSTDELAYED_COUNTS = 5000;    //监听时间间隔

    //悬浮窗相关的变量
    WindowManager wm;
    WindowManager.LayoutParams layoutParams;
    View displayView;

    Activity thi = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestOverlayPermission();//申请悬浮窗权限

        video = findViewById(R.id.video);
        test = findViewById(R.id.test_button);

        String path = "android.resource://"+getPackageName() +"/"+R.raw.live;//获取视频路径
        Uri uri = Uri.parse(path);//将路径转换成uri
        video.setVideoURI(uri);//为视频播放器设置视频路径
        video.setMediaController(new MediaController(MainActivity.this));//显示控制栏
        video.setFocusable(true); //让VideoView获得焦点
        video.start(); //开始播放视频

        if(canUsageStats(getApplicationContext())) {
            mHandler = new Handler();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //分析进程
                    String fore = getForegroundPackageName(getApplicationContext());
                    if("com.android.camera".equals(fore)){   //监听相机
                        showFloatingView();
                    }
                    mHandler.postDelayed(this, POSTDELAYED_COUNTS);
                }
            });
        }else{
            //申请权限
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),809);
        }

        //测试按钮
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFloatingView();
            }
        });
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
            layoutParams.height = 580;
            layoutParams.x = 10;
            layoutParams.y = 10;

            //加入注释面板
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            displayView = layoutInflater.inflate(R.layout.permissions_description_panel, null);

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
                    if(x >= 200 && x <= 500 && y >= 1400 && y <= 1550 ){
                        //点击取消按钮
                        wm.removeView(displayView);
                    }
                    if(x >= 600 && x <= 900 && y >= 1400 && y <= 1550 ){
                        //点击确认按钮
                        wm.removeView(displayView);
                        ActivityCompat.requestPermissions(thi,
                                new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}

