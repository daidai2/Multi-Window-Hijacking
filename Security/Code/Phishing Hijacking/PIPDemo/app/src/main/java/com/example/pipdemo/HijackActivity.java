package com.example.pipdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class HijackActivity extends AppCompatActivity {

    private static final String TAG = "PIPDemo";

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        androidx.appcompat.app.ActionBar title_actionBar = getSupportActionBar();
        if (title_actionBar != null) {
            title_actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijack);

        Intent AnotherVideo = new Intent(this, AnotherVideoActivity.class);
        AnotherVideo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);                                //没有这段创造不了新的PIP
        Intent news = getIntent();
        int time = news.getIntExtra("time",0);
        AnotherVideo.putExtra("time",time);
        startActivity(AnotherVideo);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0,0);
    }
}