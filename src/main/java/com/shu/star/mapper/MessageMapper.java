package com.shu.star.mapper;

import com.shu.star.model.User;
import com.shu.star.vo.MessageVo;
import com.shu.star.vo.ParamsMap;
import com.shu.star.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author shuxibing
 * @Date 2020/5/20 23:21
 * @Uint d9lab_2019
 * @Description:
 */
public interface MessageMapper {


    void addMessage(ParamsMap paramsMap)throws Exception;

    List<MessageVo> getMessage(ParamsMap paramsMap);
}
