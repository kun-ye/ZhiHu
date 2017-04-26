package com.kunye.zhihu.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * 单例模式 懒汉
 * Created by kunye on 2016/12/10.
 */

public class BitmapHelper {

    private static BitmapUtils mBirmapUtils = null;

    public static BitmapUtils getBitmapUtils(){
        if (mBirmapUtils == null){
            synchronized(BitmapHelper.class){
                if (mBirmapUtils == null){
                    mBirmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return  mBirmapUtils;
    }
}
