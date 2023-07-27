package com.sg.Music.dao;

import com.sg.Music.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GenreDaoDBTest {

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

    public GenreDaoDBTest() {
    }

    @BeforeEach
    void setUp() {

        List<Album> albums = albumDao.getAllAlbums();
        albums.forEach(album -> {
            albumDao.deleteAlbumByID(album.getId());
        });

        List<Artist> artists = artistDao.getAllArtists();
        artists.forEach(artist -> {
            artistDao.deleteArtistByID(artist.getId());
        });

        List<Genre> genres = genreDao.getAllGenres();
        genres.forEach(genre -> {
            genreDao.deleteGenreByID(genre.getId());
        });

        List<Label> labels = labelDao.getAllLabels();
        labels.forEach(label -> {
            labelDao.deleteLabelByID(label.getId());
        });

        List<Playlist> playlists = playlistDao.getAllPlaylists();
        playlists.forEach(playlist -> {
            playlistDao.deletePlaylistByID(playlist.getId());
        });

        List<Song> songs = songDao.getAllSongs();
        songs.forEach(song -> {
            songDao.deleteSongByID(song.getId());
        });
    }

    @Test
    void getGenreByID() {
    }

    @Test
    void getAllGenres() {
    }

    @Test
    void addGenre() {
    }

    @Test
    void updateGenre() {
    }

    @Test
    void deleteGenreByID() {
    }
}