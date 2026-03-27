package com.youtubetospotify.yt_spotify_sync.service;

import com.youtubetospotify.yt_spotify_sync.config.SpotifyProperties;
import org.springframework.stereotype.Service;

@Service
public class SpotifyAuthService {

    private final SpotifyProperties properties;

    public SpotifyAuthService(SpotifyProperties properties) {
        this.properties = properties;
    }

    /**
     * Generate the login URL for Spotify OAuth authorization
     */
    public String getLoginUrl() {
        return "https://accounts.spotify.com/authorize?..."; // TODO: complete query params
    }

    /**
     * Exchange authorization code for access token
     */
    public String getAccessToken(String authorizationCode) {
        return null; // TODO: implement token request
    }

    /**
     * Refresh the access token using refresh token
     */
    public String refreshAccessToken(String refreshToken) {
        return null; // TODO
    }
}