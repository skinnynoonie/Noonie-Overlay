package me.skinnynoonie.event;

public interface QueueListener {

    void onRequeue(RequeueEvent event);
    void onUserLeave(UserLeaveEvent event);
    void onUserJoin(UserJoinEvent event);
    void onOnlineMessage(OnlineMessageEvent event);

}
