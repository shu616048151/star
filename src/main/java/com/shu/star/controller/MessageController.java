package com.shu.star.controller;

import com.shu.star.enums.MessageType;
import com.shu.star.mapper.MessageMapper;
import com.shu.star.vo.MessageVo;
import com.shu.star.vo.ParamsMap;
import com.shu.star.vo.ResponseMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author shuxibing
 * @Date 2020/5/24 13:50
 * @Uint d9lab_2019
 * @Description:
 */
@Api(tags = {"消息"}, description = "消息管理", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageMapper messageMapper;

    @ApiOperation(value = "发送信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sendUserId", value = "发送者", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "receiveUserId", value = "接受者", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "messageType", value = "消息类型", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "content", value = "内容", paramType = "form", dataType = "string"),
    })
    @RequestMapping(value = "/sendMessage")
    public Map sendMessage(int sendUserId, int receiveUserId,String content, MessageType messageType) throws Exception {
        ResponseMap responseMap = ResponseMap.getInstance();

        ParamsMap paramsMap = ParamsMap.getPageInstance();
        paramsMap.put("sendUserId",sendUserId);
        paramsMap.put("receiveUserId",receiveUserId);
        paramsMap.put("content",content);
        paramsMap.put("messageType",messageType.ordinal());
        paramsMap.put("createTime",new Date());
        paramsMap.put("updateTime",new Date());
        paramsMap.put("isDeleted",0);
        messageMapper.addMessage(paramsMap);
        return responseMap.putSuccess("发送成功");
    }

    @ApiOperation(value = "获取两人对话私信", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sendUserId", value = "发送者", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "receiveUserId", value = "接受者", paramType = "form", dataType = "int"),
    })
    @RequestMapping(value = "/getPrivateMessage")
    public Map getPrivateMessage(int sendUserId, int receiveUserId) throws Exception {
        ResponseMap responseMap = ResponseMap.getInstance();

        ParamsMap paramsMap = ParamsMap.getPageInstance();
        paramsMap.put("sendUserId",sendUserId);
        paramsMap.put("receiveUserId",receiveUserId);
        paramsMap.put("messageType",MessageType.私信.ordinal());
        paramsMap.put("isDeleted",0);
        List<MessageVo> messageVoList = messageMapper.getMessage(paramsMap);
        return responseMap.putList(messageVoList,"获取成功");
    }


}
