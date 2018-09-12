package com.young.udp;

import java.io.*;

/**
 * @author young
 * create_date 2018/9/12 14:28
 * @version 1.0
 */
public class SocketDto implements Serializable {
    private Integer type;
    private String name;
    private String host;
    private Integer port;
    private String content;

    public static final Integer REGISTER = 1;
    public static final Integer MESSAGE = 2;
    public static final Integer LOGOUT = 3;

    private SocketDto(String name, String host, Integer port, String content, Integer type) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.content = content;
        this.type = type;
    }

    public static SocketDto register(String name, String host, Integer port) {
        return new SocketDto(name, host, port, null, REGISTER);
    }

    public static SocketDto message(String name, String content) {
        return new SocketDto(name, null, 0, content, MESSAGE);
    }

    public static SocketDto logout(String name, String host, Integer port) {
        return new SocketDto(name, host, port, null, LOGOUT);
    }

    public static byte[] getByteArray(Object object) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(stream);
        out.writeObject(object);
        return stream.toByteArray();
    }

    public static SocketDto getObject(byte[] bytes) throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(stream);
        return (SocketDto) in.readObject();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
