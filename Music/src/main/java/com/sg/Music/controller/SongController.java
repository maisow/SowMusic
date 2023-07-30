package com.sg.Music.controller;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Playlist;
import com.sg.Music.entities.Song;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SongController {

    @Autowired
    ServiceInterface service;

    @GetMapping("songs")
    public String displaySongs(Model model) {
        List<Song> songs = service.getAllSongs();
        List<Album> albums = service.getAllAlbums();
        List<Artist> artists = service.getAllArtists();
        List<Playlist> playlists = service.getAllPlaylists();
        model.addAttribute("songs", songs);
        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        model.addAttribute("playlists", playlists);

        return "songs";
    }

    @GetMapping("songDetail")
    public String songDetail(Integer id, Model model) {
        Song song = service.getSongByID(id);
        model.addAttribute("song", song);

        return "songDetail";
    }
    @PostMapping("deleteSong")
    public String deleteSong(Integer id) {
        service.deleteSongByID(id);

        return "redirect:/songs";
    }
}
