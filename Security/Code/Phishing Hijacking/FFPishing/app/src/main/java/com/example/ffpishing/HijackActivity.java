package com.example.ffpishing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class HijackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijack);

        //隐藏标题栏
        androidx.appcompat.app.ActionBar title_actionBar = getSupportActionBar();
        if (title_actionBar != null) {
            title_actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏

        showToast(this);
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