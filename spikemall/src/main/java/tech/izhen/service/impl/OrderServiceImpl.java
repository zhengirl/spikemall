package tech.izhen.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.izhen.dao.OrderDOMapper;
import tech.izhen.dao.SequenceDOMapper;
import tech.izhen.dataobject.OrderDO;
import tech.izhen.dataobject.SequenceDO;
import tech.izhen.error.BusinessException;
import tech.izhen.error.EmBusinessError;
import tech.izhen.service.ItemService;
import tech.izhen.service.OrderService;
import tech.izhen.service.UserService;
import tech.izhen.service.model.ItemModel;
import tech.izhen.service.model.OrderModel;
import tech.izhen.service.model.UserModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhen
 * @date 2019年5月30日
 * @description Service层的实现
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDOMapper orderDOMapper;
    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderModel creatOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //1.校验下单状态，检查下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不合法");
        }
        if(amount <= 0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }


        //2.落单减库存，支付减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号，即订单号
        orderModel.setId(generateOrderNO());
        OrderDO orderDO = this.convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //商品销量增加
        itemService.increaseSales(itemId,amount);

        //4.返回前端
        return orderModel;
    }

    /**
     * 订单号有16位，前八位为时间信息，年月日
     * 中间6位为自增序列
     * 即使事务失败，此事务也不应该重复被使用
     * 最后两位为分库分表位
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    protected String generateOrderNO(){
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        int sequence=0;

        //获取当前的sequence
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");

        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        //int转String的方式s
       String sequenceStr = String.valueOf(sequence);
        for(int i=0;i<6-sequenceStr.length();i++){
            stringBuilder.append("0");
        }
        stringBuilder.append(sequenceStr);

        //暂时写死
        stringBuilder.append("00");

        return new String(stringBuilder);

    }

    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        return orderDO;
    }
}
