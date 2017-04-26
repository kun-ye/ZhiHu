package com.kunye.zhihu.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.kunye.zhihu.global.ZhiHuApplication;


/**
 * Created by kunye on 2016/12/6.
 */

public class UIUtils {

    public static Context getContext(){
        return ZhiHuApplication.getContext();
    }

    public static Handler getHandler(){
        return ZhiHuApplication.getHandler();
    }

    public static int getMainThreadId(){
        return ZhiHuApplication.getMainThreadId();
    }

    ///////////////////////////////加载资源文件///////////////////////////////////////
    //获取字符串
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }

    //获取字符串数组
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }

    //获取图片
    public static Drawable getDrawble(int id){
        return getContext().getResources().getDrawable(id);
    }

    //获取颜色
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }

    //获取尺寸
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);//返回具体的像素值
    }

    ///////////////////////////////dp与sp转换///////////////////////////////////////
    public static int dp2px(float dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp*density + 0.5f);
    }

    public static float px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return px/density;
    }

    ///////////////////////////////加载布局文件///////////////////////////////////////
    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }

    ///////////////////////////////判断是否运行在主线程////////////////////////////////
    public static boolean isRunOnUIThread(){
        //获取当前线程id是否与主线程相同
        int myTid= android.os.Process.myTid();
        if (myTid == getMainThreadId()){
            return true;
        }
        return false;
    }

    //运行主线程
    public static void runOnUIThread(Runnable r){
        if (isRunOnUIThread()){
            //是主线程就run
            r.run();
        }else {
            //若子线程借助handler让其运行在主线程
            getHandler().post(r);
        }
    }
}
