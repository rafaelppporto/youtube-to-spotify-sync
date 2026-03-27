package com.youtubetospotify.yt_spotify_sync.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtubetospotify.yt_spotify_sync.service.SpotifyAuthService;

@RestController
public class SpotifyController {

    private final SpotifyAuthService spotifyAuthService;

    public SpotifyController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }

    // STEP 1 - Login
    @GetMapping("/spotify/login")
    public String login() {
        return spotifyAuthService.generateAuthUrl();
    }

    // STEP 2 - Callback (returns token response)
    @GetMapping("/spotify/callback")
    public Map<String, Object> callback(@RequestParam String code) {
        return spotifyAuthService.exchangeCodeForToken(code);
    }

    // STEP 3 - Get user profile (needs accessToken)
    @GetMapping("/spotify/me")
    public Map<String, Object> me(@RequestParam String accessToken) {
        return spotifyAuthService.getCurrentUser(accessToken);
    }
}