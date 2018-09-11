package com.young.udp;

/**
 * @author young
 * create_date 2018/9/11 23:52
 * @version 1.0
 */
public class User {
    private String host;
    private Integer port;

    public User(String host, Integer port) {
        this.host = host;
        this.port = port;
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

    @Override
    public String toString() {
        return "User{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
