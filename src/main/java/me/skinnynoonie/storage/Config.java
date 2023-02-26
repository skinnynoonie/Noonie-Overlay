package me.skinnynoonie.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Config {

    static {
        initiate();
    }

    public static JsonObject VALUES;

    public static void save() {
        try {
            PrintWriter printWriter = new PrintWriter(System.getProperty("user.home") + "\\NoonieOverlay\\config.json");
            printWriter.print(VALUES);
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setMinecraftLogDir(String dir) {
        VALUES.addProperty("minecraft_log_dir", dir);
    }

    public static String getMinecraftLogDir() {
        return VALUES.get("minecraft_log_dir").getAsString();
    }

    public static void setAntiSniperApiKey(String apiKey) {
        VALUES.addProperty("antisniper_api_key", apiKey);
    }

    public static String getAntiSniperApiKey() {
        return VALUES.get("antisniper_api_key").getAsString();
    }

    public static void setHypixelApiKeys(List<String> apiKeys) {
        JsonArray jsonArray = new JsonArray();
        for(String apiKey : apiKeys) jsonArray.add(apiKey);
        VALUES.add("hypixel_api_keys", jsonArray);
    }

    public static List<String> getHypixelApiKeys() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (JsonElement hypixelApiKey : VALUES.get("hypixel_api_keys").getAsJsonArray()) {
            arrayList.add(hypixelApiKey.getAsString());
        }
        return arrayList;
    }

    public static String getRandomHypixelApiKey() {
        JsonArray hypixelApiKeys = VALUES.get("hypixel_api_keys").getAsJsonArray();
        return hypixelApiKeys.get(new Random().nextInt(hypixelApiKeys.size())).getAsString();
    }

    private static void initiate() {
        new File(System.getProperty("user.home") + "\\NoonieOverlay").mkdirs();
        try {
            new File(System.getProperty("user.home") + "\\NoonieOverlay\\config.json").createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String configContents;
        try {
            configContents = Files.readString(Paths.get(System.getProperty("user.home") + "\\NoonieOverlay\\config.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(configContents == null || configContents.equals("")) {
            configContents = "{\"hypixel_api_keys\":[],\"antisniper_api_key\":\"\",\"minecraft_log_dir\":\"\"}";
        }
        VALUES = JsonParser.parseString(configContents).getAsJsonObject();
        if(!VALUES.has("hypixel_api_keys")) VALUES.add("hypixel_api_keys", new JsonArray());
        if(!VALUES.has("antisniper_api_key")) VALUES.addProperty("antisniper_api_key", "");
        if(!VALUES.has("minecraft_log_dir")) VALUES.addProperty("minecraft_log_dir", "");
    }


}
