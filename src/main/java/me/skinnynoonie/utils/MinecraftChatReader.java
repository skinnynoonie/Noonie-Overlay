package me.skinnynoonie.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Pattern;

public class MinecraftChatReader {

    private final BufferedReader fileReader;

    public MinecraftChatReader(File minecraftLogFile) throws FileNotFoundException {
        this.fileReader = new BufferedReader(new FileReader(minecraftLogFile));
    }

    public MinecraftChatReader(String minecraftLogFile) throws FileNotFoundException {
        this(new File(minecraftLogFile));
    }

    /**
     * Skips to the last line of the log file.
     */
    public void skip() {
        try{
            while(this.fileReader.readLine() != null);
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets the next chat in the minecraft log file.
     *
     * @param rawChat to send the entire log line.
     *
     * @return null if there is no next chat, otherwise a String.
     */
    public String nextChat(boolean rawChat) {
        String currentLogMessage;
        while((currentLogMessage = nextLog()) != null) {
            if (currentLogMessage.contains("[Client thread/INFO]: [CHAT]"))
                if (rawChat) return currentLogMessage;
                else {
                    String[] rawChatMessage = currentLogMessage.split(Pattern.quote("[Client thread/INFO]: [CHAT] "));
                    if(rawChatMessage.length < 2) return "";
                    return currentLogMessage.split(Pattern.quote("[Client thread/INFO]: [CHAT] "))[1];
                }
        }
        return null;
    }

    /**
     * Gets the next chat in the minecraft log file.
     * @return null if there is no next chat, otherwise a String.
     */
    public String nextChat() {
        return this.nextChat(false);
    }

    /**
     * @return null if there is no new log, otherwise returns a String.
     */
    public String nextLog() {
        String nextChat = null;
        try {
            nextChat = this.fileReader.readLine();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return nextChat;
    }
}
