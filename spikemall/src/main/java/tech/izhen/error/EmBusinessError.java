package tech.izhen.error;

/**
 * @author zhen
 * @date 2019年5月9日（星期四） (GMT-7)
 * @Description 枚举业务错误类，实现了通用错误接口
 */
public enum EmBusinessError implements CommonError{

    /**
     * 通用错误类型 1000开头
     */
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    /**
     * 20000为用户信息相关错误定义
     */
    USER_NOT_EXIT(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户名或密码不正确"),
    USER_NOT_LOGIN(20003,"用户未登录"),
    /**
     * 30000开头为交易信息错误
     */
    STOCK_NOT_ENOUGH(30001,"库存不足");

    private int errorCode;
    private String errMsg;

    EmBusinessError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        // 提供定制化服务
        return this;
    }}
