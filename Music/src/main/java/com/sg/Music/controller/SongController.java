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
public class SongController {

    @Autowired
    ServiceInterface service;

    @GetMapping("songs")
    public String displaySongs(Model model) {
        List<Song> songs = service.getAllSongs();
        List<Album> albums = service.getAllAlbums();
        List<Artist> artists = service.getAllArtists();
        List<Playlist> playlists = service.getAllPlaylists();
        model.addAttribute("songs", songs);
        model.addAttribute("albums", albums);
        model.addAttribute("artists", artists);
        model.addAttribute("playlists", playlists);

        return "songs";
    }

    @GetMapping("songDetail")
    public String songDetail(Integer id, Model model) {
        Song song = service.getSongByID(id);
        model.addAttribute("song", song);

        return "songDetail";
    }
    @PostMapping("deleteSong")
    public String deleteSong(Integer id) {
        service.deleteSongByID(id);

        return "redirect:/songs";
    }

    @GetMapping("addSong")
    public String addSong(Model model) {
        List<Playlist> playlists = service.getAllPlaylists();
        List<Artist> artists = service.getAllArtists();
        List<Album> albums = service.getAllAlbums();
        model.addAttribute("playlists", playlists);
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        // Add a new Playlist object to the model for form-backing
        model.addAttribute("song", new Song());
        return "addSong";
    }


    @PostMapping("addSong")
    public String addSong(@Valid Song song, BindingResult result, HttpServletRequest request, Model model) {
        String artistId = request.getParameter("artistId");
        String albumId = request.getParameter("albumId");
        String[] playlistIds = request.getParameterValues("playlistId");

        song.setArtist(service.getArtistByID(Integer.parseInt(artistId)));
        song.setAlbum(service.getAlbumByID(Integer.parseInt(albumId)));

        if (playlistIds == null) {
            FieldError error = new FieldError("song", "playlists", "Song must belong to at least one playlist");
            result.addError(error);
        } else {
            List<Playlist> playlists = new ArrayList<>();
            for (String playlistId : playlistIds) {
                playlists.add(service.gePlaylistByID(Integer.parseInt(playlistId)));
            }
            song.setPlaylists(playlists);
        }

        if (StringUtils.isEmpty(song.getName())) {
            FieldError error = new FieldError("song", "name", "Song must have a name");
            result.addError(error);
        }

        if (artistId == null) {
            FieldError error = new FieldError("song", "artist", "Song must have a artist");
            result.addError(error);
        }
        if (albumId == null) {
            FieldError error = new FieldError("song", "album", "Song must have a album");
            result.addError(error);
        }


        if (result.hasErrors()) {
            model.addAttribute("artists", service.getAllArtists());
            model.addAttribute("albums", service.getAllAlbums());
            model.addAttribute("playlists", service.getAllPlaylists());
            return "addSong";
        }

        service.addSong(song);

        return "redirect:/songs";
    }


    @GetMapping("editSong")
    public String editSong(Integer id, Model model) {
        List<Playlist> playlists = service.getAllPlaylists();
        List<Artist> artists = service.getAllArtists();
        List<Album> albums = service.getAllAlbums();
        model.addAttribute("playlists", playlists);
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        Song song = service.getSongByID(id);
        model.addAttribute("song", song);
        return "editSong";
    }


    @PostMapping("editSong")
    public String editSong(@Valid Song song, BindingResult result, HttpServletRequest request, Model model) {
        String artistId = request.getParameter("artistId");
        String albumId = request.getParameter("albumId");
        String[] playlistIds = request.getParameterValues("playlistId");

        song.setArtist(service.getArtistByID(Integer.parseInt(artistId)));
        song.setAlbum(service.getAlbumByID(Integer.parseInt(albumId)));

        if (playlistIds == null) {
            FieldError error = new FieldError("song", "playlists", "Song must belong to at least one playlist");
            result.addError(error);
        } else {
            List<Playlist> playlists = new ArrayList<>();
            for (String playlistId : playlistIds) {
                playlists.add(service.gePlaylistByID(Integer.parseInt(playlistId)));
            }
            song.setPlaylists(playlists);
        }

        if (StringUtils.isEmpty(song.getName())) {
            FieldError error = new FieldError("song", "name", "Song must have a name");
            result.addError(error);
        }

        if (artistId == null) {
            FieldError error = new FieldError("song", "artist", "Song must have a artist");
            result.addError(error);
        }
        if (albumId == null) {
            FieldError error = new FieldError("song", "album", "Song must have a album");
            result.addError(error);
        }


        if (result.hasErrors()) {
            model.addAttribute("artists", service.getAllArtists());
            model.addAttribute("albums", service.getAllAlbums());
            model.addAttribute("playlists", service.getAllPlaylists());
            return "editSong";
        }

        service.updateSong(song);

        return "redirect:/songs";
    }



}


