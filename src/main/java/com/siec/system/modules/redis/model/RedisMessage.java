package com.siec.system.modules.redis.model;

/**
 * @Description: redis订阅发布模式，监听的内容
 * @author: 老骨头（lgt）
 * @date: 2021/2/14
 */
public class RedisMessage {
    private String type;
    private String content;

    public RedisMessage() {
    }

    public RedisMessage(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}