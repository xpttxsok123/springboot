package com.xp.demo.task01;

public class Student {

    // private 私有，外界不能访问
    private int age;

    //班级
    private int clazz;

    //性别
    private String gender;


    //方法具备功能
    // this 代表当前对象
    public void setAge(int age){
        this.age = age;
    }

    //获取
    public int getAge(){
        return this.age;
    }


    public int getClazz() {
        return clazz;
    }

    public void setClazz(int clazz) {
        this.clazz = clazz;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
