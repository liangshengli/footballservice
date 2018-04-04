package com.football.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	
	/**
	 * 实体转string
	 * @param bean
	 * @return
	 */
	public static String beanToJson(Object bean){
		JSONObject jsonObject = JSONObject.fromObject(bean); 
		return jsonObject.toString();
	}
	
	public static <T> Object jsonToObject(String json,Class<T> beanClz){
		 
		return JSONObject.toBean(JSONObject.fromObject(json),beanClz);
	}
	
	public static <T> String ListToJson(List<T> list)
	{
		JSONArray jsonArray=JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	public static <T> List<T> JsonToList(String json,Class<T> beanClz) {
		JSONArray jsonArray = JSONArray.fromObject(json);  
        JSONObject jsonObject;  
        Object pojoValue;  
  
        List<T> list = new ArrayList<T>();  
        for (int i = 0; i < jsonArray.size(); i++) {  
  
            jsonObject = jsonArray.getJSONObject(i);  
            pojoValue = JSONObject.toBean(jsonObject, beanClz);  
            list.add((T)pojoValue);  
        }  
        return list;  
	}
	public static Map<String, Object> json2Map(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        Map<String, Object> valueMap = new HashMap<String, Object>();
        while (keyIter.hasNext()) {
            key = (String) keyIter.next();
            value = jsonObject.get(key);
            valueMap.put(key, value);
        }
        return valueMap;
    }
	
	 
	 
}
