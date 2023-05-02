package com.wxapp.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxapp.app.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
