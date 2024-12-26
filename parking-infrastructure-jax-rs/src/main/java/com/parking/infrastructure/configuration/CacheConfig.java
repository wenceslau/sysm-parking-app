package com.parking.infrastructure.configuration;

import jakarta.enterprise.context.SessionScoped;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SessionScoped
public class CacheConfig implements Serializable {

    private final Map<String, Object> cache = new HashMap<>();

    public void addCache(String key, Object value, Object... params) {
        var keyValue = buildKey(key, params);
        cache.remove(keyValue);
        cache.put(keyValue, value);
    }

    public void removeCache(String key, Object... params) {
        var keyValue = buildKey(key, params);
        cache.remove(keyValue);
    }

    public Object getCache(String key, Object... params) {
        var keyValue = buildKey(key, params);
        return cache.get(keyValue);
    }

    private String buildKey(String key, Object... params) {
        return key + Arrays.toString(params).replace(" ", "");
    }

}
