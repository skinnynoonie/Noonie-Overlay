package me.skinnynoonie;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.skinnynoonie.event.*;
import me.skinnynoonie.overlay.stats.Overlay;
import me.skinnynoonie.storage.Config;
import me.skinnynoonie.storage.OverlayData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main implements QueueListener {

    private Overlay overlay;

    public static void main(String[] args) throws IOException, InterruptedException {
        Main main = new Main();
        QueueHandler.registerListener(main);

        Overlay overlay = new Overlay();
        overlay.setVisible(true);
        main.setOverlay(overlay);
        /*
        for(String key : Config.API_KEYS) {
            HttpResponse<String> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder().uri(
                    URI.create("https://api.hypixel.net/key?key="+key)).build(), HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200) {
                System.out.println(key + " is invalid!");
            }
        }
        */

    }

    @Override
    public void onRequeue(RequeueEvent event) {
        overlay.getStatsContainer().getStatTable().clear();
    }

    @Override
    public void onUserLeave(UserLeaveEvent event) {
        overlay.getStatsContainer().getStatTable().remove(event.getUsername());
    }

    @Override
    public void onUserJoin(UserJoinEvent event) {
        new Thread(
                () -> getStatsAndPush(event.getUsername())
        ).start();
    }

    @Override
    public void onOnlineMessage(OnlineMessageEvent event) {
        for(String username : event.getUsernames()) {
            if(overlay.getStatsContainer().getStatTable().contains(username)) continue;
            new Thread(() -> getStatsAndPush(username)).start();
        }
    }

    public void getStatsAndPush(String username) {
        if(overlay.getStatsContainer().getStatTable().contains(username)) return;

        OverlayData overlayData = new OverlayData().setUsername(username, null);

        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpRequest usernameToUuidRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.mojang.com/users/profiles/minecraft/" + username))
                .GET()
                .build();

        String uuid = null;
        try{
            HttpResponse<String> usernameToUuidResponse = httpClient.send(usernameToUuidRequest, HttpResponse.BodyHandlers.ofString());
            if(usernameToUuidResponse.statusCode() == 200) {
                uuid = JsonParser.parseString(usernameToUuidResponse.body()).getAsJsonObject().get("id").getAsString();
            }
        } catch (Exception ignored) {}
        if(!QueueHandler.contains(username)) return;
        if(overlay.getStatsContainer().getStatTable().contains(username)) return;

        if(uuid == null) {
            overlay.getStatsContainer().getStatTable().add(overlayData);
            return;
        }

        HttpRequest hypixelStatRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.hypixel.net/player?key="+Config.getRandomHypixelApiKey()+"&uuid="+uuid))
                .GET()
                .build();

        HttpResponse<String> hypixelStatsResponse = null;
        try{
            HttpResponse<String> response = httpClient.send(hypixelStatRequest, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) hypixelStatsResponse = response;
        } catch (Exception ignored) {}
        if(!QueueHandler.contains(username)) return;
        if(overlay.getStatsContainer().getStatTable().contains(username)) return;

        if(hypixelStatsResponse == null) {
            overlay.getStatsContainer().getStatTable().add(overlayData);
            return;
        }

        overlayData.parseResponse(hypixelStatsResponse);
        overlay.getStatsContainer().getStatTable().add(overlayData);

        if(!overlayData.getWinstreak().equals("?")) return;
        if(Config.getAntiSniperApiKey() == null || Config.getAntiSniperApiKey().equals("")) return;

        HttpResponse<String> antiSniperResponse = null;
        HttpRequest antiSniperRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.antisniper.net/winstreak?uuid="+uuid+"&key="+Config.getAntiSniperApiKey()))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(antiSniperRequest, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) antiSniperResponse = response;
        } catch (Exception ignored) {}
        if(!QueueHandler.contains(username)) return;
        if(!overlay.getStatsContainer().getStatTable().contains(username)) return;
        if(antiSniperResponse == null) return;

        JsonObject antiSniperData = JsonParser.parseString(antiSniperResponse.body()).getAsJsonObject();
        int winstreakEstimate = antiSniperData.getAsJsonObject("player").getAsJsonObject("data").get("overall_winstreak").getAsInt();
        overlayData.setWinstreak(winstreakEstimate, true);
        overlay.getStatsContainer().getStatTable().remove(username);
        overlay.getStatsContainer().getStatTable().add(overlayData);
    }

    public void setOverlay(Overlay overlay) {
        this.overlay = overlay;
    }
}