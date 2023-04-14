package com.ep.utils;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-11 22:15
 */

import lombok.Data;

/***
 * 用于封装后端给前端返回的信息
 */
@Data
public class ResultInfo {

    private int code;           //状态码

    private String message;      //错误信息

    private Object data;         //后端返回结果数据对象

    private long timestamp ;

    public ResultInfo() {
        this.timestamp = System.currentTimeMillis();
    }

    /***
     * 成功，默认状态码为200
     * @param data 返回数据
     * @return
     */
    public static  ResultInfo success(Object data) {
        ResultInfo info = new ResultInfo();
        info.setCode(ReturnCode.S200.getCode());
        info.setMessage(ReturnCode.S200.getMessage());
        info.setData(data);
        return info;
    }
    /***
     * 成功，可以自定义状态码和成功信息
     * @param code  状态码
     * @param message 自定义信息
     * @param data  数据
     * @return
     */
    public static ResultInfo success(int code, String message, Object data) {
        ResultInfo info = new ResultInfo();
        info.setCode(code);
        info.setMessage(message);
        info.setData(data);
        return info;
    }

    /***
     * 失败
     * @param code  状态码
     * @param message  自定义信息
     * @return
     */
    public static ResultInfo fail(int code, String message) {
        ResultInfo info = new ResultInfo();
        info.setCode(code);
        info.setMessage(message);
        return info;
    }
}
