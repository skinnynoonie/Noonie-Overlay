package me.skinnynoonie.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.skinnynoonie.utils.MinecraftLegacyHTMLParser;

import java.net.http.HttpResponse;
import java.text.DecimalFormat;

public class OverlayData {

    private String lvl = "?";
    private String username = "?";
    private String winstreak = "?";
    private String fkdr = "?";
    private String wlr = "?";
    private String wins = "?";
    private String losses = "?";

    public OverlayData parseResponse(HttpResponse<String> response) {

        JsonObject data = JsonParser.parseString(response.body()).getAsJsonObject();

        try {
            int level = data.getAsJsonObject("player").getAsJsonObject("achievements").get("bedwars_level").getAsInt();
            setLevel(level);
        }catch (Exception ignored) {}

        try {
            String displayName = data.getAsJsonObject("player").get("displayname").getAsString();

            String dRank = null;
            String monthlyPackageRank = null;
            String newPackageRank = null;
            String rank = "NONE";

            if(data.getAsJsonObject("player").has("rank")) dRank = data.getAsJsonObject("player").get("rank").getAsString();
            if(data.getAsJsonObject("player").has("monthlyPackageRank")) monthlyPackageRank = data.getAsJsonObject("player").get("monthlyPackageRank").getAsString();
            if(data.getAsJsonObject("player").has("newPackageRank")) newPackageRank = data.getAsJsonObject("player").get("newPackageRank").getAsString();

            if(dRank != null) rank = dRank;
            else if(monthlyPackageRank != null) rank = "MVP++";
            else if (newPackageRank != null) {
                if(newPackageRank.equals("VIP_PLUS")) rank = "VIP+";
                else if(newPackageRank.equals("MVP_PLUS")) rank = "MVP+";
                else rank = newPackageRank;
            }
            setUsername(displayName, rank);
        }catch (Exception ignored) {}

        try {
            int ws = data.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("winstreak").getAsInt();
            setWinstreak(ws, false);
        }catch (Exception ignored) {}

        try {
            int fk = data.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("final_kills_bedwars").getAsInt();
            int fd = data.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("final_deaths_bedwars").getAsInt();
            setFKDR(fk, fd);
        }catch (Exception ignored) {}

        try {
            int w = data.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("wins_bedwars").getAsInt();
            int l = data.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("losses_bedwars").getAsInt();
            setWinLossRatio(w, l);
        }catch (Exception ignored) {}

        try {
            int w = data.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("wins_bedwars").getAsInt();
            setWins(w);
        }catch (Exception ignored) {}

        try {
            int l = data.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("losses_bedwars").getAsInt();
            setLosses(l);
        }catch (Exception ignored) {}

        //Some corrections down here.
        if(!lvl.equals("?")) {
            if(fkdr.equals("?")) setFKDR(0,0);
            if(wlr.equals("?")) setWinLossRatio(0,0);
            if(wins.equals("?")) setWins(0);
            if(losses.equals("?")) setLosses(0);
        }

        return this;
    }

