package tech.izhen.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.izhen.dao.ItemDOMapper;
import tech.izhen.dao.ItemStockDOMapper;
import tech.izhen.dataobject.ItemDO;
import tech.izhen.dataobject.ItemStockDO;
import tech.izhen.error.BusinessException;
import tech.izhen.error.EmBusinessError;
import tech.izhen.service.ItemService;
import tech.izhen.service.PromoService;
import tech.izhen.service.model.ItemModel;
import tech.izhen.service.model.PromoModel;
import tech.izhen.validator.ValidationResult;
import tech.izhen.validator.ValidatorIml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhen
 * @descrition 商品服务的接口的具体实现
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ValidatorIml validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private PromoService promoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ItemModel creatItem(ItemModel itemModel) throws BusinessException {
        //一、校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }


        //二、模型转换 ItemModel->dataobject
        ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);

        //三、写入数据库
        itemDOMapper.insertSelective(itemDO);

        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = this.convertItemStockDoFromItemModel(itemModel);

        itemStockDOMapper.insertSelective(itemStockDO);

        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();

        List<ItemModel> itemModelList = itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertItemModelFromItemDoItemStock(itemDO,itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null){
            return null;
        }

        // 操作获得库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());

        // 将dataobject转换成model
        ItemModel itemModel = this.convertItemModelFromItemDoItemStock(itemDO,itemStockDO);

        //获取活动商品信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if(promoModel != null && promoModel.getStatus() != 3){
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseStock(Integer item, Integer amount) throws BusinessException {
        int affectedRow = itemStockDOMapper.decreaseStock(item,amount);
        if(affectedRow > 0){
            //更新库存成功
            return true;
        }else {
            //更新库存失败
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDOMapper.increaseSales(itemId,amount);

    }


    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);

        return itemDO;
    }

    private ItemStockDO convertItemStockDoFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());

        return itemStockDO;
    }
    private ItemModel convertItemModelFromItemDoItemStock(ItemDO itemDO, ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();

        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }
}
