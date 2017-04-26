package com.kunye.zhihu.bean;

import java.util.List;

/**
 * Created by admin on 2017/2/13.
 */

public class LatestBean {

    public String date;
    public List<StoriesBean> stories;
    public List<TopStoriesBean> top_stories;

    public static class StoriesBean {
        public int type;
        public int id;
        public String ga_prefix;
        public String title;
        public List<String> images;

    }

    public static class TopStoriesBean {
        public String image;
        public int type;
        public int id;
        public String ga_prefix;
        public String title;
    }
}
