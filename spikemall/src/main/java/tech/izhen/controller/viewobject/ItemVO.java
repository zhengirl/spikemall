package tech.izhen.controller.viewobject;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @author zhen
 * @description 用于前端的用户界面展示
 */
@Getter
@Setter
public class ItemVO {
    private Integer id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品价格
     * float和double类型尤其不适合于货币计算，它们没有提供完全精确的结果
     */
    private BigDecimal price;

    /**
     * 商品的库存
     */
    private Integer stock;

    /**
     * 商品的描述
     */
    private String description;

    /**
     * 商品的销量
     */
    private Integer sales;

    /**
     * 商品描述图片的url
     */
    private String imgUrl;

    /**
     * 记录商品是否在秒杀活动中，以及对应的状态 0：没有秒杀活动 1：秒杀活动未开始 2：秒杀活动进行中
     */
    private Integer promoStatus;

    /**
     * 秒杀活动价格
     */
    private BigDecimal promoPrice;

    /**
     * 秒杀活动Id
     */
    private Integer promoId;

    /**
     * 秒杀活动开始时间
     */
    private String startDate;



}
