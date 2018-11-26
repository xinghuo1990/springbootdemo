package com.xh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/hello")
    public String sayHello(){
        System.out.println("hello");
        return "hello";
    }
}
