package com.kunye.zhihu.http.protocol;

import com.google.gson.Gson;
import com.kunye.zhihu.bean.DetailInfoBean;

/**
 * Created by admin on 2017/2/21.
 */

public class DetailProtocol extends BaseProtocol<DetailInfoBean> {
    private String id;

    @Override
    public String getKey() {
        return getId();
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public DetailInfoBean parseData(String result) {
        Gson gson = new Gson();
        DetailInfoBean detailInfoBean = gson.fromJson(result,DetailInfoBean.class);
        DetailInfoBean info = new DetailInfoBean();
        info.body = detailInfoBean.body;
        info.image = detailInfoBean.image;
        info.title = detailInfoBean.title;
        info.image_source = detailInfoBean.image_source;
        return info;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id  = id;
    }
}
