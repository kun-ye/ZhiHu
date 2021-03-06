package com.kunye.zhihu.ui.fragment;

import android.content.Intent;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunye.zhihu.R;
import com.kunye.zhihu.bean.BoredBean;
import com.kunye.zhihu.global.GlobalConstants;
import com.kunye.zhihu.http.protocol.BoredProtocol;
import com.kunye.zhihu.ui.activity.DetailInfoActivity;
import com.kunye.zhihu.ui.adapter.MyBaseAdapter;
import com.kunye.zhihu.ui.holder.BaseHolder;
import com.kunye.zhihu.ui.holder.BoredHolder;
import com.kunye.zhihu.ui.view.LoadingPage;
import com.kunye.zhihu.ui.view.MyListView;
import com.kunye.zhihu.utils.BitmapHelper;
import com.kunye.zhihu.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户推荐日报
 * Created by kunye on 2017/1/22.
 */

public class UserFragment extends BaseFragment {
    private ArrayList<BoredBean.StoriesBean> data;
    private ArrayList<BoredBean.EditorsBean> editors;
    private BoredBean bored;
    private BitmapUtils mBitmapUtils;

    private ImageView otherTitle,editor_image1,editor_image2,editor_image3,editor_image4,editor_image5;
    private TextView other_textview;
    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        View header = UIUtils.inflate(R.layout.other_header_pager);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        otherTitle = (ImageView) header.findViewById(R.id.other_title);
        other_textview = (TextView) header.findViewById(R.id.other_textview);
        other_textview.setText(bored.description);
        mBitmapUtils.display(otherTitle,bored.background);
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0,0,view.getWidth(),view.getHeight());
            }
        };

        editor_image1 = (ImageView ) header.findViewById(R.id.editor_image1);
        editor_image1.setVisibility(View.VISIBLE);
        mBitmapUtils.display(editor_image1,editors.get(0).avatar);
        editor_image1.setOutlineProvider(viewOutlineProvider);
        editor_image1.setClipToOutline(true);

        view.addHeaderView(header,data,false);
        view.setAdapter(new BoredAdapter(data));
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoredBean.StoriesBean storiesInfo = data.get(position-1);
                if (storiesInfo != null){
                    Intent intent = new Intent(UIUtils.getContext(), DetailInfoActivity.class);
                    String idItem = storiesInfo.id+"";
                    intent.putExtra("id",idItem);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        //请求网络
        BoredProtocol protocol = new BoredProtocol();
        data = protocol.getData(GlobalConstants.USER_URL);
        editors = protocol.getEditors();
        bored = protocol.getBored();
        return check(data);
    }

    class BoredAdapter extends MyBaseAdapter<BoredBean.StoriesBean> {

        public BoredAdapter(List<BoredBean.StoriesBean> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new BoredHolder();
        }

        @Override
        public ArrayList<BoredBean.StoriesBean> onLoadMore() {
            return null;
        }
    }
}
