package com.kunye.zhihu.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kunye.zhihu.ui.holder.BaseHolder;
import com.kunye.zhihu.ui.holder.MoreHolder;
import com.kunye.zhihu.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 对adapter进行封装
 * Created by kunye on 2017/2/16.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_MORE = 1;

    private List<T> data;

    private int i = 1;
    private  SimpleDateFormat simpleDateFormat;
    private String date;

    public MyBaseAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回当前位置应该展示那种布局类型
    @Override
    public int getItemViewType(int position) {
        if (position == getCount()-1){
            return TYPE_MORE;
        }else {
            return getInnerType();
        }
    }

    //子类可以重写此方法来更改返回布局的类型
    public int getInnerType(){
        return TYPE_NORMAL;
    }

    //返回布局类型个数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null){
            //1,加载布局文件
            //2.初始化控件
            //3.打标记
            //判断是否是加载更多
            if (getItemViewType(position) == TYPE_MORE){
                //加载更多
                holder = new MoreHolder(hasMore());
            }else {
                holder = getHolder(); 
            }
        }else {
            holder = (BaseHolder) convertView.getTag();
        }
        //4.刷新数据
        if (getItemViewType(position) != TYPE_MORE){
            holder.setData(getItem(position));
        }else {
            //加载更多布局
            MoreHolder moreHolder = (MoreHolder) holder;
            if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE){
                loadMore(moreHolder);
            }
        }
        return holder.getRootView();
    }

    public boolean hasMore(){
        return true;//默认都是有更多数据
    }

    public abstract BaseHolder getHolder();
    //加载更多数据,必须由子类实现
    public abstract ArrayList<T> onLoadMore();

    private boolean isLoadMore = false;//标记是否正在加载更多
    //加载更多数据
    public void loadMore(final MoreHolder moreHolder) {
        if (!isLoadMore) {
            isLoadMore = true;
            new Thread(){
                @Override
                public void run() {
                    final ArrayList<T> moreData = onLoadMore();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null){
                                //每一页20条，如果返回的小于20条，就认为到了最后一页
                                if (moreData.size()>21||moreData.size()<16){
                                    moreHolder.setData(MoreHolder.STATE_MORE_NONE);//更新界面
                                }else {
                                    moreHolder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                //将更多数据追加list集合中
                                TextView title = new TextView(UIUtils.getContext());
                                title.setText(getFormatDate());
                                title.setPadding(20,20,20,20);
                                title.setTextSize(15);
                                title.setTextColor(Color.GRAY);

                                data.addAll(moreData);
                                //刷新界面
                                MyBaseAdapter.this.notifyDataSetChanged();
                            }else {
                                //没有moreData，加载失败
                                moreHolder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            }.start();
        }
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-i);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(calendar.getTime());
        date = simpleDateFormat.format(calendar.getTime());
        i++;
        return time;
    }

    private String getFormatDate(){
        return date;
    }

}
