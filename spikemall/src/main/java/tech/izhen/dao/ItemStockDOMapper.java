package tech.izhen.dao;

import org.apache.ibatis.annotations.Param;
import tech.izhen.dataobject.ItemStockDO;

public interface ItemStockDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon May 20 17:10:16 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon May 20 17:10:16 CST 2019
     */
    int insert(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon May 20 17:10:16 CST 2019
     */
    int insertSelective(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon May 20 17:10:16 CST 2019
     */
    ItemStockDO selectByPrimaryKey(Integer id);
    ItemStockDO selectByItemId(Integer itemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon May 20 17:10:16 CST 2019
     */
    int updateByPrimaryKeySelective(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon May 20 17:10:16 CST 2019
     */
    int updateByPrimaryKey(ItemStockDO record);

    /**
     * 支付减库存
     * @param itemId
     * @param amount
     * @return
     */
    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}