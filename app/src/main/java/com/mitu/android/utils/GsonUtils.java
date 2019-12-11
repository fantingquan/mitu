package com.mitu.android.utils;

import android.text.TextUtils;

import com.mitu.android.base.BaseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HuangQiang on 18/5/8.
 */

public class GsonUtils {
    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithModifiers(
                        Modifier.FINAL,
                        Modifier.TRANSIENT,
                        Modifier.STATIC);
        gson = gsonBuilder.create();
    }

    private GsonUtils() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }


    /**
     * 转成bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(JsonObject json, Class<T> cls) {
        T t = null;

        try {
            if (gson != null) {
                t = gson.fromJson(json, cls);
            }
        } catch (Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }

        return t;
    }

    public static <T> BaseModel<T> GsonToDDCModel(JsonObject json, Type type) {
        BaseModel<T> baseModel = null;

        try {
            if (gson != null) {
                baseModel = gson.fromJson(json, type);
            }
        } catch (Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }

        return baseModel;
    }

    public static <T> BaseModel<T> GsonToDDCModel(JsonObject json, Class<T> clazz) {
        BaseModel<T> baseModel = null;

        try {
            if (gson != null) {
                Type type = new ParameterizedTypeImpl(BaseModel.class, new Class[]{clazz});
                baseModel = gson.fromJson(json, type);
            }
        } catch (Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }

        return baseModel;
    }
    public static <T> BaseModel<T> GsonToDDCModel(String jsonStr, Class<T> clazz) {
        BaseModel<T> baseModel = null;

        try {
            if (gson != null) {
                Type type = new ParameterizedTypeImpl(BaseModel.class, new Class[]{clazz});
                baseModel = gson.fromJson(jsonStr, type);
            }
        } catch (Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }

        return baseModel;
    }

    public static <T> BaseModel<List<T>> GsonToDDCModelList(JsonObject json, Class<T> clazz) {
        BaseModel<List<T>> baseModel = null;

        try {
            if (gson != null) {
                Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
                Type type = new ParameterizedTypeImpl(BaseModel.class, new Type[]{listType});
                baseModel = gson.fromJson(json, type);
            }
        } catch (Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }

        return baseModel;
    }

    public static <T> BaseModel<List<T>> GsonRelaceToDDCModelList(JsonObject json, Class<T> clazz) {
        BaseModel<List<T>> baseModel = null;

        try {
            if (gson != null) {
                Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
                Type type = new ParameterizedTypeImpl(BaseModel.class, new Type[]{listType});
                baseModel = gson.fromJson(replaceStringJson(json), type);
            }
        } catch (Throwable throwable) {
            LogUtils.e(throwable.getMessage());
        }

        return baseModel;
    }


    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * map转成JSON
     *
     * @param map
     * @return
     */
    public static JsonObject MapToJson(Map map) {
        JsonObject json = null;
        if (gson != null) {
            json = new JsonParser().parse(gson.toJson(map)).getAsJsonObject();
        }
        return json;
    }

    /**
     * 转换Json
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T GsonReplaceStringToBean(JsonObject json, Class<T> cls) {
        T t = null;
        try {
            if (gson != null) {
                t = gson.fromJson(replaceStringJson(json), cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * 处理json中是String的json串
     *
     * @param jsonData
     * @return
     */
    public static String replaceStringJson(JsonObject jsonData) {
        String strJson = jsonData == null ? "" : jsonData.toString();
        if (TextUtils.isEmpty(strJson)) return null;
        strJson = strJson.replace("\"[{", "[{");
        strJson = strJson.replace("\\\"", "\"");
        strJson = strJson.replace("}]\"", "}]");
        strJson = strJson.replace("\"{", "{");
        strJson = strJson.replace("}\"", "}");
        strJson = strJson.replace("\"null\"", "null");
        LogUtils.w("replaceStringJson", strJson);
        return strJson;
    }
}
