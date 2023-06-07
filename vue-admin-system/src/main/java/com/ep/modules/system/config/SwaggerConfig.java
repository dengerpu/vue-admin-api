package com.ep.modules.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/***
 * @author dep
 * @version 1.0
 * @date 2023-06-06 14:58
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Autowired
    private ApplicationContext context;

    @Bean //配置docket以配置Swagger具体参数
    public Docket docket() {

        // 判断当前是否处于该环境
        boolean flag;
        if(context.getEnvironment().getActiveProfiles()[0].equals("dev")){
            flag = true;
        }else {
            flag = false;
        }
        return new Docket(DocumentationType.OAS_30)  // 指定swagger3.0版本
                .apiInfo(apiInfo())
                .enable(flag)
                .select() // 通过.select()方法去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.ep.modules.system.controller"))
                .build();
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("爱写bug的小邓程序员", "http://xxx.xxx.com/联系人访问链接", "1649743146@qq.com");
        return new ApiInfo(
                "Vue-admin", // 标题
                "Vue-admin接口说明", // 描述
                "v1.0", // 版本
                "http://terms.service.url/组织链接", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }
}
