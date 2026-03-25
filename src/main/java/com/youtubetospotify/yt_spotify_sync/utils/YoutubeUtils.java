package com.youtubetospotify.yt_spotify_sync.utils;

public class YoutubeUtils {

    public static final String LIST_FORMAT = "list=";

    //extrair id da playlist da url
    public static String extractPlaylistId(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        // Caso tenha "list="
        int index = url.indexOf(LIST_FORMAT);
        if (index == -1) {
            return null;
        }

        String playlistId = url.substring(index + LIST_FORMAT.length());

        // Remove outros parâmetros (ex: &index=n)
        int ampIndex = playlistId.indexOf("&");
        if (ampIndex != -1) {
            playlistId = playlistId.substring(0, ampIndex);
        }

        return playlistId;
    }
}