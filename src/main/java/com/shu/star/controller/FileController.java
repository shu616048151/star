package com.shu.star.controller;

import com.shu.star.mapper.UserMapper;
import com.shu.star.util.FileUtil;
import com.shu.star.vo.ResponseMap;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

/**
 * @Author shuxibing
 * @Date 2020/5/24 13:56
 * @Uint d9lab_2019
 * @Description:
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "文件上传", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public Map uploadFile(MultipartFile file) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        String url = FileUtil.upload("1", file);
        userMapper.addFile(url,new Date());
        return map.putValue(url,"新增成功");
    }
}
