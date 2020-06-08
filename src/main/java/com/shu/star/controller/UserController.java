package com.shu.star.controller;

import cn.hutool.crypto.SecureUtil;
import com.shu.star.enums.AddressType;
import com.shu.star.enums.Gender;
import com.shu.star.enums.StarType;
import com.shu.star.enums.UserType;
import com.shu.star.mapper.UserMapper;
import com.shu.star.model.User;
import com.shu.star.util.FileUtil;
import com.shu.star.vo.ParamsMap;
import com.shu.star.vo.ResponseMap;
import com.shu.star.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.misc.FieldUtil;

import java.text.SimpleDateFormat;
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
    public Map addUser(User user){
        ResponseMap map = ResponseMap.getInstance();
        List<UserVo> userVoList = userMapper.getUserByUserName(user.getUserName());
        if (userVoList !=null && userVoList.size() > 0){
            return map.putFailure("注册失败",-1);
        }
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setIsDeleted(0);
        user.setCreateTime(new Date());
        user.setStatus(0);
        userMapper.addUser(user);
        return map.putSuccess("注册成功");
    }

    @ApiOperation(value = "新增明星", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addStar",method = RequestMethod.POST)
    public Map addStar(String userName, String passwrod, String name, Gender gender, String birthday, String people, Integer height, Double weight, String address, AddressType addressType, StarType[] starTypes, MultipartFile file) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        log.info(""+starTypes.length);
        if (birthday==null){
            return map.putFailure("数据有误",-1);
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = simpleDateFormat.parse(birthday);
        Date today=new Date();
        Integer age=today.getYear()-parse.getYear();
        User user=new User(userName,SecureUtil.md5(passwrod),name,gender,age,height,weight,address);
        user.setPoint(0);
        user.setAddressType(addressType);
        user.setUserType(UserType.明星);
        user.setBirthday(birthday);
        user.setPeople(people);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDeleted(0);
        user.setStatus(0);
        Integer flag = userMapper.addUser(user);
        if (flag > 0){
            log.info(addressType.toString());
           for (StarType starType:starTypes){
               userMapper.addStarItem(user.getId(),starType.ordinal(),new Date());
           }
            String url = FileUtil.upload("1", file);
           userMapper.addUserFile(user.getId(),url,new Date());
            return map.putSuccess("新增成功");
        }
        return map.putFailure("新增失败",-1);
    }

    @ApiOperation(value = "新增明星", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addStarNew",method = RequestMethod.POST)
    public Map addStarNew(String userName, String passwrod, String name, Gender gender, String birthday, String people, Integer height, Double weight, String address, AddressType addressType,StarType[] starTypes,String file) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        if (birthday==null){
            return map.putFailure("数据有误",-1);
        }
        //用户名检测
        List<UserVo> userVoList = userMapper.getUserByUserName(userName);
        if (userVoList !=null && userVoList.size() > 0){
            return map.putFailure("注册失败",-1);
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = simpleDateFormat.parse(birthday);
        Date today=new Date();
        Integer age=today.getYear()-parse.getYear();
        User user=new User(userName,SecureUtil.md5(passwrod),name,gender,age,height,weight,address);
        user.setPoint(0);
        user.setAddressType(addressType);
        user.setUserType(UserType.明星);
        user.setBirthday(birthday);
        user.setPeople(people);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDeleted(0);
        user.setStatus(0);
        Integer flag = userMapper.addUser(user);
        if (flag > 0){
            log.info(addressType.toString());
            log.info(""+starTypes.length);
            for (StarType starType:starTypes){
                userMapper.addStarItem(user.getId(),starType.ordinal(),new Date());
            }
            userMapper.addUserFile(user.getId(),file,new Date());

            return map.putSuccess("新增成功");
        }
        return map.putFailure("新增失败",-1);
    }

    @ApiOperation(value = "修改明星信息(基本数据信息不能修改)", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "form", dataType = "int",required = true),
    })
    @RequestMapping(value = "/updateStar",method = RequestMethod.POST)
    public Map updateStar(User user, StarType[] starTypes, MultipartFile file) throws Exception {
        ResponseMap map = ResponseMap.getInstance();
        user.setUserType(UserType.明星);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDeleted(0);
        user.setStatus(0);
        Integer flag = userMapper.updateUser(user);
        if (flag > 0){
            if (starTypes !=null){
               for (StarType starType:starTypes){
                    userMapper.addStarItem(user.getId(),starType.ordinal(),new Date());
               }
            }
            if (file != null){
                String url = FileUtil.upload("1", file);
                userMapper.addUserFile(user.getId(),url,new Date());
            }
            return map.putSuccess("修改成功");
        }
        return map.putFailure("新增失败",-1);
    }


    @ApiOperation(value = "用户登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "userName", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "password", paramType = "form", dataType = "string")
    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map login(String userName, String password){
        password=SecureUtil.md5(password);
        ResponseMap instance = ResponseMap.getInstance();
        List<UserVo> userList = userMapper.login(userName, password);
        if ( userList == null || userList.isEmpty()){
            return instance.putFailure("登录失败",-1);
        }
        return instance.putValue(userList.get(0),"登录成功");
    }


    @ApiOperation(value = "获取用户信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "form", dataType = "int")
    })
    @RequestMapping(value = "/getUserById",method = RequestMethod.POST)
    public UserVo getUserById(int id){
//        log.info(userMapper.getUserById(id).toString());
        return userMapper.getUserById(id);
    }

    @ApiOperation(value = "刷新人气", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "userid", paramType = "form", dataType = "int")
    })
    @RequestMapping(value = "/addStarPoint",method = RequestMethod.POST)
    @ResponseBody
    public Map addStarPoint(int id){
        ResponseMap map = ResponseMap.getInstance();
        userMapper.addStarPoint(id);
        return map.putSuccess("人气增加成功");
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
    public List<UserVo> getUserList(AddressType addressType,StarType starType,Gender gender,int current,int size){
        ParamsMap paramsMap = ParamsMap.getPageInstance(current, size);
        if (addressType !=null){
            paramsMap.put("addressType",addressType.ordinal());
        }
        if (starType != null){
            paramsMap.put("starType",starType.ordinal());
        }
        if (gender!=null){
            paramsMap.put("gender",gender.ordinal());
        }
        List<UserVo> userList= userMapper.getUserListByMap(paramsMap);
        return userList;
    }



}
