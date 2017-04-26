package com.kunye.zhihu.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kunye.zhihu.R;
import com.kunye.zhihu.bean.LatestBean;
import com.kunye.zhihu.global.GlobalConstants;
import com.kunye.zhihu.http.protocol.IndexMoreDataProtocol;
import com.kunye.zhihu.http.protocol.IndexProtocol;
import com.kunye.zhihu.ui.activity.DetailInfoActivity;
import com.kunye.zhihu.ui.adapter.MyBaseAdapter;
import com.kunye.zhihu.ui.holder.BaseHolder;
import com.kunye.zhihu.ui.holder.IndexHeaderHolder;
import com.kunye.zhihu.ui.holder.IndexHolder;
import com.kunye.zhihu.ui.view.LoadingPage;
import com.kunye.zhihu.ui.view.MyListView;
import com.kunye.zhihu.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 首页
 * Created by admin on 2017/1/22.
 */

public class IndexFragment extends BaseFragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<LatestBean.StoriesBean> data;
    private ArrayList<LatestBean.StoriesBean> dataMore;
    private ArrayList<LatestBean.TopStoriesBean> mPictureList;

    @Override
    public View onCreateSuccessView() {
        View main = UIUtils.inflate(R.layout.fragment_index);
        MyListView view = (MyListView) main.findViewById(R.id.rl);
        mSwipeRefreshLayout = (SwipeRefreshLayout) main.findViewById(R.id.srl);
//        MyListView view = new MyListView(UIUtils.getContext());
//        mSwipeRefreshLayout = new SwipeRefreshLayout(UIUtils.getContext());
        mSwipeRefreshLayout.setEnabled(false);
        //加轮播条
        IndexHeaderHolder header = new IndexHeaderHolder();
        view.addHeaderView(header.getRootView());
        view.setAdapter(new IndexAdapter(data));
        if (mPictureList != null){
            //设置轮播条数据
            header.setData(mPictureList);
        }
        TextView title = new TextView(UIUtils.getContext());
        title.setText("今日热闻");
        title.setPadding(20,20,20,20);
        title.setTextSize(15);
        title.setTextColor(Color.GRAY);
        view.addHeaderView(title,data,false);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        //为listview设置点击事件
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatestBean.StoriesBean latestInfo = data.get(position-2);
                if (latestInfo != null){
                    Intent intent = new Intent(UIUtils.getContext(), DetailInfoActivity.class);
                    String idItem = latestInfo.id+"";
                    intent.putExtra("id",idItem);
                    startActivity(intent);
                }
            }
        });
        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.e("position",view.getFirstVisiblePosition()+"");
                if (firstVisibleItem == 0){
                    mSwipeRefreshLayout.setEnabled(true);
                }else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });
        return main;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        //请求网络
        IndexProtocol protocol = new IndexProtocol();
        data = protocol.getData(GlobalConstants.LATEST_URL);
        mPictureList = protocol.getPictureList();
        return check(data);
    }



    class IndexAdapter extends MyBaseAdapter<LatestBean.StoriesBean>{

        public IndexAdapter(List<LatestBean.StoriesBean> data) {
            super(data);
        }

        @Override
        public BaseHolder<LatestBean.StoriesBean> getHolder() {
            return new IndexHolder();
        }

        @Override
        public ArrayList<LatestBean.StoriesBean> onLoadMore() {
            IndexMoreDataProtocol protocol = new IndexMoreDataProtocol();
            dataMore = protocol.getData("http://news.at.zhihu.com/api/4/news/before/"+getDate());
            return dataMore;
        }

    }

}
