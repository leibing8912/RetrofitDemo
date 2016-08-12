package com.ym.retrofitdemo.httprequest;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.ym.retrofitdemo.httprequest.HttpResponse.BaseResponse;
import com.ym.retrofitdemo.utils.SystemUtil;
import com.ym.retrofitdemo.utils.Tea;
import com.ym.retrofitdemo.utils.TransData;
import com.ym.retrofitdemo.utils.TransUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @className: YmApiConvert
 * @classDescription: this converter decode the response.
 * @author: leibing
 * @createTime: 2016/8/9
 */
public class YmApiConvertFactory extends Converter.Factory{

    public static YmApiConvertFactory create() {
        return create(new Gson());
    }

    private Gson gson;

    public static YmApiConvertFactory create(Gson gson) {
        return new YmApiConvertFactory(gson);
    }

    private YmApiConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new GsonResponseBodyConverter<>(gson, type);
    }

    final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final Type type;

        GsonResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        @Override public T convert(ResponseBody value) throws IOException {
            BaseResponse baseResponse;
            String reString;
            try {
                // TEAg解密返回的数据
                reString = Tea.decryptByBase64Tea(value.string());
                // 解析Json数据返回TransData对象
                TransData transData = TransUtil.getResponse(reString);
                try {
                    if (transData.getCode().equals("400") ||
                            TextUtils.isEmpty(transData.getResult())) {
                        baseResponse = (BaseResponse) SystemUtil.getObject(((Class) type).getName());
                        baseResponse.setResponseCode(("200".equals(transData.getCode())));
                        baseResponse.setResponseMessage(transData.getMessage());
                    } else {
                        baseResponse = new Gson().fromJson(transData.getResult(), type);
                        baseResponse.setResponseCode(("200".equals(transData.getCode())));
                        baseResponse.setResponseMessage(transData.getMessage());
                        baseResponse.setResponseResult(transData.getResult());
                    }
                    return (T)baseResponse;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
            }
            //从不返回一个空的Response.
            baseResponse = (BaseResponse) SystemUtil.getObject(((Class) type).getName());
            try {
                baseResponse.setResponseCode(false);
                //YmApiConverter can not recognize the response!
                baseResponse.setResponseMessage("");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return (T)baseResponse;
            }
        }
    }
}
