package com.cloud.provider.bean;

/**
 * @ClassName Attributes
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/7 9:26
 */
public class Attributes {
    private Dress dress;
    private String gender;
    private Headpose headpose;
    private Integer age;
    private String smile;

    public Dress getDress() {
        return dress;
    }

    public void setDress(Dress dress) {
        this.dress = dress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Headpose getHeadpose() {
        return headpose;
    }

    public void setHeadpose(Headpose headpose) {
        this.headpose = headpose;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSmile() {
        return smile;
    }

    public void setSmile(String smile) {
        this.smile = smile;
    }
}
