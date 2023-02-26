package me.skinnynoonie.utils;

import java.util.HashMap;
import java.util.regex.Pattern;

public class MinecraftLegacyHTMLParser {

    public static String parseLegacy(String text) {
        HashMap<String, String> keyCodes = new HashMap<>();
        keyCodes.put("§0", "<font color=#000000>");
        keyCodes.put("§1", "<font color=#0000AA>");
        keyCodes.put("§2", "<font color=#00AA00>");
        keyCodes.put("§3", "<font color=#00AAAA>");
        keyCodes.put("§4", "<font color=#AA0000>");
        keyCodes.put("§5", "<font color=#AA00AA>");
        keyCodes.put("§6", "<font color=#FFAA00>");
        keyCodes.put("§7", "<font color=#AAAAAA>");
        keyCodes.put("§8", "<font color=#555555>");
        keyCodes.put("§9", "<font color=#5555FF>");
        keyCodes.put("§a", "<font color=#55FF55>");
        keyCodes.put("§b", "<font color=#55FFFF>");
        keyCodes.put("§c", "<font color=#FF5555>");
        keyCodes.put("§d", "<font color=#FF55FF>");
        keyCodes.put("§e", "<font color=#FFFF55>");
        keyCodes.put("§f", "<font color=#FFFFFF>");

        for(String key : keyCodes.keySet())
            text = text.replace(key, keyCodes.get(key));

        return "<html>" + text + "</html>";
    }

    public static String parseLegacyBedwarsLevel(String text, Integer level) {
        if(level == null) return null;
        char[] chars = level.toString().toCharArray();
        HashMap<String, String> keyCodes = new HashMap<>();
        keyCodes.put("§0", "<font color=#000000>");
        keyCodes.put("§1", "<font color=#0000AA>");
        keyCodes.put("§2", "<font color=#00AA00>");
        keyCodes.put("§3", "<font color=#00AAAA>");
        keyCodes.put("§4", "<font color=#AA0000>");
        keyCodes.put("§5", "<font color=#AA00AA>");
        keyCodes.put("§6", "<font color=#FFAA00>");
        keyCodes.put("§7", "<font color=#AAAAAA>");
        keyCodes.put("§8", "<font color=#555555>");
        keyCodes.put("§9", "<font color=#5555FF>");
        keyCodes.put("§a", "<font color=#55FF55>");
        keyCodes.put("§b", "<font color=#55FFFF>");
        keyCodes.put("§c", "<font color=#FF5555>");
        keyCodes.put("§d", "<font color=#FF55FF>");
        keyCodes.put("§e", "<font color=#FFFF55>");
        keyCodes.put("§f", "<font color=#FFFFFF>");

        for(String key : keyCodes.keySet())
            text = text.replace(key, keyCodes.get(key));

        for(char kar : chars)
            text = text.replaceFirst(Pattern.quote("*"), String.valueOf(kar));

        return "<html>" + text + "</html>";
    }

}
