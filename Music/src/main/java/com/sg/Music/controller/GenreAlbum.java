package com.sg.Music.controller;

import com.sg.Music.entities.*;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("genreDetail")
    public String genreDetail(Integer id, Model model) {
        Genre genre = service.getGenreByID(id);
        model.addAttribute("genre", genre);

        return "genreDetail";
    }

    @PostMapping("deleteGenre")
    public String deleteGenre(Integer id) {
        service.deleteGenreByID(id);

        return "redirect:/genres";
    }

}
