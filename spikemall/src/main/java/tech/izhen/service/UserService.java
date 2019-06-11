package tech.izhen.service;

import tech.izhen.error.BusinessException;
import tech.izhen.service.model.UserModel;

/**
 * @author zhen
 * @date 2019年5月9日
 * @description service层的接口
 */
public interface UserService {

    /**
     * 通过用户ID获取用户对象的方法
     * @param id
     * @return UserModel
     */
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;

    /**用户有效登录验证
     * @param telphone
     * @param encrptPassword 用户加密后的密码
     */
    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;
}
