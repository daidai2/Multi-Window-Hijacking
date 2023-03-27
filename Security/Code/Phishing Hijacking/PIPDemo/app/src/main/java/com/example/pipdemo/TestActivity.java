package com.example.pipdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.Display;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "PIPDemo";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.i(TAG,"5: ID:"+getTaskId());
        Log.i(TAG,"5：Multi:"+isInMultiWindowMode());
        Log.i(TAG,"5：PIP:"+isInPictureInPictureMode());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume(){
        super.onResume();
        //收到数据
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

        Log.i(TAG,"6: ID:"+getTaskId());
        Log.i(TAG,"6：Multi:"+isInMultiWindowMode());
        Log.i(TAG,"6：PIP:"+isInPictureInPictureMode());
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(0,0);
    }

}