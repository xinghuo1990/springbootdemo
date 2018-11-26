package com.xh.dto;


import java.io.Serializable;

public class UserDto implements Serializable{
    private String name;
    private String sex;
    private String age;

    public UserDto(){

    }
    public UserDto(String name,String sex){
        this.name= name;
        this.sex =sex;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
