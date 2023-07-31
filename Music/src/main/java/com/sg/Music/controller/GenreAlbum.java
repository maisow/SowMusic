package com.sg.Music.controller;

import com.sg.Music.entities.*;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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

    @GetMapping("addGenre")
    public String addGenre(Model model) {
        List<Song> songs = service.getAllSongs();
        model.addAttribute("songs", songs);
        // Add a new Genre object to the model for form-backing
        model.addAttribute("genre", new Genre());
        return "addGenre";
    }


    @PostMapping("addGenre")
    public String addGenre(@Valid Genre genre, BindingResult result, HttpServletRequest request, Model model) {
        String songId = request.getParameter("songID");

        genre.setSong(service.getSongByID(Integer.parseInt(songId)));

        if (StringUtils.isEmpty(genre.getName())) {
            FieldError error = new FieldError("genre", "name", "Genre must must have a name");
            result.addError(error);
        }

        if (StringUtils.isEmpty(genre.getDescription())) {
            FieldError error = new FieldError("genre", "description", "Genre must must have a description");
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "addGenre";
        }

        service.addGenre(genre);

        return "redirect:/genres";
    }

    @GetMapping("editGenre")
    public String editGenre(Integer id, Model model) {
        List<Song> songs = service.getAllSongs();
        model.addAttribute("songs", songs);
        Genre genre = service.getGenreByID(id);
        model.addAttribute("genre", genre);
        return "editGenre";
    }


    @PostMapping("editGenre")
    public String editGenre(@Valid Genre genre, BindingResult result, HttpServletRequest request, Model model) {
        String songId = request.getParameter("songId");

        genre.setSong(service.getSongByID(Integer.parseInt(songId)));

        if (StringUtils.isEmpty(genre.getName())) {
            FieldError error = new FieldError("genre", "name", "Genre must must have a name");
            result.addError(error);
        }

        if (StringUtils.isEmpty(genre.getDescription())) {
            FieldError error = new FieldError("genre", "description", "Genre must must have a description");
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "editGenre";
        }

        service.updateGenre(genre);

        return "redirect:/genres";
    }

}