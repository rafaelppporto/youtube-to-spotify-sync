package com.youtubetospotify.yt_spotify_sync.client;

import com.youtubetospotify.yt_spotify_sync.config.YoutubeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
public class YoutubeClient {

    private static final Logger log = LoggerFactory.getLogger(YoutubeClient.class);

    private final YoutubeProperties props;
    private final RestTemplate restTemplate;

    public YoutubeClient(YoutubeProperties props, RestTemplate restTemplate) {
        this.props = props;
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getPlaylistItems(String playlistId) {

        String url = UriComponentsBuilder.fromUriString(props.getApiUrl())
                .queryParam("part", props.getPart())
                .queryParam("maxResults", props.getMaxResults())
                .queryParam("playlistId", playlistId)
                .queryParam("key", props.getApiKey())
                .toUriString();

        log.info("Calling Youtube API: {}", url);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("items")) {
            return List.of();
        }

        return (List<Map<String, Object>>) response.get("items");
    }
}