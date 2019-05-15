package com.example.autandroidapp;

public class ChatMsgList {
    public final static String Msg_sent = "Msg_sent";
    public final static String Msg_rece = "Msg_rece";

    private String msgContent;
    private String msgType;

    public ChatMsgList(String msgType, String msgContent)
    {
        this.msgContent = msgContent;
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
