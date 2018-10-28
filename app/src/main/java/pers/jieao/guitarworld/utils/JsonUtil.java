package pers.jieao.guitarworld.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pers.jieao.guitarworld.model.Creation;

public class JsonUtil {

    public static List<Creation> getCreationList(String string) {
        List<Creation> creationList = null;
        try {
            JSONObject responseJsonObject = new JSONObject(string);
            if (responseJsonObject.getInt("code") == 0) {
                JSONArray creationArray = responseJsonObject.getJSONArray("data");
                for (int i = 0; i < creationArray.length(); i++) {
                    Creation creation = new Creation();
                    JSONObject jsonObject = creationArray.getJSONObject(i);
                    creation.setAuthorId(jsonObject.getString("authorId"));
                    creation.setTitle(jsonObject.getString("title"));
                    creation.setContent(jsonObject.getString("content"));
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = simpleDateFormat.parse(jsonObject.getString("sendTime"));
                        creation.setCreateTime(date);
                    } catch (ParseException px) {
                        px.printStackTrace();
                    }
                    JSONArray commentArray = responseJsonObject.getJSONArray("commentList");
                    /*
                    List<Comment> commentList = new LinkedList<>();
                    for (int j = 0; j < commentArray.length(); j++) {
                        Comment comment = new Comment();
                        //TODO 解析comment
                        JSONObject jsonObject2 = creationArray.getJSONObject(j);
                        commentList.add(comment);
                    }*/

                    creationList.add(creation);
                }
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return creationList;
    }
}
