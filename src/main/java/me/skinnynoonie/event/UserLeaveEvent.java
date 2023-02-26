package me.skinnynoonie.event;

public class UserLeaveEvent {

    private String username;

    public UserLeaveEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
