package com.kunye.zhihu.http.protocol;

import com.google.gson.Gson;
import com.kunye.zhihu.bean.LatestBean;

import java.util.ArrayList;

/**
 * Created by kunye on 2017/2/16.
 */

public class IndexProtocol extends BaseProtocol<ArrayList<LatestBean.StoriesBean>> {
    ArrayList<LatestBean.TopStoriesBean> pictures;
    String date;

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
            storiesBean = new LatestBean.StoriesBean();
            storiesBean.title = latestBean.stories.get(i).title;
            storiesBean.images = latestBean.stories.get(i).images;
            storiesBean.id = latestBean.stories.get(i).id;
            list.add(storiesBean);
        }

        LatestBean.TopStoriesBean topStoriesBean;
        pictures = new ArrayList<LatestBean.TopStoriesBean>();
        for (int j = 0;j<5;j++){
            topStoriesBean = new LatestBean.TopStoriesBean();
            topStoriesBean.image = latestBean.top_stories.get(j).image;
            topStoriesBean.title = latestBean.top_stories.get(j).title;
            topStoriesBean.id = latestBean.top_stories.get(j).id;
            pictures.add(topStoriesBean);
        }

        return list;
    }

    public ArrayList<LatestBean.TopStoriesBean> getPictureList(){
        return pictures;
    }

}
