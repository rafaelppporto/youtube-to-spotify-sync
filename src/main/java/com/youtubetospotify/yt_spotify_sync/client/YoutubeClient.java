package com.youtubetospotify.yt_spotify_sync.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class YoutubeClient {

    @Value("${youtube.api.url}")
    private String apiUrl;

    @Value("${youtube.max-results}")
    private String maxResults;

    @Value("${youtube.api.part}")
    private String part;

    @Value("${youtube.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getPlaylistItems(String playlistId) {

        String url = apiUrl
                + "?part=" + part
                + "&maxResults=" + maxResults
                + "&playlistId=" + playlistId
                + "&key=" + apiKey;

        String url2 = UriComponentsBuilder.fromUriString(apiUrl)
                                            .queryParam("part", part)
                                            .queryParam("maxResults", maxResults)
                                            .queryParam("playlistId", playlistId)
                                            .queryParam("key", apiKey)
                                            .toUriString();


        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("items")) {
            return List.of();
        }

        return (List<Map<String, Object>>) response.get("items");
    }
}