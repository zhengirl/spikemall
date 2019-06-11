package tech.izhen.service;

import tech.izhen.error.BusinessException;
import tech.izhen.service.model.ItemModel;

import java.util.List;

/**
 * @author zhen
 * @date 2019年5月20日
 * @description 商品模型的服务层
 */
public interface ItemService {
    /**
     * 创建商品
     */
    ItemModel creatItem(ItemModel itemModel) throws BusinessException;

    /**
     * 商品列表浏览
     */
    List<ItemModel> listItem();

    /**
     * 商品详情浏览
     */
    ItemModel getItemById(Integer id);

    /**
     * 库存扣减
     * @param item
     * @return
     */
    boolean decreaseStock(Integer item,Integer amount) throws BusinessException;


    /**
     * @param itemId
     * @param amount
     * @throws BusinessException
     */
    void increaseSales(Integer itemId,Integer amount)throws BusinessException;
}
