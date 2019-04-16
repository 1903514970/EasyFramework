package com.dj.frameworklib.utils.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Created by dengjun on 2019/3/27.
 */

class SharedPreferenceImpl<T extends ISharedPreferenceKeysEntity> implements ISharedPreference<T> {

    private Context context;
    private SharedPreferences mPreference;
    private T t;

    SharedPreferenceImpl(Context context, Class<T> clazz){
       this.context = context;
       mPreference = context.getSharedPreferences(getSharedPreferenceName(clazz), Context.MODE_PRIVATE);
    }

    private String getSharedPreferenceName(Class<T> clazz){
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t.getSharedPreferenceName();
    }

    /**
     * 用来存放SharePreferenceName和Keys的对象
     * @return
     */
    @Override
    public T getKeysObject() {
        return t;
    }

    @Override
    public boolean writeString(String key, String value) {
        try {
            return mPreference.edit().putString(key,value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean writeLong(String key, long value) {
        try {
            return mPreference.edit().putLong(key,value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean writeInt(String key, int value) {
        try {
            return mPreference.edit().putInt(key,value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean writeFloat(String key, float value) {
        try {
            return mPreference.edit().putFloat(key,value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean writeBoolean(String key, boolean value) {
        try {
            return mPreference.edit().putBoolean(key,value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean writeStringSet(String key, Set<String> value) {
        try {
            return mPreference.edit().putStringSet(key,value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean writeObject(String key, Object object) {
        if(object == null){
            return false;
        }
        try {
            return writeString(key, JSON.toJSONString(object));
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean writeList(String key, List arrays) {
        if(arrays == null){
            return false;
        }
        try {
            return writeString(key, JSON.toJSONString(arrays));
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String readString(String key, String defaultValue) {
        return mPreference.getString(key,defaultValue);
    }

    @Override
    public long readLong(String key, long defaultValue) {
        return mPreference.getLong(key,defaultValue);
    }

    @Override
    public int readInt(String key, int defaultValue) {
        return mPreference.getInt(key,defaultValue);
    }

    @Override
    public float readFloat(String key, float defaultValue) {
        return mPreference.getFloat(key,defaultValue);
    }

    @Override
    public boolean readBoolean(String key, boolean defaultValue) {
        return mPreference.getBoolean(key,defaultValue);
    }

    @Override
    public Set<String> readStringSet(String key) {
        return mPreference.getStringSet(key,null);
    }

    @Override
    public <S> S readObject(String key, Class<S> clazz) {
        String content = readString(key,"");
        try {
            return JSON.parseObject(content,clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <S> List<S> readList(String key, Class<S> clazz) {
        String content = readString(key,"");
        try {
            return JSON.parseArray(content,clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean write(String fieldName, Object value) {
        if(fieldName == null){
            return false;
        }

        SPReturnType anno = getFieldReturnType(fieldName);
        if(anno == null){
            Log.i("SharedPreferenceImpl","field should user annotation SPReturnType");
            return false;
        }

        SPReturnTypeEnum returnType = anno.returnType();

        if(SPReturnTypeEnum.Boolean.equals(returnType)){
            try {
                return writeBoolean(fieldName, (Boolean) value);
            }catch (Exception e){}
        }

        if(SPReturnTypeEnum.Long.equals(returnType)){
            try {
                return writeLong(fieldName, (Long) value);
            }catch (Exception e){}
        }

        if(SPReturnTypeEnum.Int.equals(returnType)){
            try {
                return writeInt(fieldName, (Integer) value);
            }catch (Exception e){}
        }

        if(SPReturnTypeEnum.Float.equals(returnType)){
            try {
                return writeFloat(fieldName, (Float) value);
            }catch (Exception e){}
        }

        if(SPReturnTypeEnum.String.equals(returnType)){
            try {
                return writeString(fieldName, (String) value);
            }catch (Exception e){}
        }

        if(SPReturnTypeEnum.StringSet.equals(returnType)){
            try {
                return writeStringSet(fieldName, (Set<String>) value);
            }catch (Exception e){}
        }

        if(SPReturnTypeEnum.Object.equals(returnType)){
            try {
                return writeObject(fieldName, value);
            }catch (Exception e){}
        }

        if(SPReturnTypeEnum.List.equals(returnType)){
            try {
                return writeList(fieldName, (List) value);
            }catch (Exception e){}
        }

        return false;
    }

    @Override
    public Object read(String fieldName) {
        if(fieldName == null){
            return null;
        }

        SPReturnType anno = getFieldReturnType(fieldName);
        if(anno == null){
            Log.i("SharedPreferenceImpl","field should user annotation SPReturnType");
            return null;
        }

        SPReturnTypeEnum returnType = anno.returnType();
        String defaultValue =  anno.defaultValue();

        if(SPReturnTypeEnum.Boolean.equals(returnType)){
            try {
                return readBoolean(fieldName,Boolean.parseBoolean(defaultValue));
            }catch (Exception e){
                return readBoolean(fieldName,false);
            }
        }

        if(SPReturnTypeEnum.Int.equals(returnType)){
            try {
                return readInt(fieldName,Integer.parseInt(defaultValue));
            }catch (Exception e){
                return readInt(fieldName,0);
            }
        }

        if(SPReturnTypeEnum.Float.equals(returnType)){
            try {
                return readFloat(fieldName,Float.parseFloat(defaultValue));
            }catch (Exception e){
                return readFloat(fieldName,0);
            }
        }

        if(SPReturnTypeEnum.Long.equals(returnType)){
            try {
                return readLong(fieldName,Long.parseLong(defaultValue));
            }catch (Exception e){
                return readLong(fieldName,0);
            }
        }

        if(SPReturnTypeEnum.String.equals(returnType)){
            try {
                return readString(fieldName,defaultValue);
            }catch (Exception e){
                return readString(fieldName,null);
            }
        }

        if(SPReturnTypeEnum.StringSet.equals(returnType)){
            return readStringSet(fieldName);
        }

        if(SPReturnTypeEnum.Object.equals(returnType)){
            return readObject(fieldName,anno.mapObject());
        }

        if(SPReturnTypeEnum.List.equals(returnType)){
            return readList(fieldName,anno.mapObject());
        }

        return null;
    }

    private SPReturnType getFieldReturnType(String fieldName){
        Class clazz = t.getClass();
        try {
            Field field = clazz.getField(fieldName);
            if(field !=  null){
                SPReturnType typeAnno = field.getAnnotation(SPReturnType.class);
                return typeAnno;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
