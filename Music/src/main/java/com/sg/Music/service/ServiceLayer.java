package com.sg.Music.service;

import com.sg.Music.dao.*;
import com.sg.Music.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLayer implements ServiceInterface {

    @Autowired
    AlbumDao albumDao;

    @Autowired
    ArtistDao artistDao;

    @Autowired
    GenreDao genreDao;

    @Autowired
    LabelDao labelDao;

    @Autowired
    PlaylistDao playlistDao;

    @Autowired
    SongDao songDao;

    @Override
    public Album getAlbumByID(int id) {
       return albumDao.getAlbumByID(id);
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumDao.getAllAlbums();
    }

    @Override
    public Album addAlbum(Album album) {
        return albumDao.addAlbum(album);
    }

    @Override
    public void updateAlbum(Album album) {
        albumDao.updateAlbum(album);
    }

    @Override
    public void deleteAlbumByID(int id) {
        albumDao.deleteAlbumByID(id);
    }

    @Override
    public Artist getArtistByName(String artistName) {
        return albumDao.getArtistByName(artistName);
    }

    @Override
    public List<Album> getAlbumsByArtist(Artist artist) {
        return albumDao.getAlbumsByArtist(artist);
    }

    @Override
    public Artist getArtistByID(int id) {
        return artistDao.getArtistByID(id);
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistDao.getAllArtists();
    }

    @Override
    public Artist addArtist(Artist artist) {
        return artistDao.addArtist(artist);
    }

    @Override
    public void updateArtist(Artist artist) {
        artistDao.updateArtist(artist);
    }

    @Override
    public void deleteArtistByID(int id) {
        artistDao.deleteArtistByID(id);
    }

    @Override
    public List<Artist> getArtistsByLabel(Label label) {
        return artistDao.getArtistsByLabel(label);
    }

    @Override
    public List<Artist> getArtistsByAlbum(Album album) {
        return artistDao.getArtistsByAlbum(album);
    }

    @Override
    public Genre getGenreByID(int id) {
        return genreDao.getGenreByID(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    @Override
    public Genre addGenre(Genre genre) {
        return genreDao.addGenre(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreDao.updateGenre(genre);
    }

    @Override
    public void deleteGenreByID(int id) {
        genreDao.deleteGenreByID(id);
    }

    @Override
    public Label getLabelByID(int id) {
        return labelDao.getLabelByID(id);
    }

    @Override
    public List<Label> getAllLabels() {
        return labelDao.getAllLabels();
    }

    @Override
    public Label addLabel(Label label) {
        return labelDao.addLabel(label);
    }

    @Override
    public void updateLabel(Label label) {
        labelDao.updateLabel(label);
    }

    @Override
    public void deleteLabelByID(int id) {
        labelDao.deleteLabelByID(id);
    }

    @Override
    public Playlist gePlaylistByID(int id) {
        return playlistDao.gePlaylistByID(id);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistDao.getAllPlaylists();
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        return playlistDao.addPlaylist(playlist);
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        playlistDao.updatePlaylist(playlist);
    }

    @Override
    public void deletePlaylistByID(int id) {
        playlistDao.deletePlaylistByID(id);
    }

    @Override
    public List<Playlist> getPlaylistsBySong(Song song) {
        return playlistDao.getPlaylistsBySong(song);
    }

    @Override
    public Song getSongByID(int id) {
        return songDao.getSongByID(id);
    }

    @Override
    public List<Song> getAllSongs() {
        return songDao.getAllSongs();
    }

    @Override
    public Song addSong(Song song) {
        return songDao.addSong(song);
    }

    @Override
    public void updateSong(Song song) {
        songDao.updateSong(song);
    }

    @Override
    public void deleteSongByID(int id) {
        songDao.deleteSongByID(id);
    }
}
