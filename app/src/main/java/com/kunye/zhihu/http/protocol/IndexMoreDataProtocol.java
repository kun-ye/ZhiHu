package com.kunye.zhihu.http.protocol;

import com.google.gson.Gson;
import com.kunye.zhihu.bean.LatestBean;

import java.util.ArrayList;

/**
 * Created by admin on 2017/2/22.
 */

public class IndexMoreDataProtocol extends BaseProtocol<ArrayList<LatestBean.StoriesBean>> {
    @Override
    public String getKey() {
        return "";
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public ArrayList<LatestBean.StoriesBean> parseData(String result) {
        Gson gson = new Gson();
        LatestBean latestBean = gson.fromJson(result,LatestBean.class);
        LatestBean.StoriesBean storiesBean;
        ArrayList<LatestBean.StoriesBean> list = new ArrayList<LatestBean.StoriesBean>();
        for (int i = 0;i<latestBean.stories.size();i++){
            //一定要在循环中重新new对象，不然每次都是同个对象被加载数据会重复！！！！
            storiesBean = new LatestBean.StoriesBean();
            storiesBean.title = latestBean.stories.get(i).title;
            storiesBean.images = latestBean.stories.get(i).images;
            storiesBean.id = latestBean.stories.get(i).id;
            list.add(storiesBean);
        }
        return list;
    }
}
