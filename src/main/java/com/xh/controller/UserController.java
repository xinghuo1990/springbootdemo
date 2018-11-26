package com.xh.controller;

import com.xh.dto.UserDto;
import com.xh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    @Qualifier(value = "userService")
    private IUserService userService;

    @RequestMapping(value = "queryUser")//value 可以不用加/
    public List<UserDto> queryUser(UserDto userDto){
        List<UserDto> userDtoList = null;
        try {
            userDto.setName("1");
            userDtoList = userService.queryUser(userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDtoList;

    }
}
