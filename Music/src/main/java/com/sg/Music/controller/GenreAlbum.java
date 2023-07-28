package com.sg.Music.controller;

import com.sg.Music.entities.*;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GenreAlbum {

    @Autowired
    ServiceInterface service;

    @GetMapping("genres")
    public String displayGenres(Model model) {
        List<Song> songs = service.getAllSongs();
        List<Genre> genres = service.getAllGenres();
        model.addAttribute("songs", songs);
        model.addAttribute("genres", genres);

        return "genres";
    }

}
