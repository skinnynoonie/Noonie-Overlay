package me.skinnynoonie.event;

import me.skinnynoonie.utils.MinecraftChatReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class QueueHandler {

    static { initiate(); }

    public static ArrayList<QueueListener> listeners = new ArrayList<>();
    public static CopyOnWriteArrayList<String> queue = new CopyOnWriteArrayList<>();

    public static void registerListener(QueueListener listener) {
        if(listeners.contains(listener)) return;
        listeners.add(listener);
    }

    public static boolean contains(String username) {
        return queue.contains(username);
    }

    private static void initiate() {
        MinecraftChatReader logReader;
        try {
            logReader = new MinecraftChatReader(System.getProperty("user.home") + "\\.lunarclient\\offline\\multiver\\logs\\latest.log");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        logReader.skip();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {throw new RuntimeException(e);}
                String chat = logReader.nextChat();
                if(chat == null) continue;

                if(chat.contains("has joined") && !chat.contains(":")) {
                    String username = chat.split(" ")[0];
                    queue.addIfAbsent(username);
                    for(QueueListener listener : listeners) {
                        listener.onUserJoin(new UserJoinEvent(username));
                    }
                    continue;
                }

                if(chat.endsWith("has quit!") && !chat.contains(":")) {
                    String username = chat.split(" ")[0];
                    queue.remove(username);
                    for(QueueListener listener : listeners) {
                        listener.onUserLeave(new UserLeaveEvent(username));
                    }
                    continue;
                }

                if(chat.contains("Sending you to ") && !chat.contains(":")) {
                    queue.clear();
                    for(QueueListener listener : listeners) {
                        listener.onRequeue(new RequeueEvent());
                    }
                    continue;
                }

                if(chat.startsWith("ONLINE: ")) {
                    String[] usernames = chat.split("ONLINE: ")[1].replace("\n", "").split(", ");
                    for(String username : usernames) queue.addIfAbsent(username);
                    for(QueueListener listener : listeners) {
                        listener.onOnlineMessage(new OnlineMessageEvent(usernames));
                    }
                    continue;
                }
            }
        }).start();
    }

}
