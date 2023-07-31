package com.sg.Music.dao;

import com.sg.Music.entities.Album;
import com.sg.Music.entities.Artist;

import java.util.List;

public interface AlbumDao {
    Album getAlbumByID(int id);
    List<Album> getAllAlbums();
    Album addAlbum(Album album);
    void updateAlbum(Album album);
    void deleteAlbumByID(int id);
    List<Artist> getArtistsForAlbum(int albumId);
    List<Album> getAlbumsByArtist(Artist artist);

    public Artist getArtistByName(String artistName);

}
