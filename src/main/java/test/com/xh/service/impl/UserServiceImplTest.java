package test.com.xh.service.impl;

import com.SpringbootApplication;
import com.xh.dto.UserDto;
import com.xh.service.impl.UserTestServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/** 
* UserServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 15, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SpringbootApplication.class)
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class UserServiceImplTest {

    @Autowired
    private UserTestServiceImpl userTestService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: queryUser(UserDto userDto)
    *
    */
    @Test
    public void testQueryUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("李");
        userTestService.queryUser(userDto);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("3");
        list.add("2");
        //value 为对象
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user",userDto);
        UserDto  userDtos=  (UserDto)valueOperations.get("user");
        System.out.println(userDtos);
        //value 为list
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("left", list);
        listOperations.rightPush("right", list);
        List leftPop = (List)listOperations.leftPop("left");
        System.out.println(leftPop);
        List rightPop = (List)listOperations.rightPop("right");
        System.out.println(rightPop);


    }
}
