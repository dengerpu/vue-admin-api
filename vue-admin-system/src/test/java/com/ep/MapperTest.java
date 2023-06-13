package com.ep;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.ep.modules.system.entity.User;
import com.ep.modules.system.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import java.util.Map;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-10 21:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id", "username", "password");
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testUserByUsername() {
        String username = "admin";
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(lambdaQueryWrapper);

        System.out.println(user);
    }

    @Test
    public void testSQLSession() {
        String sql = "select * from sys_user";
        List<Map<String, Object>> maps = SqlRunner.db().selectList(sql);
        System.out.println(maps);
    }
}