    public OverlayData setLevel(Integer level) {
        if(level == null) return this;
        if(level <= 9) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[*✫]", level);
        }
        if(level >= 10 && level <= 99) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[**✫]", level);
        }
        if(level >= 100 && level <= 199) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§f[***✫]", level);
        }
        if(level >= 200 && level <= 299) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§6[***✫]", level);
        }
        if(level >= 300 && level <= 399) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§b[***✫]", level);
        }
        if(level >= 400 && level <= 499) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§2[***✫]", level);
        }
        if(level >= 500 && level <= 599) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§3[***✫]", level);
        }
        if(level >= 600 && level <= 699) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§4[***✫]", level);
        }
        if(level >= 700 && level <= 799) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§d[***✫]", level);
        }
        if(level >= 800 && level <= 899) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§9[***✫]", level);
        }
        if(level >= 900 && level <= 999) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§5[***✫]", level);
        }
        if(level >= 1000 && level <= 1099) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§c[§6*§e*§a*§b*§d✫§5]", level);
        }
        if(level >= 1100 && level <= 1199) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§f****§7✪]", level);
        }
        if(level >= 1200 && level <= 1299) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§e****§6✪§7]", level);
        }
        if(level >= 1300 && level <= 1399) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§b****§3✪§7]", level);
        }
        if(level >= 1400 && level <= 1499) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§a****§2✪§7]", level);
        }
        if(level >= 1500 && level <= 1599) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§3****§9✪§7]", level);
        }
        if(level >= 1600 && level <= 1699) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§c****§4✪§7]", level);
        }
        if(level >= 1700 && level <= 1799) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§d****§5✪§7]", level);
        }
        if(level >= 1800 && level <= 1899) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§9****§1✪§7]", level);
        }
        if(level >= 1900 && level <= 1999) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§7[§5****§8✪§7]", level);
        }
        if(level >= 2000 && level <= 2099) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§8[§7*§f**§7*✪§8]", level);
        }
        if(level >= 2100 && level <= 2199) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§f[*§e**§6*❀]", level);
        }
        if(level >= 2200 && level <= 2299) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§6[*§f**§b*§3❀]", level);
        }
        if(level >= 2300 && level <= 2399) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§5[*§d**§6*§e❀]", level);
        }
        if(level >= 2400 && level <= 2499) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§b[*§f**§7*❀§8]", level);
        }
        if(level >= 2500 && level <= 2599) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§f[*§a**§2*❀]", level);
        }
        if(level >= 2600 && level <= 2699) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§4[*§c**§d*❀§5]", level);
        }
        if(level >= 2700 && level <= 2799) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§e[*§f**§8*❀]", level);
        }
        if(level >= 2800 && level <= 2899) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§a[*§2**§6*❀§e]", level);
        }
        if(level >= 2900 && level <= 2999) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§b[*§3**§9*❀§1]", level);
        }
        if(level >= 3000 && level <= 3099) {
            lvl = MinecraftLegacyHTMLParser.parseLegacyBedwarsLevel("§e[*§6**§c*❀§4]", level);
        }
        return this;
    }

    public OverlayData setUsername(String username, String rank) {
        if(rank == null) {
            this.username = username;
            return this;
        }
        if(rank.equals("ADMIN")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§c[ADMIN] " + username);
        if(rank.equals("GAME_MASTER")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§2[GM] " + username);
        if(rank.equals("MODERATOR")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§e[MOD] " + username);
        if(rank.equals("YOUTUBER")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§c[&fYT&c] " + username);
        if(rank.equals("MVP++")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§6" + username);
        if(rank.equals("MVP+")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§b" + username);
        if(rank.equals("MVP")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§b" + username);
        if(rank.equals("VIP+")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§a" + username);
        if(rank.equals("VIP")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§a" + username);
        if(rank.equals("NONE")) this.username = MinecraftLegacyHTMLParser.parseLegacy("§7" + username);
        return this;
    }

    public OverlayData setWinstreak(int winstreak, boolean estimate) {
        if(estimate) {
            if (winstreak >= 0 && winstreak <= 3) this.winstreak = "<html><font color="+Colors.DANGER_LEVEL_1+">"+"(E) "+winstreak+"</html>";
            if (winstreak >= 4 && winstreak <= 9) this.winstreak = "<html><font color="+Colors.DANGER_LEVEL_2+">"+"(E) "+winstreak+"</html>";
            if (winstreak >= 10 && winstreak <= 24) this.winstreak = "<html><font color="+Colors.DANGER_LEVEL_3+">"+"(E) "+winstreak+"</html>";
            if (winstreak >= 25 && winstreak <= 49) this.winstreak = "<html><font color="+Colors.DANGER_LEVEL_4+">"+"(E) "+winstreak+"</html>";
            if (winstreak >= 50 && winstreak <= 99) this.winstreak = "<html><font color="+Colors.DANGER_LEVEL_5+">"+"(E) "+winstreak+"</html>";
            if (winstreak >= 100) this.winstreak = "<html><font color="+Colors.DANGER_LEVEL_6+">"+"(E) "+winstreak+"</html>";
        }
        else {
            if (winstreak >= 0 && winstreak <= 3) this.winstreak = "<html><font color=" + Colors.DANGER_LEVEL_1 + ">" + winstreak + "</html>";
            if (winstreak >= 4 && winstreak <= 9) this.winstreak = "<html><font color=" + Colors.DANGER_LEVEL_2 + ">" + winstreak + "</html>";
            if (winstreak >= 10 && winstreak <= 24) this.winstreak = "<html><font color=" + Colors.DANGER_LEVEL_3 + ">" + winstreak + "</html>";
            if (winstreak >= 25 && winstreak <= 49) this.winstreak = "<html><font color=" + Colors.DANGER_LEVEL_4 + ">" + winstreak + "</html>";
            if (winstreak >= 50 && winstreak <= 99) this.winstreak = "<html><font color=" + Colors.DANGER_LEVEL_5 + ">" + winstreak + "</html>";
            if (winstreak >= 100) this.winstreak = "<html><font color=" + Colors.DANGER_LEVEL_6 + ">" + winstreak + "</html>";
        }
        return this;
    }

    public OverlayData setFKDR(int kills, int deaths) {
        double fkdr = (double) kills / Math.max(deaths, 1);
        String formattedFkdr = new DecimalFormat("0.00").format(fkdr);
        if (fkdr >= 0 && fkdr <= 0.99) this.fkdr = "<html><font color="+Colors.DANGER_LEVEL_1+">"+formattedFkdr+"</html>";
        if (fkdr > 0.99 && fkdr <= 2.99) this.fkdr = "<html><font color="+Colors.DANGER_LEVEL_2+">"+formattedFkdr+"</html>";
        if (fkdr > 2.99 && fkdr <= 4.99) this.fkdr = "<html><font color="+Colors.DANGER_LEVEL_3+">"+formattedFkdr+"</html>";
        if (fkdr > 4.99 && fkdr <= 9.99) this.fkdr = "<html><font color="+Colors.DANGER_LEVEL_4+">"+formattedFkdr+"</html>";
        if (fkdr > 9.99 && fkdr <= 24.99) this.fkdr = "<html><font color="+Colors.DANGER_LEVEL_5+">"+formattedFkdr+"</html>";
        if (fkdr > 24.99) this.fkdr = "<html><font color="+Colors.DANGER_LEVEL_6+">"+formattedFkdr+"</html>";
        return this;
    }

    public OverlayData setWinLossRatio(int wins, int losses) {
        double wlr = (double) wins / Math.max(losses, 1);
        String formattedWlr = new DecimalFormat("0.00").format(wlr);
        if (wlr >= 0 && wlr <= 0.99) this.wlr = "<html><font color="+Colors.DANGER_LEVEL_1+">"+formattedWlr+"</html>";
        if (wlr > 0.99 && wlr <= 1.99) this.wlr = "<html><font color="+Colors.DANGER_LEVEL_2+">"+formattedWlr+"</html>";
        if (wlr > 1.99 && wlr <= 4.99) this.wlr = "<html><font color="+Colors.DANGER_LEVEL_3+">"+formattedWlr+"</html>";
        if (wlr > 4.99 && wlr <= 6.99) this.wlr = "<html><font color="+Colors.DANGER_LEVEL_4+">"+formattedWlr+"</html>";
        if (wlr > 6.99 && wlr <= 9.99) this.wlr = "<html><font color="+Colors.DANGER_LEVEL_5+">"+formattedWlr+"</html>";
        if (wlr > 9.99) this.wlr = "<html><font color="+Colors.DANGER_LEVEL_6+">"+formattedWlr+"</html>";
        return this;
    }

    public OverlayData setWins(int wins) {
        if (wins >= 0 && wins <= 499) this.wins = "<html><font color="+Colors.DANGER_LEVEL_1+">"+wins+"</html>";
        if (wins > 499 && wins <= 999) this.wins = "<html><font color="+Colors.DANGER_LEVEL_2+">"+wins+"</html>";
        if (wins > 999 && wins <= 2999) this.wins = "<html><font color="+Colors.DANGER_LEVEL_3+">"+wins+"</html>";
        if (wins > 2999 && wins <= 4999) this.wins = "<html><font color="+Colors.DANGER_LEVEL_4+">"+wins+"</html>";
        if (wins > 4999 && wins <= 9999) this.wins = "<html><font color="+Colors.DANGER_LEVEL_5+">"+wins+"</html>";
        if (wins > 9999) this.wins = "<html><font color="+Colors.DANGER_LEVEL_6+">"+wins+"</html>";
        return this;
    }

    public OverlayData setLosses(int losses) {
        if (losses >= 0 && losses <= 2) this.losses = "<html><font color="+Colors.DANGER_LEVEL_6+">"+losses+"</html>";
        if (losses > 2 && losses <= 7) this.losses = "<html><font color="+Colors.DANGER_LEVEL_5+">"+losses+"</html>";
        if (losses > 7 && losses <= 20) this.losses = "<html><font color="+Colors.DANGER_LEVEL_4+">"+losses+"</html>";
        if (losses > 20 && losses <= 80) this.losses = "<html><font color="+Colors.DANGER_LEVEL_3+">"+losses+"</html>";
        if (losses > 80 && losses <= 1000) this.losses = "<html><font color="+Colors.DANGER_LEVEL_2+">"+losses+"</html>";
        if (losses > 1000) this.losses = "<html><font color="+Colors.DANGER_LEVEL_1+">"+losses+"</html>";
        return this;
    }

    public String getWins() {
        return wins;
    }

    public String getLosses() {
        return losses;
    }

    public String getLevel() {
        return lvl;
    }

    public String getUsername() {
        return username;
    }

    public String getWinstreak() {
        return winstreak;
    }

    public String getFKDR() {
        return fkdr;
    }

    public String getWinLossRatio() {
        return wlr;
    }

}
