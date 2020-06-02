package com.shu.star.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shu.star.enums.AddressType;
import com.shu.star.enums.Gender;
import com.shu.star.enums.UserType;
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
    private Gender gender;
    private Integer age;
    private Integer height;
    private Double weight;
    //民族
    private String people;
    private String address;
    private Integer point;
    //用户类型
    private UserType userType;
    //明星地区类型
    private AddressType addressType;
    private Integer status; //是否被禁
    private String birthday;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;

    public User() {
    }

    public User(String userName, String password, String name,Gender gender, Integer age, Integer height, Double weight, String address) {
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
