package tech.izhen.response;

import lombok.Data;

/**
 * 统一返回格式
 * @author zhen
 * @date 2019年5月9日
 */
@Data
public class CommonReturnType {
    /**
     * 表明对应请求的返回处理结果 "success" or "fail"
     */
    private String status;
    private Object data;

    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result, String status){
            CommonReturnType type = new CommonReturnType();
            type.setStatus(status);
            type.setData(result);

        return type;
    }
}
