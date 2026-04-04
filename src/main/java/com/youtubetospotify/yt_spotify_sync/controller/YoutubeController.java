package com.youtubetospotify.yt_spotify_sync.controller;

import com.youtubetospotify.yt_spotify_sync.model.TrackInfo;
import com.youtubetospotify.yt_spotify_sync.service.YoutubeService;
import com.youtubetospotify.yt_spotify_sync.utils.YoutubeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

// TODO /playlist -> /youtube and adjust in insomnia for tests
@RequestMapping("/playlist")
public class YoutubeController {

    private final YoutubeService youtubeService;

    public YoutubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/extract")
    public List<TrackInfo> extractTracks(@RequestParam String url) {

        String playlistId = YoutubeUtils.extractPlaylistId(url);

        if (playlistId == null) {
            return List.of(new TrackInfo("", "Invalid URL", ""));
        }

        return youtubeService.getTracks(playlistId);
    }
}