package com.kunye.zhihu.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunye.zhihu.R;
import com.kunye.zhihu.bean.LatestBean;
import com.kunye.zhihu.utils.BitmapHelper;
import com.kunye.zhihu.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;
import com.squareup.picasso.Picasso;

/**
 * 首页holder
 * Created by kunye on 2017/2/16.
 */

public class IndexHolder extends BaseHolder<LatestBean.StoriesBean> {
    private TextView textView;
    private ImageView imageView;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        //1 加载布局
        View view = UIUtils.inflate(R.layout.list_item);
        //2 初始化控件
        textView = (TextView) view.findViewById(R.id.tv_title);
        imageView = (ImageView) view.findViewById(R.id.iv_icon);
        mBitmapUtils = BitmapHelper.getBitmapUtils();//单例模式
        return view;
    }

    @Override
    public void refreshView(LatestBean.StoriesBean data) {
        textView.setText(data.title);
        mBitmapUtils.display(imageView,data.images.get(0));
//        Picasso.with(UIUtils.getContext()).load(data.images.get(0)).into(imageView);
    }

}
