package com.kunye.zhihu.bean;

import java.util.List;

/**
 * Created by admin on 2017/2/21.
 */

public class DetailInfoBean {

    public String body;
    public String image_source;
    public String title;
    public String image;
    public String share_url;
    public String ga_prefix;
    public SectionBean section;
    public int type;
    public int id;
    public List<?> js;
    public List<String> images;
    public List<String> css;

    public static class SectionBean {
        public String thumbnail;
        public int id;
        public String name;
    }
}
