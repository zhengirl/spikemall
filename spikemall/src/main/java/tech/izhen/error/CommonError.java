package tech.izhen.error;

/**
 * @author zhen
 * @date  2019年5月9日（星期四）
 * @description 定义通用错误的接口
 */
public interface CommonError {
    /**
     * 解析错误码
     * @return 错误码
     */
    int getErrorCode();

    /**
     * 返回错误消息
     * @return 错误消息     *
     */
    String getErrorMsg();

    /**
     * 设置错误消息
     * @param errMsg 错误消息
     * @return 通用测错误
     */
    CommonError setErrMsg(String errMsg);

}
