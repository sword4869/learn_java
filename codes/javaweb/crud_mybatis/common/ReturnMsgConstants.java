package com.safesoft.domain.common;

/**
 * @author Chris.Liu
 * @date 2024/4/8
 * @apiNote 业务返回信息常量
 */
public final class ReturnMsgConstants {
    private ReturnMsgConstants() {
    }

    /**
     * 信息分隔符
     */
    public static final String MSG_SPLIT_BAR = "-";

    /**
     * 通用操作信息提示
     */
    public static final String HANDLE_SUCCESS = "操作成功";
    public static final String HANDLE_FAIL = "操作失败";

    /**
     * 萤石云相关
     */
    public static final String RETURN_MSG_EZVIZ_ACCESS_TOKEN_FAILURE = "萤石云accessToken获取失败";
    public static final String RETURN_MSG_EZVIZ_PLAY_ADDRESS_FAILURE = "获取萤石云监控播放地址失败";

}
