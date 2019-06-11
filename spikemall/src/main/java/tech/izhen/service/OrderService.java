package tech.izhen.service;

import tech.izhen.error.BusinessException;
import tech.izhen.service.model.OrderModel;

/**
 * @author zhen
 * @date 2019年5月30日
 * @description 用户下单的核心service层
 */
public interface OrderService {
    OrderModel creatOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}
