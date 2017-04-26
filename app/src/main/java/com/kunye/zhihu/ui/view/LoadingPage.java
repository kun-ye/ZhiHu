package com.kunye.zhihu.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.kunye.zhihu.R;
import com.kunye.zhihu.utils.UIUtils;

/**
 * Created by kunye on 2017/1/25.
 */

public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_LOAD_UNDO = 1;// 未加载
    private static final int STATE_LOAD_LOADING = 2;// 正在加载
    private static final int STATE_LOAD_ERROR = 3;// 加载失败
    private static final int STATE_LOAD_EMPTY = 4;// 数据为空
    private static final int STATE_LOAD_SUCCESS = 5;// 加载成功

    private int mCurrentState = STATE_LOAD_UNDO;// 当前状态

    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 初始化加载中的布局
        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);// 将加载中的布局添加给当前的帧布局
        }
        // 初始化加载失败布局
        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);
            //点击重试事件
            Button retry = (Button) mErrorPage.findViewById(R.id.retry);
            retry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
            addView(mErrorPage);
        }

        // 初始化数据为空布局
        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }
        showRightPage();
    }


    // 根据当前状态,决定显示哪个布局
    private void showRightPage() {

        mLoadingPage.setVisibility((mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING) ? View.VISIBLE : View.GONE);

        mErrorPage.setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);

        mEmptyPage.setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);

        // 当成功布局为空,并且当前状态为成功,才初始化成功的布局
        if (mSuccessPage == null && mCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }

        if (mSuccessPage != null) {
            mSuccessPage.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    // 开始加载数据
    //开始加载数据
    public void loadData(){
        // 状态归零
        if (mCurrentState == STATE_LOAD_EMPTY
                || mCurrentState == STATE_LOAD_ERROR
                || mCurrentState == STATE_LOAD_SUCCESS) {
            mCurrentState = STATE_LOAD_UNDO;
        }
        if (mCurrentState == STATE_LOAD_UNDO){//如果当前没有加载，就开始加载数据
            new Thread(){
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null){
                                mCurrentState = resultState.getState();//加载结束后要更新网络状态
                                //根据最新网络状态刷新界面
                                showRightPage();
                            }
                        }
                    });
                }
            }.start();
        }
    }

    // 加载成功后显示的布局, 必须由调用者来实现
    public abstract View onCreateSuccessView();

    // 加载网络数据, 返回值表示请求网络结束后的状态
    public abstract ResultState onLoad();

    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS), STATE_EMPTY(STATE_LOAD_EMPTY), STATE_ERROR(
                STATE_LOAD_ERROR);

        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

}

