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

import java.util.List;

@Controller
public class PlaylistController {

    @Autowired
    ServiceInterface service;

    @GetMapping("playlists")
    public String displayPlaylists(Model model) {
        List<Song> songs = service.getAllSongs();
        List<Playlist> playlists = service.getAllPlaylists();
        model.addAttribute("songs", songs);
        model.addAttribute("playlists", playlists);

        return "playlists";
    }

}
