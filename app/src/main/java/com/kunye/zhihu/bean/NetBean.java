package com.kunye.zhihu.bean;

import java.util.List;

/**
 * 互联网安全
 * Created by kunye on 2017/2/23.
 */

public class NetBean {

    public String description;
    public String background;
    public int color;
    public String name;
    public String image;
    public String image_source;
    public List<StoriesBean> stories;
    public List<EditorsBean> editors;

    public static class StoriesBean {
        public int type;
        public int id;
        public String title;
        public List<String> images;
    }

    public static class EditorsBean {
        public String url;
        public String bio;
        public int id;
        public String avatar;
        public String name;
    }
}
