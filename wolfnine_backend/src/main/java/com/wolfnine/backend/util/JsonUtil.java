package com.wolfnine.backend.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wolfnine.backend.constant.CrawlConstant;

public class JsonUtil {
    public static JsonObject getItemFromArrayByKey(String key, JsonArray jsonArray) {
       for(int i = 0; i < jsonArray.size(); i++) {
           JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
           if(jsonObject.get(CrawlConstant.CONFIG_SELECTOR_KEY).getAsString().equals(key)) {
                return jsonObject;
           }
       }
       return new JsonObject();
    }
}
