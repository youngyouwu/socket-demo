package com.young.udp;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author young
 * create_date 2018/9/11 23:52
 * @version 1.0
 */
public class User implements Serializable {
    private String name;
    private String host;
    private Integer port;
    private Integer type;

    public User(String host, Integer port, Integer type, String name) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.type = type; // 1: 加入， 2: 退出
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(host, user.host) &&
                Objects.equals(port, user.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", type=" + type +
                '}';
    }
}
