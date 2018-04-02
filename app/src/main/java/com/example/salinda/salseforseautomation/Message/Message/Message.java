package com.example.salinda.salseforseautomation.Message.Message;


public class Message {


    private String message,type;
    private  String from;
    private long time;
    private boolean seen;

    public Message(String from) {
        this.from = from;
    }

    public Message(String message, boolean seen , long time, String type) {
        this.message = message;
        this.type = type;
        this.time = time;
        this.seen = seen;

    }
    public Message() {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
