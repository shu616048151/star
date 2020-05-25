package com.shu.star.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @Author shuxibing
 * @Date 2020/5/20 23:21
 * @Uint d9lab_2019
 * @Description:
 */
@Data
public class User {
    private Integer id;
    @JsonIgnore
    private String userName;
    @JsonIgnore
    private String password;
    private String name;
    private Integer gender;
    private Integer age;
    private Integer height;
    private Double weight;
    private String address;
    private Integer point;
    private Integer userType;
    private Integer addressType;
    private Integer status; //是否被禁
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;

    public User() {
    }

    public User(String userName, String password, String name,Integer gender, Integer age, Integer height, Double weight, String address) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.gender=gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.address = address;
    }
}