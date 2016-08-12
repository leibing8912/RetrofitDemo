package com.ym.retrofitdemo.module.main;

import com.ym.retrofitdemo.httprequest.HttpResponse.BaseResponse;

/**
 * @className: MainModel
 * @classDescription: 获取登录服务器返回的信息 (数据层)
 * @author: leibing
 * @createTime: 2016/8/12
 */
public class LoginModel extends BaseResponse {
    public String userId = "";
    public String lastLoginTime = "";
    public String terminalId = "";
    public String activeCode = "";
    public String status = "";
    public String mileage = "";
    public String modelId = "";
    public String modelName = "";
    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId=" + userId +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", activeCode='" + activeCode + '\'' +
                ", status='" + status + '\'' +
                ", mileage='" + mileage + '\'' +
                ", modelId='" + modelId + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
