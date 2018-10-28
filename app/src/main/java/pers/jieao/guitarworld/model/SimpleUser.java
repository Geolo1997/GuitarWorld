package pers.jieao.guitarworld.model;

import java.util.Date;
import java.util.List;

import pers.jieao.guitarworld.network.Network;


public class SimpleUser extends User {

    protected List<Long> attentionList;
    protected List<Long> fansList;

    public SimpleUser(long id, String password, List<Long> attentionList, List<Long> fansList) {
        super(id, password);
        this.attentionList = attentionList;
        this.fansList = fansList;
    }

    public int sendMessage(long receiverId, MessageContent messageContent) {
        Message message = new Message(getId(), receiverId, messageContent, new Date());
        int status = Network.sendMessage(message);
        return status;
    }

    public int attention(long userId) {
        return 0;
    }
}
