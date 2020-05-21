package com.shu.star.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author shuxibing
 * @Date 2020/5/21 8:42
 * @Uint d9lab_2019
 * @Description:
 */
@Data
public class Message {
    private Integer id;
    private Integer sendUserId;
    private Integer receiveUserId;
    private String content;
    private Date createTime;
    private Date updateTime;
    private Integer isDeletd;

    public Message() {
    }

    public Message(Integer sendUserId, Integer receiveUserId, String content) {
        this.sendUserId = sendUserId;
        this.receiveUserId = receiveUserId;
        this.content = content;
    }
}
