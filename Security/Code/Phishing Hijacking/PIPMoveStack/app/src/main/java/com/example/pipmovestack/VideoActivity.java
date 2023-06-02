package com.example.pipmovestack;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = "PIPMoveStack";
    private VideoView video;          //视频播放器
    private Button enter;             //PIP按钮

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //关闭进入动画
        overridePendingTransition(0,0);
        //设置
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = findViewById(R.id.video);                 //视频
        enter = findViewById(R.id.enter_button);          //进入PIP的按钮
        setVideoPath();                                   //设置视频源
        //隐藏标题栏
        androidx.appcompat.app.ActionBar title_actionBar = getSupportActionBar();
        if (title_actionBar != null) {
            title_actionBar.hide();
        }

        //进入PIP模式按钮
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(canUsageStats(getApplicationContext())) {
                    //开启监听Service
                    startService(new Intent(VideoActivity.this, MonitorService.class));
                    //开启PIP
                    Intent intent = new Intent(VideoActivity.this, VideoActivity.class);
                    intent.putExtra("flag", true);
                    intent.putExtra("time",video.getCurrentPosition());
                    startActivity(intent);
                }else{
                    //申请权限
                    startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),809);
                }
            }
        });

        //收到数据进入PIP模式
        Intent news = getIntent();
        boolean flag = news.getBooleanExtra("flag",false);
        if(flag){
            Display d = getWindowManager()
                    .getDefaultDisplay();
            Point p = new Point();
            d.getSize(p);
            int width = 2 * p.x;
            int height = width / 9 * 5;
            Rational ratio = new Rational(width, height);
            PictureInPictureParams.Builder pip_Builder = new PictureInPictureParams.Builder();
            pip_Builder.setAspectRatio(ratio).build();
            enterPictureInPictureMode(pip_Builder.build());
            int time = news.getIntExtra("time",0);
            video.seekTo(time+2500);
        }
        boolean hijack = news.getBooleanExtra("hijack",false);
        if(hijack){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            setContentView(R.layout.activity_hijack);
        }
    }

    //重写页面
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            // Hide the controls in picture-in-picture mode.
            findViewById(R.id.actor_name).setVisibility(View.GONE);
            findViewById(R.id.actor_pic).setVisibility(View.GONE);
            enter.setEnabled(false);
            enter.setVisibility(View.GONE);
        } else {
            // Restore the playback UI based on the playback status.
            findViewById(R.id.actor_name).setVisibility(View.VISIBLE);
            findViewById(R.id.actor_pic).setVisibility(View.VISIBLE);
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
        if (mode == AppOpsManager.MODE_DEFAULT) {
            return (context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode == AppOpsManager.MODE_ALLOWED);
        }
    }

    //设置视频路径
    public void setVideoPath(){
        String path = "android.resource://"+getPackageName() +"/"+R.raw.live;//获取视频路径
        Uri uri = Uri.parse(path);//将路径转换成uri
        video.setVideoURI(uri);//为视频播放器设置视频路径
        video.setMediaController(new MediaController(VideoActivity.this));//显示控制栏
        video.setFocusable(true); //让VideoView获得焦点
        video.start(); //开始播放视频
    }

    @Override
    public void onPause(){
        super.onPause();
        video.stopPlayback();
    }
    @Override
    public void onStop(){
        super.onStop();
        video.stopPlayback();
    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0,0);
        video.stopPlayback();
    }
}