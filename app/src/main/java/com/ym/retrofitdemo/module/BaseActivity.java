package com.ym.retrofitdemo.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.ym.retrofitdemo.module.AppManager;

/**
 * @className : BaseActivity.java
 * @classDescription : Activity基础类
 * @author : AIDAN SU
 * @createTime : 2014-4-1
 *
 */
public class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }

    /**
     * 启动新Activity
     * @param classic 目标Activity类
     * @param bundle 参数
     */
    public void launch(Class<? extends Activity> classic,Bundle bundle){
        Intent intent = new Intent();
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        intent.setClass(this, classic);
        startActivity(intent);
    }

    public void lanuch(Context context,Class<? extends Activity> classic){
        Intent intent = new Intent();
        intent.setClass(context, classic);
        startActivity(intent);
    }

    /**
     * 启动新Activity
     * @param classic 目标Activity类
     * @param Request 参数
     */
    public void launch(Class<? extends Activity> classic,int Request){
        Intent intent = new Intent();
        intent.setClass(this, classic);
        startActivityForResult(intent, Request);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getInstance().finishActivity(this);
    }

    @Override
    public void onBackPressed() {
        AppManager.getInstance().finishActivity(this);
    }
}
