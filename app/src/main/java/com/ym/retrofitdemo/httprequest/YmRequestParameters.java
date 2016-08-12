package com.ym.retrofitdemo.httprequest;

import android.content.Context;
import com.ym.retrofitdemo.utils.StringUtil;
import com.ym.retrofitdemo.utils.Tea;
import com.ym.retrofitdemo.utils.TransUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: YmRequestParameters
 * @classDescription: This class keep the request parameters and convert the parameters
 *                    to a json then encrypt it.
 * @author: swallow
 * @createTime: 2015/6/11
 */
public class YmRequestParameters {
    private Map<String,String> paramMap;

    /**
     * 构造一个YmRequestParameters对像。
     * @author swallow
     * @createTime 2015/6/11
     * @lastModify 2015/6/11
     * @param context
     * @param
     * @return
     */
    public YmRequestParameters(Context context, String[] paramKey, String... paramValue){
        paramMap = new HashMap<String, String>();
        for(int i=0; paramKey!=null && i<paramKey.length && i<paramValue.length; i++){
            paramMap.put(paramKey[i], paramValue[i]);
        }
    }

    @Override
    public String toString() {
        String strJson = TransUtil.listToJson(paramMap);
        try{
            if(StringUtil.isNotEmpty(strJson)){
                String paramTea = Tea.encryptByBase64Tea(strJson); // 加密json数据
                return paramTea;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "Error: "+this.getClass().getName();
    }
}
