package com.example.pipdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class CallOutActivity extends AppCompatActivity {

    private static final String TAG = "PIPDemo";

    ActionBar actionBar;

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
        setContentView(R.layout.activity_call_out);
        actionBar = getActionBar();

        Log.i(TAG,"3: ID:"+getTaskId());
        Log.i(TAG,"3：Multi:"+isInMultiWindowMode());
        Log.i(TAG,"3：PIP:"+isInPictureInPictureMode());

        //进入PIP
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

        Log.i(TAG,"4: ID:"+getTaskId());
        Log.i(TAG,"4：Multi:"+isInMultiWindowMode());
        Log.i(TAG,"4：PIP:"+isInPictureInPictureMode());
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0,0);
    }
}