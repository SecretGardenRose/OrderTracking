package com.south.common.exception;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 *
 *
 */
public enum BizCodeEnume {
    /**
     * 系统未知异常
     */
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    /**
     * 参数校验错误
     */
    VALID_EXCEPTION(10001, "参数格式校验失败"),

    TO_MANY_REQUEST(10002, "请求流量过大，请稍后再试"),
    SMS_CODE_EXCEPTION(10002, "验证码获取频率太高，请稍后再试"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),
    USER_EXIST_EXCEPTION(15001, "存在相同的用户"),
    PHONE_EXIST_EXCEPTION(15002, "存在相同的手机号"),
    NO_STOCK_EXCEPTION(21000, "商品库存不足"),
    LOGIN_ACCOUNT_PASSWORD_EXCEPTION(15003, "账号或密码错误"),
    SECKILL_EXCEPTION(30001, "访问限流"),


    /**
     * 订单模块
     */
    INSERT_DATA_ERROR(50001, "数据添加失败"),
    IMPORT_DATA_ERROR(50002, "订单数据导入异常"),
    MKDIR_FAILED(50003,"文件夹创建失败"),
    UPLOAD_FAILED(50004,"图片上传失败"),
    UPDATE_DATA_ERROR(50005,"数据更新失败"),
    ;

    private final int code;
    private final String msg;

    BizCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
