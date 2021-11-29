package com.example.appdoan_vutruonggiang.UI;

public class Chat {
    String content,time;
    long id;

    public Chat(){}

    public Chat(long id,String content, String time) {
        this.id=id;
        this.content = content;
        this.time = time;
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
