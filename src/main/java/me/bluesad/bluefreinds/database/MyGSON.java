package me.bluesad.bluefreinds.database;

import com.google.gson.Gson;

/**
 * @author bluesad
 * */
public class MyGSON {
    private static Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json,Class<T> type){
        return gson.fromJson(json,type);
    }
}
