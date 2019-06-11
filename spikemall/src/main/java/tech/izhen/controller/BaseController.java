package tech.izhen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.izhen.error.BusinessException;
import tech.izhen.error.EmBusinessError;
import tech.izhen.response.CommonReturnType;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhen
 * @date 2019年5月9日（星期四)
 * @description 异常基类
 */
public class BaseController {
    /**
     * 解决未被Controller层吸收的Exception
     * @return
     */
    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object HandlerException(HttpServletRequest request, Exception ex) {
        Map<String,Object> responseData = new HashMap<>();
        // 保证程序的健壮性
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseData.put("errCode",businessException.getErrorCode());
            responseData.put("errMsg",businessException.getErrorMsg());
        }else {
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }

        return CommonReturnType.create(responseData,"fail");
    }
}
