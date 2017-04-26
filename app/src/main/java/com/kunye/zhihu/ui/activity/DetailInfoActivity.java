package com.kunye.zhihu.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunye.zhihu.R;
import com.kunye.zhihu.bean.DetailInfoBean;
import com.kunye.zhihu.global.GlobalConstants;
import com.kunye.zhihu.http.protocol.DetailProtocol;
import com.kunye.zhihu.ui.holder.DetailInfoHolder;
import com.kunye.zhihu.ui.view.LoadingPage;
import com.kunye.zhihu.utils.BitmapHelper;
import com.kunye.zhihu.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 页面详情页
 * Created by kunye on 2017/2/21.
 */

public class DetailInfoActivity extends AppCompatActivity {
    private LoadingPage mLoadingPage;

    private DetailInfoBean data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_info);
        mLoadingPage = new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return DetailInfoActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return DetailInfoActivity.this.onLoad();
            }
        };
        setContentView(mLoadingPage);
        //开始加载数据
        mLoadingPage.loadData();
        //初始化ActionBar
        initActionBar();
    }

    public View onCreateSuccessView(){
        View view = UIUtils.inflate(R.layout.activity_detail);
        FrameLayout fl_detail = (FrameLayout) view.findViewById(R.id.fl_detail);
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        fl_detail.addView(detailInfoHolder.getRootView());
        detailInfoHolder.setData(data);
        return view;
    }

    public LoadingPage.ResultState onLoad(){
        //请求网络加载数据
        DetailProtocol protocol = new DetailProtocol();
        String id = getIntent().getStringExtra("id");
        protocol.setId(id);
        data = protocol.getData(GlobalConstants.NEWS_URL);
        if (data != null){
            return LoadingPage.ResultState.STATE_SUCCESS;
        }else {
            return LoadingPage.ResultState.STATE_ERROR;
        }
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//显示左上角返回键
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        return true;
    }
}
