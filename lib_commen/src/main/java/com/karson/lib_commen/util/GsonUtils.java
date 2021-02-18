package com.karson.lib_commen.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @Author karson
 * @Date 2020/7/29-17:00
 * @desc
 */
public class GsonUtils {

    public static String toJson(Object ob){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(ob);
    }

    public static ArrayList<String> fromString(String jsonStr, ArrayList<String> arrayList){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type type;
        type = new TypeToken<ArrayList<String>>(){}.getType();
        return gson.fromJson(jsonStr, type);
    }
}
