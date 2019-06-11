package tech.izhen.controller.viewobject;

import lombok.Data;

/**
 * @author zhen
 * @date 2019年5月9日（星期四)
 * @description 传送给UI展示的类
 */
@Data
public class UserVO {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telphone;

}
