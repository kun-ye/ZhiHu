package com.kunye.zhihu.http.protocol;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.kunye.zhihu.bean.BoredBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/23.
 */

public class BoredProtocol extends BaseProtocol<ArrayList<BoredBean.StoriesBean>> {
    BoredBean bored;
    ArrayList<BoredBean.EditorsBean> editorsList;

    @Override
    public String getKey() {
        return "";
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public ArrayList<BoredBean.StoriesBean> parseData(String result) {
        Gson gson = new Gson();
        BoredBean boredBean = gson.fromJson(result,BoredBean.class);
        BoredBean.StoriesBean storiesBean;
        ArrayList<BoredBean.StoriesBean> list = new ArrayList<BoredBean.StoriesBean>();
        for (int i = 0;i<boredBean.stories.size();i++){
            storiesBean = new BoredBean.StoriesBean();
            storiesBean.id = boredBean.stories.get(i).id;
            storiesBean.title = boredBean.stories.get(i).title;
            storiesBean.images = boredBean.stories.get(i).images;
            list.add(storiesBean);
        }

        bored = new BoredBean();
        bored.name = boredBean.name;
        bored.image = boredBean.image;
        bored.background = boredBean.background;
        bored.description = boredBean.description;
        bored.color = boredBean.color;

        BoredBean.EditorsBean editorsBean = null;
        editorsList = new ArrayList<BoredBean.EditorsBean>();
        for (int i = 0;i<boredBean.editors.size();i++){
            editorsBean = new BoredBean.EditorsBean();
            editorsBean.avatar = boredBean.editors.get(i).avatar;
            editorsList.add(editorsBean);
        }
//
//        try {
//            JSONArray ja = new JSONArray(result);
//            editorsList = new ArrayList<BoredBean.EditorsBean>();
//            for (int i=0;i<ja.length();i++){
//                editorsBean = new BoredBean.EditorsBean();
//                JSONObject jo = ja.getJSONObject(i);
//                editorsBean.avatar = jo.getString("avatar");
//                editorsList.add(editorsBean);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return list;
    }

    public BoredBean getBored(){
        return bored;
    }

    public  ArrayList<BoredBean.EditorsBean> getEditors(){
        return editorsList;
    }
}
