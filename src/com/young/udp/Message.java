package com.young.udp;

import java.io.Serializable;

/**
 * @author young
 * create_date 2018/9/11 23:35
 * @version 1.0
 */
public class Message implements Serializable {
    private String from;
    private String content;

    public Message(String from, String content) {
        this.from = from;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
