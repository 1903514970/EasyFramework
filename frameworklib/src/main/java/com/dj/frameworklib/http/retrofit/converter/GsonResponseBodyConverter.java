package com.dj.frameworklib.http.retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        String valueJson = value.string();
        try {
            //先直接JSON解析
            return adapter.fromJson(valueJson);
        } catch (Exception ex){
            //如果解析报错，则先移除data数据，用于得到服务器返回的错误数据。
            //为啥这么做：javabean中data可能定义的是Object类型，但是服务器返回的data="";
            //这种情况下，不读取data以保证能得到服务器返回的错误提示，类似登录密码错误之类的
            try {
                JSONObject object = new JSONObject(valueJson);
                object.remove("data");
                return adapter.fromJson(object.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            value.close();
        }
        return null;
    }
}
