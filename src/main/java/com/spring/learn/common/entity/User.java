package com.spring.learn.common.entity;

/**
 *
 * @author J.zhu
 * @date 2019/6/1
 */
public class User extends DataEntity{

    public User(){}

    public User(String userName, Integer age){
        this.userName = userName;
        this.age = age;
    }

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 年龄
     */
    private Integer age;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
