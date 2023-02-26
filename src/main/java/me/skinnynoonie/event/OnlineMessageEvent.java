package me.skinnynoonie.event;

public class OnlineMessageEvent {

    private final String[] usernames;

    public OnlineMessageEvent(String[] usernames) {
        this.usernames = usernames;
    }

    public String[] getUsernames() {
        return usernames;
    }

}
