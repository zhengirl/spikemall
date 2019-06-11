package tech.izhen.service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author zhen
 * @date 2019年5月30日
 * @description 用户下单的交易模型
 */
@Getter
@Setter
public class OrderModel {
    /**
     * 2018102100012828
     * 交易号每一位都有每一位的含义
     */
    private String id;

    /**
     * 购买的用户id
     */
    private Integer userId;

    /**
     * 购买的商品id
     */
    private Integer itemId;

    /**
     * 若非空，则表示是以秒杀商品的方式下单
     */
    private Integer promoId;


    /**
     *  购买商品的单价，若promoId非空，则itemPrice为秒杀价格
     */
    private BigDecimal itemPrice;

    /**
     * 购买的数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal orderPrice;

}
