package com.kunye.zhihu.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kunye.zhihu.R;
import com.kunye.zhihu.utils.UIUtils;

/**
 * 加载更多布局
 * Created by kunye on 2017/2/16.
 */

public class MoreHolder extends BaseHolder<Integer> {
    /**
     *加载更多的几种状态1：可以加载更多；2：加载失败；3：没有更多数据
     */
    public static final int STATE_MORE_MORE = 1;
    public static final int STATE_MORE_ERROR = 2;
    public static final int STATE_MORE_NONE = 3;

    private LinearLayout ll_load_more;
    private TextView tv_load_error;

    public MoreHolder(boolean hasMore) {
        //如果有更多数据，将此状态给父类data，父类会同时刷新页面
        if (hasMore){
            setData(STATE_MORE_MORE);
        }else {
            setData(STATE_MORE_NONE);
        }
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        ll_load_more = (LinearLayout) view.findViewById(R.id.ll_load_more);
        tv_load_error = (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data){
            case STATE_MORE_MORE:
                //显示加载更多
                ll_load_more.setVisibility(View.VISIBLE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE:
                //隐藏加载更多
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                //加载失败
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
