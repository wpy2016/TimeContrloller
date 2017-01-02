package com.wpy.faxianbei.sk.activity.base;

import java.lang.ref.WeakReference;

/**
 * Created by peiyuwang on 17-1-2.
 * mvp架构中基本的presenter。
 * 对于ViewInterface接口采用了弱引用的方式，避免了actiivty Leak 内存泄漏
 * 即当activity中发起了网络请求加载数据，但是activity却因为跳转变为不可见，jvm由于内存不足回收了该activity
 * 而此时网络加载数据刚完成，想去调用activity显示，此时activity已经回收，所以会发生内存泄露
 */
public abstract class BasePresenter<V> {
    protected WeakReference<V> mViewInterfaceReference;
    public void attachView(V viewInterface){
        mViewInterfaceReference = new WeakReference<V>(viewInterface);
    }

    public void detachView(){
        if(mViewInterfaceReference!=null)
        {
            mViewInterfaceReference.clear();
            mViewInterfaceReference=null;
        }
    }

    public V getViewInterface(){
        return mViewInterfaceReference.get();
    }
}
