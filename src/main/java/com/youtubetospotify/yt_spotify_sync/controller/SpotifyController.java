package com.youtubetospotify.yt_spotify_sync.controller;

import com.youtubetospotify.yt_spotify_sync.service.SpotifyAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyController {

    private final SpotifyAuthService authService;

    public SpotifyController(SpotifyAuthService authService) {
        this.authService = authService;
    }

    // STEP 1 → generate login URL
    @GetMapping("/spotify/login")
    public String login() {
        return authService.generateAuthUrl();
    }

    // STEP 2 → receive code from Spotify
    @GetMapping("/callback")
    public String callback(@RequestParam String code) {
        return "Authorization code: " + code;
    }
}