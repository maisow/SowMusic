package com.sg.Music.controller;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Playlist;
import com.sg.Music.entities.Song;
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
public class PlaylistController {

    @Autowired
    ServiceInterface service;

    @GetMapping("playlists")
    public String displayPlaylists(Model model) {
        List<Song> songs = service.getAllSongs();
        List<Playlist> playlists = service.getAllPlaylists();
        model.addAttribute("songs", songs);
        model.addAttribute("playlists", playlists);

        return "playlists";
    }
    @GetMapping("playlistDetail")
    public String playlistDetail(Integer id, Model model) {
        Playlist playlist = service.gePlaylistByID(id);
        model.addAttribute("playlist", playlist);

        return "playlistDetail";
    }

    @PostMapping("deletePlaylist")
    public String deletePlaylist(Integer id) {
        service.deletePlaylistByID(id);

        return "redirect:/playlists";
    }

    @GetMapping("addPlaylist")
    public String addPlaylist(Model model) {
        List<Song> songs = service.getAllSongs();
        model.addAttribute("songs", songs);
        // Add a new Song object to the model for form-backing
        model.addAttribute("playlist", new Playlist());
        return "addPlaylist";
    }


    @PostMapping("addPlaylist")
    public String addPlaylist(@Valid Playlist playlist, BindingResult result, HttpServletRequest request, Model model) {
        String[] songIds = request.getParameterValues("songId");

        if (songIds == null){
            FieldError error = new FieldError("playlist", "songs", "Playlist must have at least one song");
            result.addError(error);
        } else{
            List<Song> songs = new ArrayList<>();
            for(String songId : songIds) {
                songs.add(service.getSongByID(Integer.parseInt(songId)));

            }  playlist.setSongs(songs);
        }

        if (StringUtils.isEmpty(playlist.getName())){
            FieldError error = new FieldError("playlist", "name", "Playlist must must have a name");
            result.addError(error);
        }

        if (StringUtils.isEmpty(playlist.getDescription())){
            FieldError error = new FieldError("playlist", "description", "Playlist must must have a description");
            result.addError(error);
        }

        if(result.hasErrors()) {
            model.addAttribute("songs", service.getAllSongs());
            return "addPlaylist";
        }

        service.addPlaylist(playlist);

        return "redirect:/playlists";
    }

    @GetMapping("editPlaylist")
    public String editPlaylist(Integer id, Model model) {
        List<Song> songs = service.getAllSongs();
        model.addAttribute("songs", songs);
        Playlist playlist = service.gePlaylistByID(id);
        model.addAttribute("playlist", playlist);
        return "editPlaylist";
    }


    @PostMapping("editPlaylist")
    public String editPlaylist(@Valid Playlist playlist, BindingResult result, HttpServletRequest request, Model model) {
        String[] songIds = request.getParameterValues("songId");

        if (songIds == null){
            FieldError error = new FieldError("playlist", "songs", "Playlist must have at least one song");
            result.addError(error);
        } else{
            List<Song> songs = new ArrayList<>();
            for(String songId : songIds) {
                songs.add(service.getSongByID(Integer.parseInt(songId)));

            }  playlist.setSongs(songs);
        }

        if (StringUtils.isEmpty(playlist.getName())){
            FieldError error = new FieldError("playlist", "name", "Playlist must must have a name");
            result.addError(error);
        }

        if (StringUtils.isEmpty(playlist.getDescription())){
            FieldError error = new FieldError("playlist", "description", "Playlist must must have a description");
            result.addError(error);
        }

        if(result.hasErrors()) {
            model.addAttribute("songs", service.getAllSongs());
            return "editPlaylist";
        }

        service.updatePlaylist(playlist);

        return "redirect:/playlists";
    }
}
