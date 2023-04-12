package com.ep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-10 20:41
 */
@SpringBootApplication
@MapperScan("com.ep.mapper")
public class VueAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(VueAdminApplication.class, args);
    }
}
