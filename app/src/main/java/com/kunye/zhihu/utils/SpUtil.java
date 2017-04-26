package com.kunye.zhihu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kunye on 2016/11/23.
 */

public class SpUtil {
    private static final String CACHE_FILE_NAME = "kunye";

    private static SharedPreferences mSharedPreferences;

    /**
     *booleean类型
     */
    public static void putBoolean(Context context,String key,boolean value){
        if (mSharedPreferences == null){
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME,Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context,String key,boolean defValue){
        if (mSharedPreferences == null){
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME,Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getBoolean(key,defValue);
    }

    /**
     *String类型
     */
    public static void putString(Context context,String key,String value){
        if (mSharedPreferences == null){
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME,Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String defValue){
        if (mSharedPreferences == null){
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME,Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getString(key,defValue);
    }

    /**
     *int类型
     */
    public static void putInt(Context context,String key,int value){
        if (mSharedPreferences == null){
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME,Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putInt(key,value).commit();
    }

    public static int getInt(Context context,String key,int defValue){
        if (mSharedPreferences == null){
            mSharedPreferences = context.getSharedPreferences(CACHE_FILE_NAME,Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getInt(key,defValue);
    }

}
