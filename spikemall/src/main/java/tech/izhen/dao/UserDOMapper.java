package tech.izhen.dao;

import tech.izhen.dataobject.UserDO;

public interface UserDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Wed May 08 15:51:49 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Wed May 08 15:51:49 CST 2019
     */
    int insert(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Wed May 08 15:51:49 CST 2019
     */
    int insertSelective(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Wed May 08 15:51:49 CST 2019
     */
    UserDO selectByPrimaryKey(Integer id);

    /**
     * 通过手机号码选择用户
     * @param telphone
     * @return
     */
    UserDO selectByTelphone(String telphone);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Wed May 08 15:51:49 CST 2019
     */
    int updateByPrimaryKeySelective(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Wed May 08 15:51:49 CST 2019
     */
    int updateByPrimaryKey(UserDO record);
}