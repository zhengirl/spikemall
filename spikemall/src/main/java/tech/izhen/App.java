package tech.izhen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.izhen.dao.UserDOMapper;
import tech.izhen.dataobject.UserDO;

/**
 * Hello world!
 *
 * @author zhen
 */
@SpringBootApplication(scanBasePackages = {"tech.izhen"})
@RestController
@MapperScan("tech.izhen.dao")
public class App 
{
    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String home(){
        UserDO userDo = userDOMapper.selectByPrimaryKey(1);
        if(userDo == null){
            return "用户对象不存在";
        } else{
            return userDo.getName();
        }


    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
