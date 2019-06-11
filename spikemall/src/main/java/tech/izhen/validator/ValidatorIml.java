package tech.izhen.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * @author zhen
 * @date 2019年5月17日
 * @description 合法性校验的实现
 */
@Component
public class ValidatorIml implements InitializingBean {
    private Validator validator;

    /**
     * @return
     * 实现校验方法并返回校验结果
     */
     public ValidationResult validate(Object bean){
         ValidationResult res = new ValidationResult();
         Set<ConstraintViolation<Object>> constraintViolationSet  = validator.validate(bean);
         if(constraintViolationSet.size() >0){
             //有错误
             res.setHasErrors(true);
             constraintViolationSet.forEach(constraintViolation->{
                 String errMsg = constraintViolation.getMessage();
                 String propertyName = constraintViolation.getPropertyPath().toString();
                 res.getErrMsgMap().put(propertyName,errMsg);
             });
         }

         return res;

     }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂化的实例方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}
