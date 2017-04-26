package com.kunye.zhihu.ui.holder;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kunye.zhihu.R;
import com.kunye.zhihu.bean.DetailInfoBean;
import com.kunye.zhihu.utils.BitmapHelper;
import com.kunye.zhihu.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 详情页
 * Created by kunye on 2017/2/21.
 */

public class DetailInfoHolder extends BaseHolder<DetailInfoBean> {
    private ImageView detail_image;
    private TextView detail_title,image_source;
    private WebView web_detail;
    private BitmapUtils mBitmapUtils;
    private RelativeLayout rl_detail;
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.activity_detail_info);
        rl_detail = (RelativeLayout) view.findViewById(R.id.rl_detail);
        detail_image = (ImageView) view.findViewById(R.id.detail_image);
        detail_title = (TextView) view.findViewById(R.id.detail_title);
        image_source = (TextView) view.findViewById(R.id.image_source);
        web_detail = (WebView) view.findViewById(R.id.web_detail);
        mBitmapUtils = BitmapHelper.getBitmapUtils();//单例模式
        return view;
    }

    @Override
    public void refreshView(DetailInfoBean data) {
        if (data.image == null){
            rl_detail.setVisibility(View.GONE);
        }
        mBitmapUtils.display(detail_image,data.image);
        detail_title.setText(data.title);
        image_source.setText(data.image_source);
        web_detail.loadDataWithBaseURL(null,data.body,"text/html","utf-8",null);
        web_detail.getSettings().setUseWideViewPort(true);
        web_detail.getSettings().setLoadWithOverviewMode(true);
        WebSettings settings = web_detail.getSettings();
        settings.setTextSize(WebSettings.TextSize.LARGEST);
//        //设置网页支持javascript
//        settings.setJavaScriptEnabled(true);
//        //支持网页缩放
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        //显示网页缩放器
//        settings.setDisplayZoomControls(false);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }
}
