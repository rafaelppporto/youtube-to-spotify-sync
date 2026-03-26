package com.youtubetospotify.yt_spotify_sync.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.youtubetospotify.yt_spotify_sync.client.YoutubeClient;

@Service
public class YoutubeService {

    private final YoutubeClient client;

    public YoutubeService(YoutubeClient client) {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public List<String> getTitles(String playlistId) {
        return client.getPlaylistItems(playlistId).stream()
                .map(item -> {
                    Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");
                    return snippet != null ? snippet.get("title").toString() : "Title not found";
                })
                .toList();
    }
}