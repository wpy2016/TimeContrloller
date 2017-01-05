package com.wpy.faxianbei.sk.activity.base;

import android.os.AsyncTask;

import com.wpy.faxianbei.sk.utils.lock.IErrorUtil;

/**
 * Created by peiyuwang on 17-1-5.
 */

public abstract class BaseAsycTask<P,V,R,T extends OnSuccessOrFail> extends AsyncTask<P,V,R>{

    protected T viewInterface=null;

    protected IErrorUtil errorUtil;

    public BaseAsycTask(T viewInterface, IErrorUtil errorUtil) {
        this.viewInterface = viewInterface;
        this.errorUtil = errorUtil;
    }

    public BaseAsycTask(IErrorUtil errorUtil) {
        this(null,errorUtil);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(viewInterface!=null)
        {
            viewInterface.showProgress();
        }
    }

    @Override
    protected void onPostExecute(R r) {
        super.onPostExecute(r);
        if(viewInterface!=null)
        {
            viewInterface.dimissProgress();
        }
    }
}
