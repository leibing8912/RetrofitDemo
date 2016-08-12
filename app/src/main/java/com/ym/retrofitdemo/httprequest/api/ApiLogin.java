package com.ym.retrofitdemo.httprequest.api;

import com.ym.retrofitdemo.module.main.LoginModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @className: ApiLogin
 * @classDescription: 登陆api
 * @author: leibing
 * @createTime: 2016/8/12
 */
public interface ApiLogin {
    String[] LOGIN_PARAM = {
            "username",
            "password",
            "channelMark",
            "phoneName",
            "source"
    };

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> login(
            @Field("parameters") String parameters,
            @Field("v") String version);
}
