package com.kunye.zhihu.ui.holder;

import android.view.View;

/**
 * Created by kunye on 2017/2/16.
 */

public abstract class BaseHolder<T> {
    private View mRootView;
    private T data;

    public BaseHolder() {
        this.mRootView = initView();
        //3.打标记
        mRootView.setTag(this);
    }

    //1,加载布局文件
    //2.初始化控件
    public abstract View initView();

    //返回item的布局对象
    public View getRootView(){
        return mRootView;
    }

    //设置当前item的数据
    public void setData(T data){
        this.data = data;
        refreshView(data);
    }

    //获取当前item的数据
    public T getData(){
        return data;
    }

    //4.根据数据刷新页面
    public abstract void refreshView(T data);

}
