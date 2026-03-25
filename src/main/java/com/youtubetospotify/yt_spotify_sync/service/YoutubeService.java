package com.youtubetospotify.yt_spotify_sync.service;

import com.youtubetospotify.yt_spotify_sync.client.YoutubeClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YoutubeService {

    private final YoutubeClient client = new YoutubeClient();

    @SuppressWarnings("unchecked")
    public List<String> getTitles(String playlistId) {

        List<Map<String, Object>> items = client.getPlaylistItems(playlistId);

        List<String> titles = new ArrayList<>();

        for (Map<String, Object> item : items) {

            Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");

            if (snippet != null) {
                String title = (String) snippet.get("title");
                titles.add(title);
            }
        }

        return titles;
    }
}