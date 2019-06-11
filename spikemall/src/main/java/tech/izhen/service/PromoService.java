package tech.izhen.service;

import tech.izhen.service.model.PromoModel;

/**
 * @author zhen
 * @date 2019年6月10日
 * @description 秒杀活动的service层
 */
public interface PromoService {
    /**
     * 根据商品Id查询是否有秒杀活动信息
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);

}
