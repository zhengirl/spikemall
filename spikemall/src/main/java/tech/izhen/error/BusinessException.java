package tech.izhen.error;

/**
 * @author zhen
 * @date 2019年5月9日（星期四) *
 * @description 包装器业务异常类实现
 */
public class BusinessException extends Exception implements CommonError {

    /**
     * 强关联前面的类
     */
    private CommonError commonError;

    /**
     * 直接接收EmBusinessError用于业务类型的传参
     * @param commonError
     */
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;

    }

    /**
     * 接受自定义的errMsg用于构造业务异常：在用户参数不合法时自定义告诉用户
     * @param commonError
     * @param errMsg
     */
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        commonError.setErrMsg(errMsg);
    }
    @Override
    public int getErrorCode() {
        return commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        commonError.setErrMsg(errMsg);
        return commonError;
    }
}
