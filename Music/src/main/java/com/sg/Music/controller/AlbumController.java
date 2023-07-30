package com.sg.Music.controller;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Playlist;
import com.sg.Music.entities.Song;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

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

    @PostMapping("deleteAlbum")
    public String deleteAlbum(Integer id) {
        service.deleteAlbumByID(id);

        return "redirect:/albums";
    }

    @GetMapping("addAlbum")
    public String addAlbum(Model model) {
        List<Artist> artists = service.getAllArtists();
        model.addAttribute("artists", artists);
        // Add a new Album object to the model for form-backing
        model.addAttribute("album", new Album());
        return "addAlbum";
    }


    @PostMapping("addAlbum")
    public String addAlbum(@Valid  Album album, BindingResult result, HttpServletRequest request, Model model) {
        String[] artistIds = request.getParameterValues("artistId");

        if (artistIds == null){
            FieldError error = new FieldError("album", "artists", "Album must belong to atleast one artist");
            result.addError(error);
        } else{
            List<Artist> artists = new ArrayList<>();
            for(String artistId : artistIds) {
                artists.add(service.getArtistByID(Integer.parseInt(artistId)));

            }  album.setArtists(artists);
        }

        if(result.hasErrors()) {
            model.addAttribute("artists", service.getAllArtists());
            return "addAlbum";
        }


        service.addAlbum(album);

        return "redirect:/albums";
    }



}
