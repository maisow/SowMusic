package com.sg.Music.service;

import com.sg.Music.entities.*;

import java.util.List;

public interface ServiceInterface {
    Album getAlbumByID(int id);
    List<Album> getAllAlbums();
    Album addAlbum(Album album);
    void updateAlbum(Album album);
    void deleteAlbumByID(int id);
    public Artist getArtistByName(String artistName);

    List<Album> getAlbumsByArtist(Artist artist);
    Artist getArtistByID(int id);
    List<Artist> getAllArtists();
    Artist addArtist(Artist artist);
    void updateArtist(Artist artist);
    void deleteArtistByID(int id);

    public List<Artist> getArtistsByLabel(Label label);

    List<Artist> getArtistsByAlbum(Album album);

    Genre getGenreByID(int id);
    List<Genre> getAllGenres();
    Genre addGenre(Genre genre);
    void updateGenre(Genre genre);
    void deleteGenreByID(int id);
    Label getLabelByID(int id);
    List<Label> getAllLabels();
    Label addLabel(Label label);
    void updateLabel(Label label);
    void deleteLabelByID(int id);


    Playlist gePlaylistByID(int id);
    List<Playlist> getAllPlaylists();
    Playlist addPlaylist(Playlist playlist);
    void updatePlaylist(Playlist playlist);
    void deletePlaylistByID(int id);

    List<Playlist> getPlaylistsBySong(Song song);

    Song getSongByID(int id);
    List<Song> getAllSongs();
    Song addSong(Song song);
    void updateSong(Song song);
    void deleteSongByID(int id);
}
