package com.spring.learn.common.entity;

/**
 * Created by J.zhu on 2019/6/1.
 */
public class User extends DataEntity{

    public User(){}

    public User(String userName, Integer age){
        this.userName = userName;
        this.age = age;
    }
    /**
     * 用户名
     */
    private String userName;
    /**
     * 年龄
     */
    private Integer age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
