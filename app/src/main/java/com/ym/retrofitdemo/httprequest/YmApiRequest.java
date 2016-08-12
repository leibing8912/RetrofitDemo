package com.ym.retrofitdemo.httprequest;

import retrofit2.Retrofit;

/**
 * @className:YmApiRequest
 * @classDescription:网络请求
 * @author: leibing
 * @createTime: 2016/8/9
 */
public class YmApiRequest {
    private static YmApiRequest instance = null;

    private Retrofit retrofit;

    private YmApiRequest(){
        retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceParameters.TRANS_HTTP_URL)
                .addConverterFactory(YmApiConvertFactory.create())
                .build();
    }

    public static YmApiRequest getInstance(){
        if (instance == null){
            instance = new YmApiRequest();
        }
        return instance;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
