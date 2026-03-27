package com.youtubetospotify.yt_spotify_sync.controller;

import com.youtubetospotify.yt_spotify_sync.model.TrackInfo;
import com.youtubetospotify.yt_spotify_sync.service.YoutubeService;
import com.youtubetospotify.yt_spotify_sync.utils.YoutubeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final YoutubeService service;

    public PlaylistController(YoutubeService service) {
        this.service = service;
    }

    @GetMapping("/extract")
    public List<TrackInfo> extractTracks(@RequestParam String url) {

        String playlistId = YoutubeUtils.extractPlaylistId(url);

        if (playlistId == null) {
            return List.of(new TrackInfo("", "Invalid URL", ""));
        }

        return service.getTracks(playlistId);
    }
}