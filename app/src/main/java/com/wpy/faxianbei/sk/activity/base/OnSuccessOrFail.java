package com.wpy.faxianbei.sk.activity.base;

/**
 * Created by peiyuwang on 17-1-3.
 */

public interface OnSuccessOrFail {
    public void showProgress();
    public void dimissProgress();
    public void onSuccess(String message);
    public void onFail(String message);
}
