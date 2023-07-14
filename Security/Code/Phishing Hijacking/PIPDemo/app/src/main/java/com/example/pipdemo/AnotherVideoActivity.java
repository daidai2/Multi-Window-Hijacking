package com.example.pipdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.widget.MediaController;
import android.widget.VideoView;

public class AnotherVideoActivity extends AppCompatActivity {

    private static final String TAG = "PIPDemo";

    private VideoView video;          //视频播放器

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //隐藏标题栏
        androidx.appcompat.app.ActionBar title_actionBar = getSupportActionBar();
        if (title_actionBar != null) {
            title_actionBar.hide();
        }
        video = findViewById(R.id.video);                 //视频
        setVideoPath();                                   //设置视频路径
        Intent news = getIntent();
        int time = news.getIntExtra("time",0);
        video.seekTo(time+2500);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume(){
        super.onResume();
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
    }

    //设置视频路径
    public void setVideoPath(){
        String path = "android.resource://"+getPackageName() +"/"+R.raw.live;//获取视频路径
        Uri uri = Uri.parse(path);//将路径转换成uri
        video.setVideoURI(uri);//为视频播放器设置视频路径
        video.setMediaController(new MediaController(AnotherVideoActivity.this));//显示控制栏
        video.setFocusable(true); //让VideoView获得焦点
        video.start(); //开始播放视频
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0,0);
    }

}