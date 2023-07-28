package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Playlist;
import com.sg.Music.entities.Song;


import java.util.List;

public interface PlaylistDao {

    Playlist gePlaylistByID(int id);
    List<Playlist> getAllPlaylists();
    Playlist addPlaylist(Playlist playlist);
    void updatePlaylist(Playlist playlist);
    void deletePlaylistByID(int id);
    List<Song> getSongsForPlaylist(int playlistId);

    List<Playlist> getPlaylistsBySong(Song song);
}
