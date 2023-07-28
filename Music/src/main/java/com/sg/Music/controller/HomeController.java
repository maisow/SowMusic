package com.sg.Music.controller;

import com.sg.Music.entities.Album;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ServiceInterface service;

    @GetMapping("/")
    public String index(Model model) {
        List<Album> albums = service.getAllAlbums();
        if (albums.size() > 10) {
            albums = albums.subList(0, 10);
        }
        model.addAttribute("albums", albums);

        return "index";
    }
}