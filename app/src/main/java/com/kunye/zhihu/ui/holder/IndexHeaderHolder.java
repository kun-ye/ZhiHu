package com.kunye.zhihu.ui.holder;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kunye.zhihu.R;
import com.kunye.zhihu.bean.LatestBean;
import com.kunye.zhihu.utils.BitmapHelper;
import com.kunye.zhihu.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * 头布局
 * Created by kunye on 2017/2/20.
 */

public class IndexHeaderHolder extends BaseHolder<ArrayList<LatestBean.TopStoriesBean>> {
    private ViewPager mViewPager;
    private TextView textView;
    private ArrayList<LatestBean.TopStoriesBean> data;
    private LinearLayout mIndicator;
    private int mPreviousPos;// 上一个被选中圆点的位置

    @Override
    public View initView() {
        //创建根布局，相对布局
        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
        //初始化布局参数
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UIUtils.dp2px(220));
        rlRoot.setLayoutParams(params);
        //ViewPager
        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlRoot.addView(mViewPager,vpParams);//把viewpager添加给相对布局
        //textView
        textView = new TextView(UIUtils.getContext());
        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.setMargins(10,320,0,10);
        textView.setTextSize(18);
        textView.setPadding(10,10,10,10);
        textView.setTextColor(Color.WHITE);
        rlRoot.addView(textView,tvParams);

        // 页码指示器
        mIndicator = new LinearLayout(UIUtils.getContext());
        // 设置边距
        int padding = UIUtils.dp2px(5);
        mIndicator.setPadding(padding, padding, padding, padding);

        // 初始化页码指示器布局参数
        RelativeLayout.LayoutParams iParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置当前线性布局相对于父控件的位置
        iParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        iParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        mIndicator.setLayoutParams(iParams);

        // 将页码指示器添加给根布局
        rlRoot.addView(mIndicator);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                int pos = arg0 % data.size();

                // 将当前圆点设置为选中样式
                ImageView view = (ImageView) mIndicator.getChildAt(pos);
                view.setImageResource(R.drawable.indicator_selected);

                if (pos != mPreviousPos) {
                    // 将上一个圆点设置为默认样式
                    ImageView prView = (ImageView) mIndicator
                            .getChildAt(mPreviousPos);
                    prView.setImageResource(R.drawable.indicator_normal);
                }

                mPreviousPos = pos;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        return rlRoot;
    }

    @Override
    public void refreshView(final ArrayList<LatestBean.TopStoriesBean> data) {
        this.data = data;
        textView.setText(data.get(0).title);
        //填充viewpager数据
        mViewPager.setAdapter(new IndexHeaderAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                int pos = position % data.size();
                textView.setText(data.get(pos).title);
            }
        });

        mIndicator.removeAllViews();// 保险期间,先清除所有子view
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < data.size(); i++) {
            ImageView view = new ImageView(UIUtils.getContext());
            if (i == 0) {
                view.setImageResource(R.drawable.indicator_selected);
            } else {
                view.setImageResource(R.drawable.indicator_normal);
                params.leftMargin = UIUtils.dp2px(3);// 设置圆点间距
            }

            mIndicator.addView(view, params);
        }

        // 设置viewpager滑动的初始位置
        mViewPager.setCurrentItem(data.size() * 1000);

        // 开启自动轮播效果
        new RunnableTask().start();
    }

    class RunnableTask implements Runnable {

        public void start() {
            // 移除之前遗留的任务(handler只有一个,但HomeFragment有可能多次被创建,
            // 从而导致消息被重复发送,所以需要先把之前的消息移除掉)
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            // 发送延时2秒的任务
            UIUtils.getHandler().postDelayed(this, 2000);
        }

        @Override
        public void run() {
            // 跳到viewpager下一个页面
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);

            // 继续发送延时两秒的任务, 形成闭环, 达到循环执行的效果
            UIUtils.getHandler().postDelayed(this, 2000);
        }

    }

    class IndexHeaderAdapter extends PagerAdapter{
        private BitmapUtils mBitmapUtils;

        public IndexHeaderAdapter() {
            mBitmapUtils = BitmapHelper.getBitmapUtils();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % data.size();
            ImageView view = new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mBitmapUtils.display(view,data.get(pos).image);
            container.addView(view);
            return view;
        }
    }
}
