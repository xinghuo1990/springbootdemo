package com.xh.service;

import com.xh.dto.UserDto;
import java.util.List;

public interface IUserService {

    List<UserDto> queryUser(UserDto userDto) throws Exception;



}
