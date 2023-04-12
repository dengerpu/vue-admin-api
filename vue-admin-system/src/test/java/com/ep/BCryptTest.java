package com.ep;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-11 22:04
 */
@SpringBootTest
public class BCryptTest {
    @Test
    public void testBCrypt() {
        // 创建密码解析器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 对密码进行加密
        String password = passwordEncoder.encode("123456");
        // 打印加密后的密码
        System.out.println(password);

        // 判断原字符和加密后的字符是否匹配
        boolean isTrue = passwordEncoder.matches("123456", password);

        System.out.println(isTrue);
    }
}
