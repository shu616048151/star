package com.shu.star.controller;

import cn.hutool.crypto.SecureUtil;
import com.shu.star.enums.AddressType;
import com.shu.star.enums.StarType;
import com.shu.star.enums.UserType;
import com.shu.star.mapper.UserMapper;
import com.shu.star.model.User;
import com.shu.star.util.FileUtil;
import com.shu.star.vo.ParamsMap;
import com.shu.star.vo.ResponseMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.misc.FieldUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author shuxibing
 * @Date 2020/5/20 23:31
 * @Uint d9lab_2019
 * @Description:
 */
@Api(tags = {"用户"}, description = "用户管理", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "新增用户", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ResponseBody
    public void addUser(User user){
        user.setPassword(SecureUtil.md5(user.getPassword()));
        userMapper.addUser(user);
    }

    @ApiOperation(value = "新增明星", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addStar",method = RequestMethod.POST)
    public Map addStar(String userName, String passwrod, String name, Integer gender, Integer age, Integer height, Double weight, String address, AddressType addressTypeNew, StarType[] starTypes, MultipartFile file) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        User user=new User(userName,SecureUtil.md5(passwrod),name,gender,age,height,weight,address);
        user.setPoint(0);
        user.setAddressType(addressTypeNew.ordinal());
        user.setUserType(UserType.明星.ordinal());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDeleted(0);
        user.setStatus(0);
        Integer flag = userMapper.addUser(user);
        if (flag > 0){
           for (StarType starType:starTypes){
                userMapper.addStarItem(user.getId(),starType.ordinal());
           }
           //上传文件
            String url = FileUtil.upload("1", file);
           userMapper.addUserFile(user.getId(),url);
            return map.putSuccess("新增成功");
        }
        return map.putFailure("新增失败",-1);
    }

    @ApiOperation(value = "用户登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "userName", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "password", paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map login(String userName, String password){
        password=SecureUtil.md5(password);
        ResponseMap instance = ResponseMap.getInstance();
        List<User> userList = userMapper.login(userName, password);
        if ( userList == null || userList.isEmpty()){
            return instance.putFailure("登录失败",-1);
        }
        return instance.putValue(userList.get(0),"登录成功");
    }


    @ApiOperation(value = "获取用户信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "userid", paramType = "form", dataType = "int")
    })
    @RequestMapping(value = "/getUserById",method = RequestMethod.POST)
    @ResponseBody
    public User getUserById(int id){
        log.info(userMapper.getUserById(id).toString());
        return userMapper.getUserById(id);
    }

    @ApiOperation(value = "获取用户信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressType", value = "addressType", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "starType", value = "starType", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "starType", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "starType", paramType = "form", dataType = "int"),
    })
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ResponseBody
    public List<User> getUserList(AddressType addressType,StarType starType,int current,int size){
        ParamsMap paramsMap = ParamsMap.getPageInstance(current, size);
        if (addressType !=null){
            paramsMap.put("addressType",addressType.ordinal());
        }
        if (starType != null){
            paramsMap.put("starType",starType.ordinal());
        }
        List<User> userList= userMapper.getUserListByMap(paramsMap);
        return userList;
    }



}
