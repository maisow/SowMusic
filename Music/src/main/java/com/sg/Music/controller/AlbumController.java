package com.sg.Music.controller;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Playlist;
import com.sg.Music.entities.Song;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addAlbum(@Valid Album album, BindingResult result, HttpServletRequest request, Model model) {
        String[] artistIds = request.getParameterValues("artistId");

        if (artistIds == null){
            FieldError error = new FieldError("album", "artists", "Album must belong to at least one artist");
            result.addError(error);
        } else{
            List<Artist> artists = new ArrayList<>();
            for(String artistId : artistIds) {
                artists.add(service.getArtistByID(Integer.parseInt(artistId)));

            }  album.setArtists(artists);
        }

        if (StringUtils.isEmpty(album.getName())){
            FieldError error = new FieldError("album", "name", "Album must must have a name");
            result.addError(error);
        }

        if (StringUtils.isEmpty(album.getDescription())){
            FieldError error = new FieldError("album", "description", "Album must must have a description");
            result.addError(error);
        }

        if(result.hasErrors()) {
            model.addAttribute("artists", service.getAllArtists());
            return "addAlbum";
        }

        service.addAlbum(album);

        return "redirect:/albums";
    }

    @GetMapping("editAlbum")
    public String editAlbum(Integer id, Model model) {
        Album album = service.getAlbumByID(id);
        List<Artist> artists = service.getAllArtists();
        model.addAttribute("album", album);
        model.addAttribute("artists", artists);

        return "editAlbum";
    }

    @PostMapping("editAlbum")
    public String performEditAlbum(@Valid Album album, BindingResult result,  HttpServletRequest request, Model model) {
        String[] artistIds = request.getParameterValues("artistId");

        if (artistIds == null){
            FieldError error = new FieldError("album", "artists", "Album must belong to at least one artist");
            result.addError(error);
        } else{
            List<Artist> artists = new ArrayList<>();
            for(String artistId : artistIds) {
                artists.add(service.getArtistByID(Integer.parseInt(artistId)));

            }  album.setArtists(artists);
        }

        if (StringUtils.isEmpty(album.getName())){
            FieldError error = new FieldError("album", "name", "Album must must have a name");
            result.addError(error);
        }

        if (StringUtils.isEmpty(album.getDescription())){
            FieldError error = new FieldError("album", "description", "Album must must have a description");
            result.addError(error);
        }

        if(result.hasErrors()) {
            model.addAttribute("artists", service.getAllArtists());
            return "editAlbum";
        }

        service.updateAlbum(album);

        return "redirect:/albums";
    }

    @GetMapping("/searchAlbums")
    public String searchAlbums(@RequestParam(name = "artistId", required = false) Integer artistId, Model model) {
        List<Album> albums;
        if (artistId != null && artistId != 0) {
            Artist artist = service.getArtistByID(artistId);
            if (artist != null) {
                albums = service.getAlbumsByArtist(artist);
            } else {
                // If the artist is not found, show an empty list of albums
                albums = new ArrayList<>();
            }
        } else {
            // If no artist is selected, show all albums
            albums = service.getAllAlbums();
        }
        List<Artist> artists = service.getAllArtists();
        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        return "albums";
    }





}
