package com.wpy.faxianbei.sk.activity.base;

import android.os.Bundle;


/**
 * mvp架构中的基本的activity
 * 封装了绑定Viewinterface
 * 解绑Viewinterface
 * @param <V>
 * @param <T>
 */
public abstract class MvpBaseActivity<V,T extends BasePresenter<V>> extends CheckPermissionsActivity {
    protected T mBasePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 创建Presenter
         */
        mBasePresenter = createPresenter();
        /**
         * 绑定ViewInterface
         */
        mBasePresenter.attachView((V)this);
    }

    public abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePresenter.detachView();
    }
}
