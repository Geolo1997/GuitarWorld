package pers.geolo.guitarworld.model;

import java.util.Date;

public class Message {

    private String senderId;
    private String receiverId;
    private MessageContent content;
    private Date sendTime;

    public Message(String senderId, String receiverId, MessageContent content, Date sendtime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sendTime = sendtime;
    }
    public String getSenderId() {
        return senderId;
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    public String getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
    public MessageContent getContent() {
        return content;
    }
    public void setContent(MessageContent content) {
        this.content = content;
    }
    public Date getSendTime() {
        return sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        String information = new StringBuilder()
                .append(senderId + " ")
                .append(receiverId + " ")
                .append(content.toString() + " ")
                .append(sendTime.toString() + " ")
                .toString();
        return information;
    }
}