package com.sg.Music.controller;

import com.sg.Music.entities.*;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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

    @PostMapping("deleteArtist")
    public String deleteArtist(Integer id) {
        service.deleteArtistByID(id);

        return "redirect:/artists";
    }

    @GetMapping("addArtist")
    public String addArtist(Model model) {
        List<Label> labels = service.getAllLabels();
        List<Album> albums = service.getAllAlbums();
        model.addAttribute("labels", labels);
        model.addAttribute("albums", albums);
        // Add a new Artist object to the model for form-backing
        model.addAttribute("artist", new Artist());
        return "addArtist";
    }


    @PostMapping("addArtist")
    public String addArtist(@Valid Artist artist, BindingResult result, HttpServletRequest request, Model model) {
        String labelId = request.getParameter("labelId");
        String[] albumIds = request.getParameterValues("albumId");

        artist.setLabel(service.getLabelByID(Integer.parseInt(labelId)));

        if (albumIds == null) {
            FieldError error = new FieldError("artist", "albums", "Artist must belong to at least one album");
            result.addError(error);
        } else {
            List<Album> albums = new ArrayList<>();
            for (String albumId : albumIds) {
                albums.add(service.getAlbumByID(Integer.parseInt(albumId)));
            }
            artist.setAlbums(albums);
        }

        if (StringUtils.isEmpty(artist.getName())) {
            FieldError error = new FieldError("artist", "name", "Artist must have a name");
            result.addError(error);
        }

        if (labelId == null) {
            FieldError error = new FieldError("artist", "label", "Artist must have a label");
            result.addError(error);
        }


        if (result.hasErrors()) {
            model.addAttribute("labels", service.getAllLabels());
            model.addAttribute("albums", service.getAllAlbums());
            return "addArtist";
        }

        service.addArtist(artist);

        return "redirect:/artists";
    }




    @GetMapping("editArtist")
    public String editArtist(Integer id, Model model) {
        List<Label> labels = service.getAllLabels();
        List<Album> albums = service.getAllAlbums();
        model.addAttribute("labels", labels);
        model.addAttribute("albums", albums);
        Artist artist = service.getArtistByID(id);
        model.addAttribute("artist", artist);
        return "editArtist";
    }


    @PostMapping("editArtist")
    public String editArtist(@Valid Artist artist, BindingResult result, HttpServletRequest request, Model model) {
        String labelId = request.getParameter("labelId");
        String[] albumIds = request.getParameterValues("albumId");

        artist.setLabel(service.getLabelByID(Integer.parseInt(labelId)));

        if (albumIds == null) {
            FieldError error = new FieldError("artist", "albums", "Artist must belong to at least one album");
            result.addError(error);
        } else {
            List<Album> albums = new ArrayList<>();
            for (String albumId : albumIds) {
                albums.add(service.getAlbumByID(Integer.parseInt(albumId)));
            }
            artist.setAlbums(albums);
        }

        if (StringUtils.isEmpty(artist.getName())) {
            FieldError error = new FieldError("artist", "name", "Artist must have a name");
            result.addError(error);
        }

        if (labelId == null) {
            FieldError error = new FieldError("artist", "label", "Artist must have a label");
            result.addError(error);
        }


        if (result.hasErrors()) {
            model.addAttribute("labels", service.getAllLabels());
            model.addAttribute("albums", service.getAllAlbums());
            return "editArtist";
        }

        service.updateArtist(artist);

        return "redirect:/artists";
    }
    @GetMapping("/searchArtists")
    public String searchArtists(@RequestParam(name = "labelId", required = false) Integer labelId, Model model) {
        List<Artist> artists;
        if (labelId != null && labelId != 0) {
            Label label = service.getLabelByID(labelId);
            if (label != null) {
                artists = service.getArtistsByLabel(label);
            } else {
                // If the label is not found, show an empty list of artists
                artists = new ArrayList<>();
            }
        } else {
            // If no label is selected, show all artists
            artists = service.getAllArtists();
        }
        List<Album> albums = service.getAllAlbums();
        List<Label> labels = service.getAllLabels();
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        model.addAttribute("labels", labels);
        return "artists";
    }




}
