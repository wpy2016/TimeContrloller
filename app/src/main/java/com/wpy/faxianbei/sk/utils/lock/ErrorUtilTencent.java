package com.wpy.faxianbei.sk.utils.lock;

/**
 * Created by peiyuwang on 17-1-5.
 */

public class ErrorUtilTencent implements IErrorUtil {

    /**
     * 通过错误码返回指定的错误
     *
     * @param errorCode 错误码
     * @return 返回错误信息
     */
    @Override
    public String getErrorString(int errorCode) {
        switch (errorCode) {
            case 0:
                return "网络在开小差哦";
            case 210:
                return "账号或者密码错误";
            case 202:
                return "学号已经注册咯";
            case 203:
                return "邮箱已经注册咯";
            default:
                return "其他错误";
        }
    }

    /**
     * 根据相应的错误字符串返回指定的错误，返回人性化的错误
     * @param errorString 错误的字符串
     * @return 返回人性化的错误提示信息
     */
    @Override
    public String getErrorString(String errorString) {
        if (errorString.contains("Network is unreachable")) {
            return "网络在开小差哦";
        } else if (errorString.equals("Invalid index 4, size is 3")) {
            return "学号或者密码错误";
        } else {
            return "其他错误";
        }
    }

    /**
     * 当无法判断是哪一种错误的时候，可以将错误码或者错误的相应字符串传入，函数有优先返回是错误码的错误提示
     * 如果没有找到错误码的提示，则返回错误字符串的错误提示
     * @param errorCode
     * @param errorString
     * @return
     */
    public String getErrorString(int errorCode,String errorString){
        return getErrorString(errorCode).equals("其他错误")?getErrorString(errorString):getErrorString(errorCode);
    }
}
