package tech.izhen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.izhen.error.BusinessException;
import tech.izhen.error.EmBusinessError;
import tech.izhen.response.CommonReturnType;
import tech.izhen.service.OrderService;
import tech.izhen.service.model.OrderModel;
import tech.izhen.service.model.UserModel;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhen
 * @date 2019年5月30日
 * @description 用户下单请求
 */
@Slf4j
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    /**
     * 封装下单请求
     * @param itemId
     * @param amount
     * @return
     */
    @RequestMapping(value = "/createorder", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "amount")Integer amount) throws BusinessException {
        Boolean isLogin =(Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录，不能下单");
        }

        UserModel userModel =(UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");


        //获取用户的登录信息
        OrderModel orderModel = orderService.creatOrder(userModel.getId() ,itemId,amount);

        return CommonReturnType.create(null);

    }
}
