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
public class AlbumController {

    @Autowired
    ServiceInterface service;

    @GetMapping("albums")
    public String displayAlbums(Model model) {
        List<Album> albums = service.getAllAlbums();
        List<Artist> artists = service.getAllArtists();
        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);

        return "albums";
    }

    @GetMapping("albumDetail")
    public String albumDetail(Integer id, Model model) {
        Album album = service.getAlbumByID(id);
        model.addAttribute("album", album);

        return "albumDetail";
    }


}
