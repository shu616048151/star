package com.shu.star;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author shuxibing
 * @Date 2020/5/20 22:59
 * @Uint d9lab_2019
 * @Description:
 */
@SpringBootApplication
@EnableSwagger2//必须存在
@MapperScan(value = "com.shu.star.mapper")
public class App {
    public static void main(String[] args){
        SpringApplication.run(App.class,args);
    }
}
