package tech.izhen.dao;

import org.apache.ibatis.annotations.Param;
import tech.izhen.dataobject.ItemDO;

import java.util.List;

public interface ItemDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon May 20 17:11:02 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon May 20 17:11:02 CST 2019
     */
    int insert(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon May 20 17:11:02 CST 2019
     */
    int insertSelective(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon May 20 17:11:02 CST 2019
     */
    ItemDO selectByPrimaryKey(Integer id);

    /**
     * 返回所有的商品列表，按照销量倒序排序
     * @return
     */
    List<ItemDO> listItem();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon May 20 17:11:02 CST 2019
     */
    int updateByPrimaryKeySelective(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon May 20 17:11:02 CST 2019
     */
    int updateByPrimaryKey(ItemDO record);

    /**
     * @param id
     * @param amount
     * @return
     */
    int increaseSales(@Param("id")Integer id,@Param("amount") Integer amount);
}