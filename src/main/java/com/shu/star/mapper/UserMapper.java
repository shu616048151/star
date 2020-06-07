package com.shu.star.mapper;

import com.shu.star.model.User;
import com.shu.star.vo.ParamsMap;
import com.shu.star.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author shuxibing
 * @Date 2020/5/20 23:21
 * @Uint d9lab_2019
 * @Description:
 */
public interface UserMapper {
    UserVo getUserById(int userId);

    Integer addUser(User user);

    List<UserVo> login(@Param("userName") String userName,@Param("password") String password);

    List<UserVo> getUserList(@Param("addressType") Integer addressType,@Param("starType") Integer starType);

    Integer addStarItem(@Param("userId") Integer id, @Param("starType") int starType, @Param("createTime")Date createTime);

    List<UserVo> getUserListByMap(ParamsMap paramsMap);

    void addUserFile(@Param("userId") Integer userId, @Param("url") String url,@Param("createTime")Date createTime);

    void addStarPoint(int id);

    Integer updateUser(User user);
}
