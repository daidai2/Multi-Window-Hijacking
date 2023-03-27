package com.example.pipdemo;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.AppOpsManager;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PIPDemo";

    private Button enter;
    private Button call_out;
    private TextView text;
    private Button service;
    ActionBar actionBar;

    //监听用
    private Handler mHandler = null;
    private static final int ANDROID_VER = 1;
    private static final int POSTDELAYED_COUNTS = 3000;
    private int time = 0;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //关闭进入动画
        overridePendingTransition(0,0);
        //隐藏标题栏
        androidx.appcompat.app.ActionBar title_actionBar = getSupportActionBar();
        if (title_actionBar != null) {
            title_actionBar.hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getActionBar();


        //收到数据
        Intent news = getIntent();
        boolean flag = news.getBooleanExtra("flag",false);
        if(flag){
            Display d = getWindowManager()
                    .getDefaultDisplay();
            Point p = new Point();
            d.getSize(p);
            int width = p.x;
            int height = p.y;
            Rational ratio = new Rational(width, height);
            PictureInPictureParams.Builder pip_Builder = new PictureInPictureParams.Builder();
            pip_Builder.setAspectRatio(ratio).build();
            enterPictureInPictureMode(pip_Builder.build());
            Log.i(TAG,"2: ID"+getTaskId());
            Log.i(TAG,"2：Multi"+isInMultiWindowMode());
            Log.i(TAG,"2：PIP:"+isInPictureInPictureMode());
        }else{
            Log.i(TAG,"1: ID:"+getTaskId());
            Log.i(TAG,"1：Multi:"+isInMultiWindowMode());
            Log.i(TAG,"1：PIP:"+isInPictureInPictureMode());
        }


        enter = findViewById(R.id.enter_button);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(canUsageStats(getApplicationContext())) {
                    //开启PIP
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("flag", true);
                    startActivity(intent);

                    //开启监听Service
                    startService(new Intent(MainActivity.this, MonitorService.class));

//                    finish();               //用于关闭原本的大Activity（在新版本存在问题）
                }else{
                    //申请权限
                    startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),809);
                }
            }
        });

    }

    //重写页面
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            // Hide the controls in picture-in-picture mode.
            enter.setEnabled(false);
            enter.setVisibility(View.GONE);
        } else {
            // Restore the playback UI based on the playback status.
            enter.setEnabled(true);
            enter.setVisibility(View.VISIBLE);
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
        if (mode == AppOpsManager.MODE_DEFAULT && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode == AppOpsManager.MODE_ALLOWED);
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0,0);
    }
}