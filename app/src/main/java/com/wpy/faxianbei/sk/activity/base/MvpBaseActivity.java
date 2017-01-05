package com.wpy.faxianbei.sk.activity.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


/**
 * mvp架构中的基本的activity
 * 封装了绑定Viewinterface
 * 解绑Viewinterface
 * @param <V>
 * @param <T>
 */
public abstract class MvpBaseActivity<V,T extends BasePresenter<V>> extends CheckPermissionsActivity {
    protected T mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 创建Presenter
         */
        mPresenter = createPresenter();
        /**
         * 绑定ViewInterface
         */
        mPresenter.attachView((V)this);
    }

    public abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
    public static void sendMessage(final OnSuccessOrFail onListener, final String message, final boolean isOk){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(onListener!=null)
                {
                    if(isOk){
                        onListener.onSuccess(message);
                    }else{
                        onListener.onFail(message);
                    }
                }
            }
        });
    }
}
