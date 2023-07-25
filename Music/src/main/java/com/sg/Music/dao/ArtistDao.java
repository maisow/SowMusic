package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;

import java.util.List;

public interface ArtistDao {

    Artist getArtistByID(int id);
    List<Artist> getAllArtists();
    Artist addArtist(Artist artist);
    void updateArtist(Artist artist);
    void deleteArtistByID(int id);

    List<Artist> getArtistsByAlbum(Album album);

}
