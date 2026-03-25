package com.youtubetospotify.yt_spotify_sync.client;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class YoutubeClient {

    private static final String API_KEY = "AIzaSyBFMWNwYG2e3cJApSD5ui2ed7sFpnL6duM";

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getPlaylistItems(String playlistId) {

        String url = "https://www.googleapis.com/youtube/v3/playlistItems"
                + "?part=snippet"
                + "&maxResults=50"
                + "&playlistId=" + playlistId
                + "&key=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("items")) {
            return List.of();
        }

        Object itemsObj = response.get("items");

        // cast controlado
        List<Map<String, Object>> items = (List<Map<String, Object>>) itemsObj;

        return items;
    }
}