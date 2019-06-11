package tech.izhen.validator;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhen
 * @date 2019年5月17日
 * @description 验证用户的各项参数是否合法
 */
@Getter
@Setter
public class ValidationResult {

    /**
     * 校验结果是否有错
     */
    private boolean hasErrors=false;

    /**
     * 存放错误信息的map
     */
    private Map<String,String> errMsgMap = new HashMap<>();

    public String getErrMsg(){
        return StringUtils.join(errMsgMap.values().toArray(),',');
    }

}
