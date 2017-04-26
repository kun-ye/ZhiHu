package com.kunye.zhihu.http.protocol;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kunye.zhihu.http.HttpHelper;
import com.kunye.zhihu.http.OKManager;
import com.kunye.zhihu.utils.IOUtils;
import com.kunye.zhihu.utils.StringUtils;
import com.kunye.zhihu.utils.UIUtils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 访问网络基类
 *      -请求网络数据
 *      -缓存机制（读写缓存）
 *      -解析数据
 * Created by kunye on 2016/12/9.
 */

public abstract class BaseProtocol<T> {
    private String jsonResult;
    private OKManager okManager;
    private Handler handler;
    private final static int SUCCESS_STATUS = 1;
    private final static int FAIL_STATUS = 0;

    public T getData(String url){
        //判断是否有缓存，有的话先加载缓存
        String result = getCache(url+getKey());
        if (StringUtils.isEmpty(result)){//没有缓存或者缓存失效
            result = getDataFromServer(url);
        }
        //开始解析
        if (result != null){
            T data = parseData(result);
            return data;
        }
        return null;
    }

    //从网络获取值,根据url
    private String getDataFromServer(String url) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(url+getKey());
        if (httpResult != null){
            String result = httpResult.getString();
            //写缓存
            if (!StringUtils.isEmpty(result)){
                setCache(url,result);
            }
            return result;
        }
        return null;

//        okManager = OKManager.getInstance();
//        final Message msg = Message.obtain();
//        okManager.asyncJsonStringByURL(url + getKey(), new OKManager.Func1() {
//            @Override
//            public void onResponse(String result) {
//                jsonResult = result;
//                Log.e("TEST-->",jsonResult);
//                if (jsonResult != null){
//                    msg.what = SUCCESS_STATUS;
//                    msg.obj = jsonResult;
//                }else {
//                    msg.what = FAIL_STATUS;
//                }
//                handler.sendMessage(msg);
//            }
//        });
//        handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what){
//                    case SUCCESS_STATUS:
//                        String json = msg.obj+"";
//                        break;
//                    case FAIL_STATUS:
//                        break;
//                }
//            }
//        };
//        if (jsonResult != null){
//            if (!StringUtils.isEmpty(jsonResult)){
//                setCache(url,jsonResult);
//            }
//            return jsonResult;
//        }
//        return null;

    }

    //获取网络链接关键字
    public abstract String getKey();

    //获取网络链接参数
    public abstract String getParams();

    //解析数据
    public abstract T parseData(String result);

    //写缓存以url为文件名;以json为文件内容
    public void setCache(String url,String json){
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹
        File cacheFile = new File(cacheDir,url+getKey());//生成缓存文件
        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            long deadline = System.currentTimeMillis()+30*60*1000;//缓存失效截止时间
            writer.write(deadline+"\n");//第一行写入缓存存储时间
            writer.write(json);//写入json
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }

    //读缓存
    public String getCache(String url){
        File cacheDir = UIUtils.getContext().getCacheDir();//本应用的缓存文件夹
        File cacheFile = new File(cacheDir,url+getKey());//生成缓存文件
        //判断缓存是否存在
        if (cacheFile.exists()){
            //判断缓存是否有效
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadline = reader.readLine();
                long deadtime = Long.parseLong(deadline);
                if (System.currentTimeMillis()<deadtime){
                    //缓存有效
                    String line;
                    StringBuffer sb = new StringBuffer();
                    while ((line = reader.readLine())!=null){
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

}

