package me.skinnynoonie.event;

public class UserJoinEvent {

    private String username;

    public UserJoinEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
