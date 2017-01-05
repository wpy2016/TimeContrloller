package com.wpy.faxianbei.sk.utils.lock;

/**
 * Created by peiyuwang on 17-1-5.
 */

public interface IErrorUtil {
    public String getErrorString(int errorCode);
    public String getErrorString(String errorString);
    public String getErrorString(int errorCode,String errorString);
}
