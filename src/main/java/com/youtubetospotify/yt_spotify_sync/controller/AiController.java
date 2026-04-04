package com.youtubetospotify.yt_spotify_sync.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtubetospotify.yt_spotify_sync.service.AiService;
import com.youtubetospotify.yt_spotify_sync.service.YoutubeService;
import com.youtubetospotify.yt_spotify_sync.utils.YoutubeUtils;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final YoutubeService youtubeService;
    private final AiService aiService;

    public AiController(YoutubeService youtubeService, AiService aiService) {
        this.youtubeService = youtubeService;
        this.aiService = aiService;
    }

    @GetMapping("/youtube/raw")
    public List<String> getRaw(@RequestParam String url) {

        String playlistId = YoutubeUtils.extractPlaylistId(url);

        return youtubeService.getRawTitles(playlistId);
    }

    @GetMapping("/prompt")
    public String buildPrompt(@RequestParam String url) {

        String playlistId = YoutubeUtils.extractPlaylistId(url);

        List<String> titles = youtubeService.getRawTitles(playlistId);

        return aiService.buildPrompt(titles);
    }

    @GetMapping("/process")
    public String process(@RequestParam String url) {

        String playlistId = YoutubeUtils.extractPlaylistId(url);

        List<String> titles = youtubeService.getRawTitles(playlistId);

        return aiService.processTracks(titles);
    }
}