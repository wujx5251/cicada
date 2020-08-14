package com.cicada.storage.bean;

import java.io.Serializable;

/**
 * 统一返回值
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public class ResultVo<T> implements Serializable {

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;

    public static final ResultVo<String> SUCCESS = new ResultVo<String>(null);
    public static final ResultVo<String> FAIL = new ResultVo<String>(FAIL_CODE, null);

    private int code;
    private String msg;
    private T content;

    public ResultVo() {
    }

    public ResultVo(T content) {
        this(SUCCESS_CODE, content, null);
    }

    public ResultVo(int code, String msg) {
        this(code, null, msg);
    }

    public ResultVo(int code, T content, String msg) {
        this.code = code;
        this.content = content;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getContent() {
        return content;
    }
}
