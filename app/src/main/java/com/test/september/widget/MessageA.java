package com.test.september.widget;

import java.io.Serializable;

public class MessageA implements Serializable {
    int messageIndex;
    String text;
    int next;

    public MessageA(int messageIndex, String text, int next) {
        this.messageIndex = messageIndex;
        this.text = text;
        this.next = next;
    }

    public int getMessageIndex() {
        return messageIndex;
    }

    public void setMessageIndex(int messageIndex) {
        this.messageIndex = messageIndex;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
