package com.sg.Music.controller;

import com.sg.Music.entities.*;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArtistController {

    @Autowired
    ServiceInterface service;

    @GetMapping("artists")
    public String displayArtists(Model model) {
        List<Artist> artists = service.getAllArtists();
        List<Album> albums = service.getAllAlbums();
        List<Label> labels = service.getAllLabels();
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        model.addAttribute("labels", labels);

        return "artists";
    }
    @GetMapping("artistDetail")
    public String artistDetail(Integer id, Model model) {
        Artist artist = service.getArtistByID(id);
        model.addAttribute("artist", artist);

        return "artistDetail";
    }




}
