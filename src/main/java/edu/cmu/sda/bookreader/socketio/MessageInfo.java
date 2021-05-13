package edu.cmu.sda.bookreader.socketio;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * message format from frontend.
 */
@Component
@ToString
public class MessageInfo {

    String msgContent;

    public String getMsgContent() {
        return this.msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;

    }
}