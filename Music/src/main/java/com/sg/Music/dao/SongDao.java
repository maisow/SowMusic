package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;
import com.sg.Music.entities.Song;

import java.util.List;

public interface SongDao {

    Song getSongByID(int id);
    List<Song> getAllSongs();
    Song addSong(Song song);
    void updateSong(Song song);
    void deleteSongByID(int id);

    Artist getArtistForSong(int songId);

    Album getAlbumForSong(int songId);


}
