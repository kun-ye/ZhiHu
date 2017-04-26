package com.kunye.zhihu.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunye.zhihu.ui.view.LoadingPage;
import com.kunye.zhihu.utils.UIUtils;

import java.util.ArrayList;


/**
 * Created by kunye on 2017/1/22.
 */

public abstract class BaseFragment extends Fragment {
    private LoadingPage mLoadingPage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        loadData();//开始加载数据
        return mLoadingPage;
    }

    public abstract View onCreateSuccessView();

    public abstract LoadingPage.ResultState onLoad();

    public void loadData(){
        //开始加载数据
        if (mLoadingPage != null){
            mLoadingPage.loadData();
        }
    }

    public LoadingPage.ResultState check(Object obj){
        if (obj != null){
            if (obj instanceof ArrayList){//判断是否是集合
                ArrayList list = (ArrayList) obj;
                if (list.isEmpty()){
                    return LoadingPage.ResultState.STATE_EMPTY;
                }else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
