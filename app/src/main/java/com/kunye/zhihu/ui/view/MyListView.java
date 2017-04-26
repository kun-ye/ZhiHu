package com.kunye.zhihu.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by kunye on 2016/12/10.
 */

public class MyListView extends ListView {

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setSelector(new ColorDrawable());//设置默认状态选择器是全透明，不给设置颜色就是默认透明
        this.setDivider(null);//去掉分隔线
        this.setCacheColorHint(Color.TRANSPARENT);//有时候滑动listview背景会变成黑色，此方法背景变成全透明
    }

    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
}
