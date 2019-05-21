package com.example.autandroidapp;

/**
 * This class defines the messages, when called the content of the message is passed along with
 * if the message was sent or recieved.
 */
public class ChatMsgList
{
    public final static String Msg_sent = "Msg_sent";
    public final static String Msg_rece = "Msg_rece";
    private String msgContent;
    private String msgType;

    /**
     * This method is the default constructor
     * @param msgType - if the message is Msg_sent or a Msg_rece
     * @param msgContent - the contents of the messsage
     */
    public ChatMsgList(String msgType, String msgContent)
    {
        this.msgContent = msgContent;
        this.msgType = msgType;
    }

    /**
     * This is the get method for the content
     * @return the content of the message
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * This is the set method for the content
     * @param msgContent - the information that needs to be save as the content
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * This is the get method for the type
     * @return - if the message was received or sent
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     *This is the set method for the type
     * @param msgType - the information to be set for what type it is
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
