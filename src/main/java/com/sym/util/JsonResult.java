package com.sym.util;

import lombok.Data;

import java.io.Serializable;

/**
 * web后端返回给前端的Json实体对象
 * <p>
 * Created by shenym on 2019/11/21.
 */
@Data
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 2096892482295250355L;

    /**
     * {@code 0 OK}
     */
    public final static String CODE_NORMAL = "0";

    /**
     * {@code error}
     */
    public final static String CODE_ERROR = "error";

    /**
     * {@code 1 REDIRECT}
     * 重定向（ajax请求重定向）
     */
    public final static String CODE_REDIRECT = "1";

    private boolean success = true;
    private String code = CODE_NORMAL;
    private String msg = "";
    private Object data = "";

    public static JsonResult success(Object data) {
        JsonResult result = new JsonResult();
        result.setData(data);
        return result;
    }

    public static JsonResult success() {
        return new JsonResult();
    }

    public static JsonResult success(String description) {
        JsonResult result = new JsonResult();
        result.setMsg(description);
        return result;
    }

    public static JsonResult failed(String message) {
        JsonResult result = new JsonResult();
        result.setCode(CODE_ERROR);
        result.setSuccess(false);
        result.setMsg(message);
        return result;
    }

    public static JsonResult failed(String message, Throwable e) {
        JsonResult result = new JsonResult();
        result.setCode(e.getClass().getName());
        result.setSuccess(false);
        result.setMsg(message);
        result.setData(e);
        return result;
    }

    /**
     * 返回json，重定向
     */
    public static JsonResult redirect(String json) {
        JsonResult result = new JsonResult();
        result.setCode(CODE_REDIRECT);
        result.setData(json);
        result.setSuccess(true);
        return result;
    }
}

