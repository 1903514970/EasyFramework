package com.dj.frameworklib.utils.shared_preference;

import java.util.List;
import java.util.Set;

/**
 * Created by dengjun on 2019/3/27.
 */

public interface ISharedPreference<T extends ISharedPreferenceKeysEntity> {

    public T getKeysObject();

    public boolean writeString(String key, String value);
    public boolean writeLong(String key, long value);
    public boolean writeInt(String key, int value);
    public boolean writeFloat(String key, float value);
    public boolean writeBoolean(String key, boolean value);
    public boolean writeStringSet(String key, Set<String> value);
    public boolean writeObject(String key, Object object);
    public boolean writeList(String key, List arrays);

    public String readString(String key, String defaultValue);
    public long readLong(String key, long defaultValue);
    public int readInt(String key, int defaultValue);
    public float readFloat(String key, float defaultValue);
    public boolean readBoolean(String key, boolean defaultValue);
    public Set<String> readStringSet(String key);
    public <S> S readObject(String key, Class<S> clazz);
    public <S> List<S> readList(String key, Class<S> clazz);

    public boolean write(String fieldName, Object value);
    public Object read(String fieldName);

}
