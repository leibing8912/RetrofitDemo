package com.ym.retrofitdemo.module.main;

import com.ym.retrofitdemo.httprequest.InterfaceParameters;
import com.ym.retrofitdemo.httprequest.YmApiRequest;
import com.ym.retrofitdemo.httprequest.YmRequestParameters;
import com.ym.retrofitdemo.httprequest.api.ApiLogin;
import com.ym.retrofitdemo.module.AppManager;
import com.ym.retrofitdemo.utils.SystemUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @className:MainPresent
 * @classDescription:登陆（逻辑层）
 * @author: leibing
 * @createTime: 2016/8/9
 */
public class LoginPresenter {
    private PresentListener mPresentListener;
    private ApiLogin mApi;

    public LoginPresenter(PresentListener mPresentListener){
        this.mPresentListener = mPresentListener;
    }

    /**
     * 请求登陆信息
     * @author leibing
     * @createTime 2016/8/9
     * @lastModify 2016/8/9
     * @param
     * @return
     */
    public void requestLogin(){
        mApi = YmApiRequest.getInstance().create(ApiLogin.class);
        final String mobileNo = "18818917198";
        String password = "123456";
        String channelMark = InterfaceParameters.CHANNEL_MARK_VALUE;
        String phoneName = SystemUtil.MOBILE_NAME + "(" + android.os.Build.MODEL + ")";
        String params = new YmRequestParameters(AppManager.getInstance().currentActivity(),
                ApiLogin.LOGIN_PARAM,
                mobileNo,password,channelMark,phoneName,"").toString();
        Call<LoginModel> mCall = mApi.login(params, InterfaceParameters.TRANS_PARAM_V);
        mCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (mPresentListener != null)
                    mPresentListener.updateUI(response.body());
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                if (mPresentListener != null)
                    mPresentListener.onFailureMessage();
            }
        });
    }

    public interface PresentListener{
        void updateUI(LoginModel mLoginModel);
        void onFailureMessage();
    }
}
