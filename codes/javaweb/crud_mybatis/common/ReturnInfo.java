package com.safesoft.domain.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.safesoft.domain.common.ReturnMsgConstants.HANDLE_FAIL;
import static com.safesoft.domain.common.ReturnMsgConstants.HANDLE_SUCCESS;


/**
 * @author Chris.Liu
 * @date 2024/3/20
 * @apiNote 服务层返回信息公共实体
 */
@ToString
public class ReturnInfo<T> {

    /**
     * 是否成功
     */
    @Getter
    @Setter
    private boolean success = true;

    /**
     * 返回数据
     */
    @Getter
    @Setter
    private T data;

    /**
     * 提示信息
     */
    @Getter
    @Setter
    private String msg = HANDLE_SUCCESS;

    public static ReturnInfo<Void> ok() {
        return new ReturnInfo<>();
    }

    public static ReturnInfo<Void> ok(String msg) {
        final ReturnInfo<Void> returnInfo = new ReturnInfo<>();
        returnInfo.setMsg(msg);

        return returnInfo;
    }

    public static <T> ReturnInfo<T> okData(T data) {
        final ReturnInfo<T> returnInfo = new ReturnInfo<>();
        returnInfo.setData(data);

        return returnInfo;
    }

    public static <T> ReturnInfo<T> okData(T data, String msg) {
        final ReturnInfo<T> returnInfo = new ReturnInfo<>();
        returnInfo.setMsg(msg);
        returnInfo.setData(data);

        return returnInfo;
    }

    public static <T> ReturnInfo<T> build(boolean success, T data, String msg) {
        final ReturnInfo<T> returnInfo = new ReturnInfo<>();
        returnInfo.setSuccess(success);
        returnInfo.setMsg(msg);
        returnInfo.setData(data);

        return returnInfo;
    }

    public static ReturnInfo<Void> failure() {
        final ReturnInfo<Void> returnInfo = new ReturnInfo<>();
        returnInfo.setSuccess(false);
        returnInfo.setMsg(HANDLE_FAIL);

        return returnInfo;
    }

    public static ReturnInfo<Void> failure(String msg) {
        final ReturnInfo<Void> returnInfo = new ReturnInfo<>();
        returnInfo.setSuccess(Boolean.FALSE);
        returnInfo.setMsg(msg);

        return returnInfo;
    }

}
