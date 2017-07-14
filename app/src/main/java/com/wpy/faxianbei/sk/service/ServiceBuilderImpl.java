package com.wpy.faxianbei.sk.service;

import android.os.RemoteException;

import com.wpy.faxianbei.sk.IServiceBuilder;

/**
 * Created by wangpeiyu on 2017/7/13.
 */

public class ServiceBuilderImpl extends IServiceBuilder.Stub {


    private String str;

    public ServiceBuilderImpl(String string) {
        str = string;
    }

    @Override
    public String basicTypes() throws RemoteException {
        return str;
    }
}
