package com.youtubetospotify.yt_spotify_sync.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "youtube")
public class YoutubeProperties {

    private String apiUrl;
    private String apiKey;
    private String part;
    private int maxResults;
}