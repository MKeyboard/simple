package com.cmschina.elasticsearch.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author grg
 * @email dengkb@gmail.com
 * @date 2020-11-09
 */
public class BaseData extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public BaseData() {
        put("code", 200);
        put("msg", "success");
        put("result", new HashMap<>());
    }

    public static BaseData error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static BaseData error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static BaseData error(int code, String msg) {
        BaseData r = new BaseData();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static BaseData ok(String msg) {
        BaseData r = new BaseData();
        r.put("msg", msg);
        return r;
    }

    public static BaseData ok(String msg, Object data) {
        BaseData r = new BaseData();
        r.put("msg", msg);
        r.put("result", data);
        return r;
    }

    public static BaseData ok(Object data) {
        BaseData r = new BaseData();
        r.put("result", data);
        return r;
    }

    public static BaseData ok() {
        return new BaseData();
    }

    @Override
    public BaseData put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public BaseData add(String key, Object value) {
        Map map = (Map) super.get("result");
        map.put(key, value);
        return this;
    }
}