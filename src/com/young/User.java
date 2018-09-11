package com.young;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author young
 * create_date 2018/9/11 12:26
 * @version 1.0
 */
public class User implements Serializable {
    private String name;
    private transient Integer age;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + new SimpleDateFormat("yyyy-MM-dd").format(birthday) +
                '}';
    }
}
