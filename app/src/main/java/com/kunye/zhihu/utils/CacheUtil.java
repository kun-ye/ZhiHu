package com.kunye.zhihu.utils;

import android.content.Context;

/**
 * 网络缓存工具类
 * Created by kunye on 2016/11/25.
 */

public class CacheUtil {
    /**
     *保存到本地
     * @param url key
     * @param json value
     */
    public static void setCache(Context context,String url, String json){
        SpUtil.putString(context,url,json);
    }

    public static String getCache(Context context,String url){
        return SpUtil.getString(context,url,null);
    }

}
