package com.cicada.component.kqpay.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 快钱请求数组参数
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 **/
public final class KqPartArray extends KqPartObject {

    private final List<KqPartObject> delegate;

    private KqPartArray() {
        this.delegate = new ArrayList<KqPartObject>(8);
    }

    public static KqPartArray instance() {
        return new KqPartArray();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(String key) {
        return delegate.contains(key);
    }

    public <T> T get(int idx) {
        return (T) delegate.get(idx);
    }

    public KqPartArray add(KqPartObject obj) {
        delegate.add(obj);
        return this;
    }

    @Override
    public KqPartArray remove(String key) {
        delegate.remove(key);
        return this;
    }

    @Override
    public KqPartArray clear() {
        delegate.clear();
        return this;
    }

    @Override
    public Object generate() {
        return delegate;
    }

}