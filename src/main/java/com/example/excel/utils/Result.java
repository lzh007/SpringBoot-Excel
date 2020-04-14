package com.example.excel.utils;

import java.io.Serializable;

public class Result<T> implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = -503065825551003534L;
    private boolean success = false;
	private T data = null;
	private String msg = "";
	private String code = "500";

	public static <T> Result<T> success(T data) {
	    Result<T> r = new Result<T>();
	    r.setData(data);
	    r.setSuccess(true);
	    r.setCode("200");
	    r.setMsg("success");
	    return r;
	}

    public static <T> Result<T> success(String message, T data) {
        return new Result<T>().setSuccess(true).setCode("200").setMsg(message).setData(data);
    }

	public static <T> Result<T> fail(String code, String msg) {
	    Result<T> r = new Result<T>();
	    r.setSuccess(false);
	    r.setCode(code);
	    r.setMsg(msg);
	    return r;
	}

	public static <T> Result<T> fail(String msg) {
	    Result<T> r = new Result<T>();
	    r.setSuccess(false);
	    r.setCode("500");
	    r.setMsg(msg);
	    return r;
	}

    public static <T> Result<T> fail(String message, T data) {
        return new Result<T>().setSuccess(false).setCode("500").setMsg(message).setData(data);
    }

	public boolean isSuccess() {
		return success;
	}

	public Result<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public T getData() {
	    return data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String getMsg() {
		return msg;
	}

    public Result<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}
    public String getCode() {
        return code;
    }
    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }
}
