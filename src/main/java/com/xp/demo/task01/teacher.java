package com.xp.demo.task01;

public class teacher {
    int age; //年龄
    double stature; //身高
    String sex; //性别
    String attribute; //属性

    @Override
    public String toString() {
        return "Teacher{" +
                "age=" + age +
                ", stature=" + stature +
                ", sex='" + sex + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getStature() {
        return stature;
    }

    public void setStature(double stature) {
        this.stature = stature;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Teacher(int age, double stature, String sex, String attribute) {
        this.age = age;
        this.stature = stature;
        this.sex = sex;
        this.attribute = attribute;
    }

    public Teacher() {
    }
}
