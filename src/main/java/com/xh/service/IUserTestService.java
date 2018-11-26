package com.xh.service;

import com.xh.dto.UserDto;

import java.util.List;

public interface IUserTestService {

    List<UserDto> queryUser(UserDto userDto) throws Exception;
}
