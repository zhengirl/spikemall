package tech.izhen.controller;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import tech.izhen.controller.viewobject.UserVO;
import tech.izhen.error.BusinessException;
import tech.izhen.error.EmBusinessError;
import tech.izhen.response.CommonReturnType;
import tech.izhen.service.UserService;
import tech.izhen.service.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author zhen
 * @date 2019年5月9日（星期四）
 * @description 与客户端之间的直接交互，最后一道关卡
 */
@Slf4j
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * 用户注册接口
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType resgister(@RequestParam(name = "telphone")String telphone,
                                      @RequestParam(name = "otpCode")String otpCode,
                                      @RequestParam(name = "name")String name,
                                      @RequestParam(name = "gender")Byte gender,
                                      @RequestParam(name = "age")Integer age,
                                      @RequestParam(name = "password")String password) throws BusinessException {
        //第一步，验证手机号是否和对应的otpCode相对应
        String inSessionOtpCode = (String)this.httpServletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }

        //第二步，对应的用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("by-phone");
        try {
            userModel.setEncrptPassword(this.encodeByMD5(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        userService.register(userModel);

        return CommonReturnType.create(null);
    }

    public String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64 = new BASE64Encoder();

        //加密字符串
        String newstr = base64.encode(md5.digest(str.getBytes("UTF-8")));
        return newstr;

    }

    /**
     * // 用户获取otp短信的接口
     * @return
     */
    @RequestMapping(value = "/getOtp", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone")String telphone){
        // 安装一定的规则生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //将otp验证码将用户的电话号码关联起来
        //使用HTTPSession筛选的方式绑定其手机号与otpCode
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将otp验证码通过短信通道发给用户 省略
        log.debug("telphone: "+telphone+"& otpCode: "+otpCode);

        return CommonReturnType.create(null);

    }

    /**
     * 用户登录的接口
     * @param telphone
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telphone")String telphone,
                                  @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if(org.apache.commons.lang3.StringUtils.isEmpty(telphone)||
                StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //用户登录服务，校验用户登录是否合法

        UserModel userModel = userService.validateLogin(telphone,this.encodeByMD5(password));

        // 将登录凭证加入到用户登录成功的session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(null);

    }


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIT);
        }

        //将核心领域模型用户对象转化为可供前端使用的viewObject
        UserVO userVO = convertFromModer(userModel);

        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModer(UserModel userModel){
        if(userModel == null){
            return  null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);

        return userVO;
    }



}
