package com.ym.retrofitdemo.module.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.ym.retrofitdemo.R;
import com.ym.retrofitdemo.module.AppManager;
import com.ym.retrofitdemo.module.BaseActivity;

/**
 * @className: LoginActivity
 * @classDescription: 登陆（UI层）
 * @author: leibing
 * @createTime: 2016/8/12
 */

public class LoginActivity extends BaseActivity implements LoginPresenter.PresentListener{
    private LoginPresenter mMainPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresent = new LoginPresenter(this);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPresent.requestLogin();
            }
        });
    }

    @Override
    public void updateUI(LoginModel mLoginModel) {
        try {
            System.out.println("dddddddddddddd mLoginResponse = " + mLoginModel.toString());
            System.out.println("dddddddddddddd lastLoginTime = " + mLoginModel.lastLoginTime);
        }catch (Exception ex){
        }
    }

    @Override
    public void onFailureMessage() {
        Toast.makeText(AppManager.getInstance().currentActivity(),
                "网络不给力",Toast.LENGTH_LONG);
    }
}
