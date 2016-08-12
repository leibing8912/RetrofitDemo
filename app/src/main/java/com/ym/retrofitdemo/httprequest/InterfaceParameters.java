package com.ym.retrofitdemo.httprequest;

import com.ym.retrofitdemo.BuildConfig;

/**
 * @className: InterfaceParameters
 * @classDescription: 参数配置
 * @author: leibing
 * @createTime: 2016/8/12
 */
public class InterfaceParameters {
    /**
     * 亚美
     */
    public final static String TRANS_HTTP_URL = BuildConfig.API_URL;
    /**
     * 接口返回结果名称
     */
    public final static String RESULT = "result";
    /**
     * 接口返回的消息名称
     */
    public final static String MESSAGE = "message";
    /**
     * 接口返回响应代码名称
     */
    public final static String CODE = "code";
    /**
     * 应用标识值：亚美（车智汇）=2，广信（爱车360）=3，松岩（爱车360）=4,天翼（车智汇）=5，树玉（车检大师）=6，俊通（车管家）=7
     */
    public static String CHANNEL_MARK_VALUE = "2";
    /**
     * 接口版本号选择
     */
    public static String TRANS_PARAM_V = BuildConfig.API_VERION;
}
