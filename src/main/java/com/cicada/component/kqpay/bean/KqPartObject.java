package com.cicada.component.kqpay.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 快钱请求kv参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public class KqPartObject {

    private final Map<String, Object> delegate;

    protected KqPartObject() {
        this.delegate = new HashMap<String, Object>(16);
    }

    public static KqPartObject instance() {
        return new KqPartObject();
    }

    public int size() {
        return delegate.size();
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean containsKey(String key) {
        return delegate.containsKey(key);
    }

    public <T> T get(String key) {
        return (T) delegate.get(key);
    }

    public KqPartObject put(String key, Object value) {
        delegate.put(key, value);
        return this;
    }

    public KqPartObject remove(String key) {
        delegate.remove(key);
        return this;
    }

    public KqPartObject clear() {
        delegate.clear();
        return this;
    }

    public Object generate() {
        return delegate;
    }

}