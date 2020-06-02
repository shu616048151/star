package com.shu.star.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shu.star.enums.AddressType;
import com.shu.star.enums.Gender;
import com.shu.star.enums.StarType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author shuxibing
 * @Date 2020/5/24 12:44
 * @Uint d9lab_2019
 * @Description:
 */
@Data
public class UserVo {
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
    private String address;
    private String people;
    private String birthday;
    private Integer point;
    private Integer userType;
    private AddressType addressType;
    private Integer status; //是否被禁
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
    private List<String> urls;
    private List<Integer> starTypeList;
}
