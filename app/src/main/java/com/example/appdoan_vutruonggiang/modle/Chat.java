package com.example.appdoan_vutruonggiang.modle;

public class Chat {
    String content,time;
    long id;
    String type;

    public Chat(){}

    public Chat(long id,String content, String time, String type) {
        this.id=id;
        this.content = content;
        this.time = time;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
