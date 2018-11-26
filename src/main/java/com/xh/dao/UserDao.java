package com.xh.dao;


import com.xh.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    public List<UserDto> queryUser(UserDto userDto) throws Exception;


}


