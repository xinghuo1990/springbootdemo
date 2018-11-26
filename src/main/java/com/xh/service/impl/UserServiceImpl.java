package com.xh.service.impl;

import com.xh.dao.UserDao;
import com.xh.dto.UserDto;
import com.xh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserDto> queryUser(UserDto userDto) throws Exception{
        List<UserDto> userDtoList = userDao.queryUser(userDto);
        return userDtoList;
    }

}
