package com.shu.star.vo;

import com.shu.star.enums.MessageType;
import lombok.Data;

import java.util.Date;

/**
 * @Author shuxibing
 * @Date 2020/5/24 14:13
 * @Uint d9lab_2019
 * @Description:
 */
@Data
public class MessageVo {
    private Integer id;
    private Integer sendUserId;
    private Integer receiveUserId;
    private String content;
    private MessageType messageType;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
}
