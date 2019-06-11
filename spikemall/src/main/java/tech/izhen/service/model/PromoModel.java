package tech.izhen.service.model;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.math.BigDecimal;


/**
 * @author zhen
 * @date 2019年6月10日
 * @description 用户秒杀交易模型
 */
@Getter
@Setter
public class PromoModel {
    private Integer id;

    /**
     * 秒杀活动状态 1表示未开始 2表示进行中 3表示已结束
     */
    private Integer status;

    /**
     * 秒杀活动名称
     */
    private String promoName;

    /**
     * 秒杀活动开始时间
     */
    private DateTime startDate;

    /**
     * 秒杀活动的结束时间
     */
    private DateTime endDate;

    /**
     * 秒杀活动的适用商品
     *
     */
    private Integer itemId;

    /**
     * 秒杀活动的商品价格
     */
    private BigDecimal promoItemPrice;


}
